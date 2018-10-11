package com.innoviti.emi.job.item.processor;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.core.SchemeModelTerminal;


@Component
@StepScope
public class SchemeModelTerminalItemProcessor implements ItemProcessor<SchemeModelTerminal, SchemeModelTerminal> {

  @Override
  public SchemeModelTerminal process(SchemeModelTerminal schemeModelTerminal) throws Exception {
    if(schemeModelTerminal.getSchemeModelTerminalComposite() == null){
      return null;
    }
    return schemeModelTerminal;
  }

}
