package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.column.mapper.impl.ManufacturerMasterColumnMapper;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.master.ManufacturerMaster;

@Component
public class ManufacturerItemProcessor implements ItemProcessor<ManufacturerMaster, Manufacturer> {
  
  @Autowired
  private ManufacturerMasterColumnMapper manufacturerMasterColumnMapper;

  @Override
  public Manufacturer process(ManufacturerMaster item) throws Exception {
    return manufacturerMasterColumnMapper.mapColumn(item);
  }

}
