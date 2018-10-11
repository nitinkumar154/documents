package com.innoviti.emi.job;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.constant.JobFileType;
import com.innoviti.emi.setup.SetupWithJob;
import com.innoviti.emi.util.Util;

public class AssetCategoryStep extends SetupWithJob{
  
  @Autowired
  private Flow assetCategoryMasterFlow;
  
  @Autowired
  private Flow assetCategoryMasterToCategoryFlow;
  
  @Test
  public void testAssetCategoryMaster() throws Exception{
    Job job = buildJob(assetCategoryMasterFlow, "categoryMasterTable");
    launchJob(job, "temp/ASTM-00059-171117-165947.txt", JobFileType.ASSET_CATEGORY_MASTER);
  }
  //@Test
  public void testCategory() throws Exception{
    Job job = buildJob(assetCategoryMasterToCategoryFlow, "assetCategoryMasterToCategoryJob");
    JobParameters jobParameters = buildJobParameters(Util.TEMP_FOLDER);
    launchJob(job, jobParameters);
  }
}
