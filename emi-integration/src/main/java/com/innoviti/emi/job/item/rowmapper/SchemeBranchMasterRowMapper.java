package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.SchemeBranchMaster;
import com.innoviti.emi.entity.master.SchemeBranchMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;

public class SchemeBranchMasterRowMapper implements FieldSetMapper<SchemeBranchMaster> {

  @Override
  public SchemeBranchMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    SchemeBranchMaster schemeBranchMaster = new SchemeBranchMaster();
    SchemeBranchMasterComposite id = new SchemeBranchMasterComposite();
    id.setSchemeId(fieldSet.readInt(0));
    id.setBranchId(fieldSet.readInt(1));
    id.setCrtupdDate(JobServiceImpl.batchDate);
    schemeBranchMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    schemeBranchMaster.setCrtupdStatus('N');
    schemeBranchMaster.setCrtupdUser("Batch");
    schemeBranchMaster.setRecordActive(false);
    schemeBranchMaster.setSchemeBranchMasterComposite(id);
    return schemeBranchMaster;
  }
}
