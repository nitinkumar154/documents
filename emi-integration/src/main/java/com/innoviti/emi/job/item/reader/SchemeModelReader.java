package com.innoviti.emi.job.item.reader;

import java.util.Deque;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.innoviti.emi.entity.column.mapper.impl.SchemeModelColumnMapper;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.master.SchemeMaster;

public class SchemeModelReader extends BaseJpaItemReader<SchemeMaster> implements ItemReader<SchemeModel>{
  
  private JpaPagingPartitionerItemReader<SchemeMaster> jpaPagingPartitionerItemReader;
  private SchemeModelColumnMapper schemeModelColumnMapper;
  private Deque<SchemeModel> schemeModelList = null;
  public SchemeModelReader(JpaPagingPartitionerItemReader<SchemeMaster> jpaPagingPartitionerItemReader) {
    super(jpaPagingPartitionerItemReader);
    this.jpaPagingPartitionerItemReader = jpaPagingPartitionerItemReader;
  }
  @Override
  public SchemeModel read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if(schemeModelList == null || schemeModelList .isEmpty()){
      SchemeMaster schemeMaster = jpaPagingPartitionerItemReader.read();
      if(schemeMaster == null){
        return null;
      }
      schemeModelList = schemeModelColumnMapper.mapColumn(schemeMaster);
    }
    if(schemeModelList != null && !schemeModelList.isEmpty()){
      return schemeModelList.pop();
    }
    return new SchemeModel();
  }
  public SchemeModelColumnMapper getSchemeModelColumnMapper() {
    return schemeModelColumnMapper;
  }
  public void setSchemeModelColumnMapper(SchemeModelColumnMapper schemeModelColumnMapper) {
    this.schemeModelColumnMapper = schemeModelColumnMapper;
  }
}
