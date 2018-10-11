package com.innoviti.emi.job.item.reader;

import java.util.Deque;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.innoviti.emi.entity.column.mapper.impl.GeneralSchemeModelColumnMapper;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.SchemeModel;

public class GeneralSchemeModelMappingReader extends BaseJpaItemReader<Model> implements ItemReader<SchemeModel>{
  
  private GeneralSchemeModelColumnMapper generalSchemeModelColumnMapper;
  private Deque<SchemeModel> schemeModels = null;
  private JpaPagingPartitionerItemReader<Model> jpaPagingPartitionerItemReader;
  
  public GeneralSchemeModelMappingReader(JpaPagingPartitionerItemReader<Model> jpaPagingPartitionerItemReader) {
    super(jpaPagingPartitionerItemReader);
    this.jpaPagingPartitionerItemReader = jpaPagingPartitionerItemReader;
  }
  
  @Override
  public SchemeModel read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if(schemeModels == null || schemeModels.isEmpty()){
      Model model = this.jpaPagingPartitionerItemReader.read();
      if(model == null){
        return null;
      }
      schemeModels = generalSchemeModelColumnMapper.mapColumn(model);
    }
   
    if(schemeModels != null && !schemeModels.isEmpty()){
      return schemeModels.poll();
    }
    return new SchemeModel();
  }

  public GeneralSchemeModelColumnMapper getGeneralSchemeModelColumnMapper() {
    return generalSchemeModelColumnMapper;
  }

  public void setGeneralSchemeModelColumnMapper(
      GeneralSchemeModelColumnMapper generalSchemeModelColumnMapper) {
    this.generalSchemeModelColumnMapper = generalSchemeModelColumnMapper;
  }
  
}
