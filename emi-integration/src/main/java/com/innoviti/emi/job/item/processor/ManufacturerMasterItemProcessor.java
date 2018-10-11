package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.ManufacturerMaster;

@Component
public class ManufacturerMasterItemProcessor
    implements ItemProcessor<ManufacturerMaster, ManufacturerMaster> {

  @Override
  public ManufacturerMaster process(ManufacturerMaster item) throws Exception {
    return item;
  }

}
