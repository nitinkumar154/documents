package com.innoviti.emi.constant;

public enum JobType {
  
  FILE_TO_DATABASE("fileReaderToDatabaseJob"),
  MASTER_TO_CORE_TABLE("masterToCoreTableJob"),
  DATABASE_TO_CSV("databaseToCsvJob");
 
  private String jobName;
  
  private JobType(String jobName){
    this.jobName = jobName;
  }
  public String getJobName() {
    return jobName;
  }
  
}
