package com.innoviti.emi.service.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.entity.core.TenureComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface TenureService extends CrudService<Tenure, TenureComposite> {

  Tenure findTenureByInnoTenureCode(String innoTenureCode);

  Iterable<Tenure> findAll(Predicate predicate);

  Page<Tenure> findAllTenures(Pageable pageable);

  Page<Tenure> findAllTenuresForDataMovement(String recordStatus, Pageable pageable);

  List<Tenure> autoCompleteTenures(String term);
 
  Tenure saveTenureForBatch(String tenureMonth);

}
