package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.SchemeMaster;

@Component
public class SchemeMasterItemProcessor implements ItemProcessor<SchemeMaster, SchemeMaster>{

  @Override
  public SchemeMaster process(SchemeMaster item) throws Exception {
    return item;
  }

}
