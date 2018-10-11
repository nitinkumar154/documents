package com.innoviti.emi.setup;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.innoviti.emi.constant.JobFileType;
import com.innoviti.emi.exception.RequestNotCompletedException;
import com.innoviti.emi.starter.EmiServiceStarter;
import com.innoviti.emi.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmiServiceStarter.class)
@TestPropertySource(locations="file:config/test-config.properties")
public class SetupWithJob {
  
  private Logger logger = LoggerFactory.getLogger(SetupWithJob.class);
  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  
  @Autowired
  private JobRepository jobRepository;
  
  protected Job buildJob(Flow flow, String jobName){
    return jobBuilderFactory.get(jobName)
        .start(flow).end()
        .build();
  }
  @Test
  public void test(){
    
  }
  /**
   * Run job for 1 step
   * 
   * @param job
   * @param filePath
   * @param jobFileType
   * @throws Exception
   */
  protected void launchJob(Job job, String filePath, JobFileType jobFileType) throws Exception{
    JobParametersBuilder parameterBuilder = new JobParametersBuilder();
    parameterBuilder.addString(jobFileType.getFileType(), filePath);
    launchJob(job, parameterBuilder.toJobParameters());
  }
  /**
   * Run job for multiple steps
   * @param job
   * @param jobParameters
   * @throws Exception
   */
  protected void launchJob(Job job, JobParameters jobParameters) throws Exception{
    JobExecution jobExecution = jobLauncher().run(job, jobParameters);
    logger.info("Job {} {} ", job.getName(), jobExecution.getStatus());
  }
  protected JobParameters buildJobParameters(String folder){
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    Path path = Paths.get(folder);
    if(path == null){
      throw new RequestNotCompletedException("Provided folder not found in file system");
    }
    File [] files = path.toFile().listFiles();
    if(files == null || files.length == 0){
      throw new RequestNotCompletedException("No files found to process");
    }
    for(JobFileType fileType : JobFileType.values()){
      if(fileType == JobFileType.SCHEME_MODEL_TEMINAL_MAPPING){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(folder).append(File.separator).append(fileType.getFileType())
        .append(System.currentTimeMillis()).append(".txt");
        jobParameterBuilder.addString(fileType.getFileType(), stringBuilder.toString());
        continue;
      }
      for(File file : files){
        if(file.getName().contains(Util.RESULT_FILE_TYPE_1)|| file.getName().contains(Util.RESULT_FILE_TYPE_2)){
          continue;
        }
        if(file.getName().startsWith(fileType.getFileType())){
         jobParameterBuilder.addString(fileType.getFileType(), file.getPath());
        }
      }
    }
    return jobParameterBuilder.toJobParameters();
  }
  private SimpleJobLauncher jobLauncher(){
    SyncTaskExecutor taskExecutor = new SyncTaskExecutor();
    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(taskExecutor);
    return jobLauncher;
  }
  
}
