package com.innoviti.emi.service.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.ModelComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface ModelService extends CrudService<Model, ModelComposite> {

  public Model findModelByInnoModelCode(String innoModelCode);

  public Iterable<Model> findAll(Predicate predicate);

  public Page<Model> findAllModels(Pageable pageable);

  public Iterable<Model> findAllModelFromModelCodeList(List<String> modelCodeList);

  public Page<Model> findAllModelsFromFilter(String productCode, String manufacturerCode,
      String categoryCode,Pageable pageable);

  public Page<Model> findAllModelsForDataMovement(String recordStatus, Pageable pageable);

  public List<Model> autoCompleteModels(String term);

}
