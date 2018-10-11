package com.innoviti.emi.entity.column.mapper.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.ManufacturerComposite;
import com.innoviti.emi.entity.master.ManufacturerMaster;
import com.innoviti.emi.repository.core.ManufacturerRepository;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;

@Component
public class ManufacturerMasterColumnMapper
    implements ColumnMapper<ManufacturerMaster, Manufacturer> {

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  private ManufacturerRepository manufacturerRepository;
  
  @Override
  public Manufacturer mapColumn(ManufacturerMaster manufacturerMaster) {
    
    String manufacturerId = String.valueOf(manufacturerMaster.getManufacturerMasterComposite().getManufacturerId());
    
    Manufacturer foundManufacturer = manufacturerRepository.findTop1ByBajajManufacturerCode(manufacturerId);
    String innoManufaturerCode = null;
    if(foundManufacturer != null){
      innoManufaturerCode = foundManufacturer.getManufacturerComposite().getInnoManufacturerCode();
    }
    else{
      SequenceType sequenceType = SequenceType.MANUFACTURER;
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(sequenceType.getSequenceName());
      String newInsertId = String.valueOf(seqNum);
      innoManufaturerCode =
          sequenceType.getSequenceIdentifier() + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);
    }
    Manufacturer manufacturer = new Manufacturer();
    manufacturer.setBajajManufacturerCode(
        manufacturerMaster.getManufacturerMasterComposite().getManufacturerId().toString());
    String manufacturerDesc = manufacturerMaster.getManufacturerDesc();
    manufacturer.setManufacturerDesc(manufacturerDesc);
    if (manufacturerDesc.length() < 50) {
      manufacturer.setManufacturerDisplayName(manufacturerDesc);
    } else {
      manufacturer.setManufacturerDisplayName(manufacturerDesc.substring(0, 49));
    }

    manufacturer.setRecordActive(true);
    manufacturer.setCrtupdReason(manufacturerMaster.getCrtupdReason());
    manufacturer.setCrtupdStatus("N");
    manufacturer.setCrtupdUser(manufacturerMaster.getCrtupdUser());
    manufacturer.setCrtupdDate(new Date());
    manufacturer.setInnoManufacturerCode(
        manufacturerMaster.getManufacturerMasterComposite().getManufacturerId().toString());
    ManufacturerComposite manufacturerComposite = new ManufacturerComposite();
    manufacturerComposite.setInnoManufacturerCode(innoManufaturerCode);
    manufacturerComposite
        .setCrtupdDate(manufacturerMaster.getManufacturerMasterComposite().getCrtupdDate());
    manufacturer.setManufacturerComposite(manufacturerComposite);
    return manufacturer;
  }
}
