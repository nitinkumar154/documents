package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.column.mapper.impl.SchemeMasterCoulmnMapper;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.master.SchemeMaster;

@Component
public class SchemeItemProcessor implements ItemProcessor<SchemeMaster, Scheme>{
  
  @Autowired
  private SchemeMasterCoulmnMapper schemeMasterCoulmnMapper;
  
  @Override
  public  Scheme process(SchemeMaster item) throws Exception {
    return schemeMasterCoulmnMapper.mapColumn(item);
  }

}
