package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.ManufacturerMaster;
import com.innoviti.emi.entity.master.ManufacturerMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;

public class ManufacturerMasterRowMapper implements FieldSetMapper<ManufacturerMaster> {

  @Override
  public ManufacturerMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    ManufacturerMaster manufacturerMaster = new ManufacturerMaster();

    ManufacturerMasterComposite composite = new ManufacturerMasterComposite();
    composite.setManufacturerId(fieldSet.readInt(0));
    composite.setCrtupdDate(JobServiceImpl.batchDate);

    manufacturerMaster.setManufacturerDesc(fieldSet.readString(1));
    manufacturerMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    manufacturerMaster.setCrtupdStatus('N');
    manufacturerMaster.setCrtupdUser("Batch");
    manufacturerMaster.setRecordActive(false);
    manufacturerMaster.setManufacturerMasterComposite(composite);
    return manufacturerMaster;
  }
}
