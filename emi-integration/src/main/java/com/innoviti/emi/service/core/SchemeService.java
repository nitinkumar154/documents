package com.innoviti.emi.service.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface SchemeService extends CrudService<Scheme, SchemeComposite> {

  Scheme findSchemeByInnoIssuerSchemeCode(String innoIssuerSchemeCode);

  Iterable<Scheme> findAll(Predicate predicate);

  Page<Scheme> findAllSchemes(Pageable pageable);

  Page<Scheme> findAllSchemesFromFilter(String bankCode, String tenureCode, Pageable pageable);

  Page<Scheme> findAllSchemesForDataMovement(String recordStatus, Pageable pageable);

  List<Scheme> findAllNonBajajSchemes();

}
