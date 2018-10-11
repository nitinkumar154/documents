package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.ModelProductMaster;

@Component
public class ModelProductMasterProcessor
    implements ItemProcessor<ModelProductMaster, ModelProductMaster> {

  @Override
  public ModelProductMaster process(ModelProductMaster item) throws Exception {
    return item;
  }

}
