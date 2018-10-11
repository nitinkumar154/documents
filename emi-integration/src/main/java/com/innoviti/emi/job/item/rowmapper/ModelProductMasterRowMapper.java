package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.ModelProductMaster;
import com.innoviti.emi.entity.master.ModelProductMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;

public class ModelProductMasterRowMapper implements FieldSetMapper<ModelProductMaster> {

  @Override
  public ModelProductMaster mapFieldSet(FieldSet fieldSet) {
    ModelProductMaster modelProductMaster = new ModelProductMaster();
    ModelProductMasterComposite id = new ModelProductMasterComposite();
    id.setModelId(fieldSet.readInt(0));
    id.setCode(fieldSet.readString(1));
    id.setCrtupdDate(JobServiceImpl.batchDate);
    modelProductMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    modelProductMaster.setCrtupdStatus('N');
    modelProductMaster.setCrtupdUser("Batch");
    modelProductMaster.setRecordActive(false);
    modelProductMaster.setModelProductMasterComposite(id);
    return modelProductMaster;
  }
}
