package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.column.mapper.impl.AssetCategoryMasterColumnMapper;
import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.master.AssetCategoryMaster;

@Component
public class AssetCategoryItemProcessor
    implements ItemProcessor<AssetCategoryMaster, Category> {

  @Autowired
  private AssetCategoryMasterColumnMapper assetCategoryMasterColumnMapper;

  @Override
  public Category process(AssetCategoryMaster item) throws Exception {
    return assetCategoryMasterColumnMapper.mapColumn(item);
  }

}
