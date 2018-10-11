package com.innoviti.emi.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.setup.SetupWithJob;
import com.innoviti.emi.util.Util;

public class MasterTableJobTest extends SetupWithJob{
  
  @Autowired
  private Flow createFileToDatabaseFlow;

  //@Test
  public void masterToTableJob() throws Exception{
    Job job = buildJob(createFileToDatabaseFlow, "masterToTableJob");
    JobParameters jobParameters = buildJobParameters(Util.TEMP_FOLDER);
    launchJob(job, jobParameters);
  }
}
