package com.innoviti.emi.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.objectstore.ObjectStoreLookUp;
import com.innoviti.emi.service.core.JobService;
import com.innoviti.emi.service.master.BajajEMIFileListService;
import com.innoviti.emi.util.Util;

@Component
public class JobScheduler {

  private static Logger logger = LoggerFactory.getLogger(JobScheduler.class);
  
  @Autowired
  private BajajEMIFileListService bajajEMIFileListService;

  @Value("${store.upload.url}")
  private String objectStoreUrl;

  @Autowired
  private JobService jobService;
  
  /**
   *
   * cron = second, minute, hour, day, month, weekday
   * @throws Exception 
   */
  @Scheduled(cron = "${job.schedule.cron}")
  public void startFileProcess() throws Exception{
    List<ObjectStoreLookUp> fileList = bajajEMIFileListService.getList();
    if(fileList != null && !fileList.isEmpty()){
      jobService.launchMasterJob(Util.TEMP_FOLDER);
    }
    logger.info("You have launched your job successfully");
  }
  
}
