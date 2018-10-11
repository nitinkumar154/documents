package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.CityMaster;
import com.innoviti.emi.entity.master.CityMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;


public class CityMasterRowMapper implements FieldSetMapper<CityMaster> {

  @Override
  public CityMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    CityMaster cityMaster = new CityMaster();
    CityMasterComposite composite = new CityMasterComposite();
    composite.setCityId(fieldSet.readString(0));
    composite.setCrtupdDate(JobServiceImpl.batchDate);
    cityMaster.setCityName(fieldSet.readString(1));
    cityMaster.setStateId(fieldSet.readInt(2, 99));
    cityMaster.setCityType(fieldSet.readString(3));
    cityMaster.setRiskPlloc(fieldSet.readString(4));
    cityMaster.setCityTypeId(fieldSet.readInt(5, 99));
    cityMaster.setCityCategory(fieldSet.readString(6));
    cityMaster.setCityMasterComposite(composite);
    cityMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    cityMaster.setCrtupdStatus('N');
    cityMaster.setCrtupdUser("Batch");
    cityMaster.setRecordActive(false);
    return cityMaster;
  }
}
