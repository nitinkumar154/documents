package com.innoviti.emi.service.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.ManufacturerComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface ManufacturerService extends CrudService<Manufacturer, ManufacturerComposite> {

  Manufacturer findManufacturerByInnoManufacturerCode(String innoManufacturerCode);

  Iterable<Manufacturer> findAll(Predicate predicate);
  
  Page<Manufacturer> findAllManufacturers(Pageable pageable);

  Page<Manufacturer> findAllManufacturersForDataMovement(String recordStatus, Pageable pageable);

  List<Manufacturer> autoCompleteManufacturers(String term);
  
}
