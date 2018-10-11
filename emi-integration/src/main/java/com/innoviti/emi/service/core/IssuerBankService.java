package com.innoviti.emi.service.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.IssuerBankComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface IssuerBankService extends CrudService<IssuerBank, IssuerBankComposite> {

  IssuerBank findIssuerBankByInnoIssuerBankCode(String innoIssuerBankCode);

  Iterable<IssuerBank> findAll(Predicate predicate);

  Page<IssuerBank> findAllIssuerBank(Pageable pageable);

  Page<IssuerBank> findAllIssuerBankForDataMovement(String recordStatus, Pageable pageable);
}
