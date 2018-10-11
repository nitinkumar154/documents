package com.innoviti.emi.file;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.constant.JobFileType;
import com.innoviti.emi.setup.SetupWithJob;

public class SchemeModelJobTest extends SetupWithJob{
  
  @Autowired
  Flow schemeMasterFlow;
  
 @Test
  public void importSchemeMasterJob() throws Exception{
    Job job = buildJob(schemeMasterFlow, "schemeMasterFlow");
    launchJob(job, "SCHM-MD-171117-111711.txt", JobFileType.MODEL_MASTER);
  }
}
