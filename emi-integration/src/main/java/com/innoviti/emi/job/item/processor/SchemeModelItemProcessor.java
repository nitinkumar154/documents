package com.innoviti.emi.job.item.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.core.SchemeModel;

@Component
@StepScope
public class SchemeModelItemProcessor implements ItemProcessor<SchemeModel, SchemeModel> {
  
  @Override
  public SchemeModel process(SchemeModel item) throws Exception {
    if(item.getSchemeModelComposite() == null){
      return null;
    }
    return item;
  }

}
