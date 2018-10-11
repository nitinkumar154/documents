package com.innoviti.emi.job;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.constant.JobFileType;
import com.innoviti.emi.setup.SetupWithJob;


public class FileToModelStepTest extends SetupWithJob{
 
  @Autowired
  private Flow modelMasterFlow;

  @Test
  public void modelFileModelTable() throws Exception{
    
    Job job = buildJob(modelMasterFlow, "modelFileModelTable");
    launchJob(job, "MDLM-00059-070316-193736.txt", JobFileType.MODEL_MASTER);
  }
}
