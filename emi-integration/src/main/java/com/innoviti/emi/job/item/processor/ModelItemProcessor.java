package com.innoviti.emi.job.item.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.column.mapper.impl.ModelMasterColumnMapper;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.master.ModelMaster;

@Component
@StepScope
public class ModelItemProcessor implements ItemProcessor<ModelMaster, Model> {

  @Autowired
  private ModelMasterColumnMapper modelMasterColumnMapper;

  @Override
  public Model process(ModelMaster item) throws Exception {
    return modelMasterColumnMapper.mapColumn(item);
  }

}
