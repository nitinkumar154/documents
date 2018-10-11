package com.innoviti.emi.service.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.SerialNo;
import com.innoviti.emi.entity.core.SerialNoComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface SerialNoService extends CrudService<SerialNo, SerialNoComposite> {

  SerialNo getSerialNoByInnoModelSerialNumber(String innoModelSerialNumber);

  Iterable<SerialNo> findAll(Predicate predicate);

  Page<SerialNo> findAllSerialNo(Pageable pageable);

  Page<SerialNo> findAllSerialNoForDataMovement(String recordStatus, Pageable pageable);

}
