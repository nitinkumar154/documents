package com.innoviti.emi.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.setup.SetupWithJob;
import com.innoviti.emi.util.Util;

public class MasterToCoreTableJobTest extends SetupWithJob{

  @Autowired
  private Job masterToCoreTableJob;
  
  //@Test
  public void masterToCoreTable() throws Exception{
    JobParameters jobParameters = buildJobParameters(Util.TEMP_FOLDER);
    launchJob(masterToCoreTableJob, jobParameters);
  }
}
