package com.innoviti.emi.job.item.reader;

import java.util.Deque;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.innoviti.emi.entity.column.mapper.impl.SchemeModelTerminalColumnMapper;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.model.Supplier;

public class SchemeModelTerminalMappingReader implements ItemReader<SchemeModelTerminal>{
  private SchemeModelTerminalColumnMapper schemeModelTerminalMapper;
  private Deque<SchemeModelTerminal> schemeModelTerminalList = null;
 
  private Supplier supplier;
  
  
  @Override
  public SchemeModelTerminal read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

    if(getSchemeModelTerminalMapper() == null){
      throw new IllegalArgumentException("SchemeModelTerminalColumnMapper must not be null");
    }
    if(schemeModelTerminalList == null || schemeModelTerminalList.isEmpty()){
      Integer supplierId = supplier.getSupplierId();
      if(supplierId == null){
        return null;
      }
      schemeModelTerminalList = getSchemeModelTerminalMapper().mapColumn(supplierId); 
    }
    if(schemeModelTerminalList != null && !schemeModelTerminalList.isEmpty()){
      return schemeModelTerminalList.poll();
    }
    return new SchemeModelTerminal();
  }
  public SchemeModelTerminalColumnMapper getSchemeModelTerminalMapper() {
    return schemeModelTerminalMapper;
  }

  public void setSchemeModelTerminalMapper(
      SchemeModelTerminalColumnMapper schemeModelTerminalMapper) {
    this.schemeModelTerminalMapper = schemeModelTerminalMapper;
  }

  public Supplier getSupplier() {
    return supplier;
  }
  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
    this.supplier.initializeSupplierIdList();
  }
  
}
