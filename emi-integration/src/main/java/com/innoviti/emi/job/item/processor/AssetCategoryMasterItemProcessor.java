package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.AssetCategoryMaster;

@Component
public class AssetCategoryMasterItemProcessor
    implements ItemProcessor<AssetCategoryMaster, AssetCategoryMaster> {

  @Override
  public AssetCategoryMaster process(AssetCategoryMaster item) throws Exception {
    return item;
  }
}
