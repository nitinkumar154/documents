package com.innoviti.emi.job.item.listener;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.innoviti.emi.integration.SftpOutboundAdapter.UploadGateway;
import com.innoviti.emi.model.Supplier;
import com.innoviti.emi.service.impl.core.DefaultDataServiceImpl;
import com.innoviti.emi.util.IntegrationUtil;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener{

  private Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
 
  @Autowired
  private UploadGateway gateway;
  
  @Autowired
  private Environment env;
  
  @Override
  public void beforeJob(JobExecution jobExecution) {
    String jobName = jobExecution.getJobInstance().getJobName();
    logger.info("Job {} started at {}", jobName, jobExecution.getStartTime());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    Supplier.clearSupplierList();
    DefaultDataServiceImpl.reinializeVariables();
    String jobName = jobExecution.getJobInstance().getJobName();
    for(Entry<String, JobParameter> jobParamEntry: jobExecution.getJobParameters().getParameters().entrySet()) {
      logger.info("Job Parameter key:" + jobParamEntry.getKey() + ", value: " + jobParamEntry.getValue());
      String resultFilePath = getResponseFilePath(jobParamEntry.getValue().toString());
      Path resultFile = Paths.get(resultFilePath);
      if(resultFile.toFile().exists()){
        try {
          logger.info("Started uploading file {} to sftp server.", resultFile);
          gateway.sendToFtp(resultFile.toFile());
          logger.info("Finished uploading file {} to sftp server.", resultFile);
        }
        catch(Exception ex) {
          logger.error("Error while uploading file from {} to sftp server.", resultFilePath);
          logger.error("", ex);
        }
        try {
          logger.info("Started uploading file {} to object store server.", resultFile);
          String uploadUrl = env.getRequiredProperty("store.upload.url")+"/upload";
          IntegrationUtil.uploadClient(resultFile.toFile(),"processed-master", "bajaj", uploadUrl);
          logger.info("Finished uploading file {} to object store server.", resultFile);
        }
        catch(Exception ex) {
          logger.error("Error while uploading file from {} to object store server.", resultFilePath);
          logger.error("", ex);
        }
      }
    }
    BatchStatus batchStatus = jobExecution.getStatus();
    logger.info("Job {} ends at {} and status is  {}", jobName, jobExecution.getEndTime(), batchStatus);
  }
  
  private String getResponseFilePath(String filePath) {
    return filePath.substring(0, filePath.lastIndexOf('.'))+"_job1_result.txt";
  }

}
