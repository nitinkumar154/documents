package com.innoviti.emi.database.csv;

import java.util.Deque;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.job.item.reader.BaseJpaItemReader;
import com.innoviti.emi.job.item.reader.JpaPagingPartitionerItemReader;

public class GeneralSchemeReader extends BaseJpaItemReader<SchemeModelTerminal> implements ItemReader<CSVSchemeModelTerminalModel>{

  private JpaPagingPartitionerItemReader<SchemeModelTerminal> jpaPagingPartitionerItemReader;
  private Deque<CSVSchemeModelTerminalModel> csvDataList = null;
  private GeneralSchemeMapper generalSchemeMapper;
  
  public GeneralSchemeReader(
      JpaPagingPartitionerItemReader<SchemeModelTerminal> jpaPagingPartitionerItemReader) {
    super(jpaPagingPartitionerItemReader);
    this.jpaPagingPartitionerItemReader = jpaPagingPartitionerItemReader;
  }

  @Override
  public CSVSchemeModelTerminalModel read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if(csvDataList == null || csvDataList.isEmpty()){
      SchemeModelTerminal schemeModelTerminal = this.jpaPagingPartitionerItemReader.read();
      if(schemeModelTerminal == null){
        return null;
      }
      csvDataList = generalSchemeMapper.mapColumn(schemeModelTerminal);
    }
   
    if(csvDataList != null && !csvDataList.isEmpty()){
      return csvDataList.poll();
    }
    return new CSVSchemeModelTerminalModel();
  }

  public GeneralSchemeMapper getGeneralSchemeMapper() {
    return generalSchemeMapper;
  }

  public void setGeneralSchemeMapper(GeneralSchemeMapper generalSchemeMapper) {
    this.generalSchemeMapper = generalSchemeMapper;
  }
  
}
