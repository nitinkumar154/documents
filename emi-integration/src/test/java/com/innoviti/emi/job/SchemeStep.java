package com.innoviti.emi.job;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.setup.SetupWithJob;
import com.innoviti.emi.util.Util;

public class SchemeStep extends SetupWithJob{

  @Autowired
  private Flow schemeMasterToSchemeFlow;
  @Test
  public void testScheme() throws Exception{
    Job job = buildJob(schemeMasterToSchemeFlow, "schemeMasterToSchemeJob");
    JobParameters jobParameters = buildJobParameters(Util.TEMP_FOLDER);
    launchJob(job, jobParameters);
  }
}
