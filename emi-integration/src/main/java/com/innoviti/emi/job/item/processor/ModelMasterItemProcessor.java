package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.ModelMaster;

@Component
public class ModelMasterItemProcessor implements ItemProcessor<ModelMaster, ModelMaster> {

  @Override
  public ModelMaster process(ModelMaster item) throws Exception {
    return item;
  }

}
