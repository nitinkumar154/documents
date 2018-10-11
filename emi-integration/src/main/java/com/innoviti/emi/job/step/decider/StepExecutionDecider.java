package com.innoviti.emi.job.step.decider;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class StepExecutionDecider implements JobExecutionDecider{

  public final static String SKIP = "SKIP";
  public final static String START = "START";
  public final static String SKIP_COMPLETED = "SKIP_COMPLETED";
  private String fileKey;
  public StepExecutionDecider(String fileKey) {
    this.fileKey = fileKey;
  }
  @Override
  public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
    String filePath = jobExecution.getJobParameters().getString(fileKey);
    if(filePath == null || filePath.isEmpty()){
      return new FlowExecutionStatus(SKIP);
    }
    Path path = Paths.get(filePath);
    if(!path.toFile().exists()){
      return new FlowExecutionStatus(SKIP);
    }
    return new FlowExecutionStatus(START);
  }

}
