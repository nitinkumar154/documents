package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.SchemeModelMaster;
import com.innoviti.emi.entity.master.SchemeModelMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;


public class SchemeModelMasterRowMapper implements FieldSetMapper<SchemeModelMaster> {

  @Override
  public SchemeModelMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    SchemeModelMaster schemeModelMaster = new SchemeModelMaster();
    SchemeModelMasterComposite id = new SchemeModelMasterComposite();
    id.setSchemeId(fieldSet.readInt(0));
    id.setModelId(fieldSet.readString(1));
    id.setManufacturer(fieldSet.readString(2));
    id.setCategoryId(fieldSet.readString(3));
    id.setCrtupdDate(JobServiceImpl.batchDate);
    schemeModelMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    schemeModelMaster.setCrtupdStatus('N');
    schemeModelMaster.setCrtupdUser("Batch");
    schemeModelMaster.setRecordActive(false);
    schemeModelMaster.setSchemeModelMasterComposite(id);
    return schemeModelMaster;
  }
}
