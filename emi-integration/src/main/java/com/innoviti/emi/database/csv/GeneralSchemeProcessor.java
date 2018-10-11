package com.innoviti.emi.database.csv;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class GeneralSchemeProcessor implements ItemProcessor<CSVSchemeModelTerminalModel, CSVSchemeModelTerminalModel>{

  @Override
  public CSVSchemeModelTerminalModel process(CSVSchemeModelTerminalModel item) throws Exception {
    String innoSchemeModelCode = item.getInnoSchemeModelCode();
    if(innoSchemeModelCode == null || innoSchemeModelCode.isEmpty()){
      return null;
    }
    return item;
  }

}
