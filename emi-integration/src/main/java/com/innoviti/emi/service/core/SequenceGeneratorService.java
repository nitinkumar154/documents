package com.innoviti.emi.service.core;


import com.innoviti.emi.entity.core.SequenceGenerator;
import com.innoviti.emi.service.CrudService;

public interface SequenceGeneratorService extends CrudService<SequenceGenerator, String>{

  int updateSequenceGenerator(String seqName);

  long getLastInsertedId();

  long getSeqNumber(String seqName);
  
}
