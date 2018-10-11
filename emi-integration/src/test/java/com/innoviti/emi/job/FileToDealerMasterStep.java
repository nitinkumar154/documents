package com.innoviti.emi.job;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.constant.JobFileType;
import com.innoviti.emi.setup.SetupWithJob;

public class FileToDealerMasterStep extends SetupWithJob{

  @Autowired
  private Flow dealerMasterFlow;
  
  @Test
  public void dealerMasterTable() throws Exception{
    Job job = buildJob(dealerMasterFlow, "dealerMasterTable");
    launchJob(job, "DLRM-00059-171117-194648.txt", JobFileType.DEALER_MASTER);
  }
}
