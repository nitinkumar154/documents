package com.innoviti.emi.service.core;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface SchemeModelService extends CrudService<SchemeModel, SchemeModelComposite> {

  SchemeModel findSchemeModelByInnoSchemeModelCode(String innoSchemeModelCode);

  public Iterable<SchemeModel> findAll(Predicate predicate);

  Page<SchemeModel> findAllSchemeModel(String bajajFlag, Pageable pageable);

  Page<SchemeModel> findAllSchemeModelsForDataMovement(String recordStatus, Pageable pageable);
  
  List<SchemeModel> getAllSchemeModelForScheme(Scheme scheme);
}
