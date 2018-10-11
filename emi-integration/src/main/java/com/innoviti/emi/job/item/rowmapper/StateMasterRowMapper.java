package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.StateMaster;
import com.innoviti.emi.entity.master.StateMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;


public class StateMasterRowMapper implements FieldSetMapper<StateMaster> {

  @Override
  public StateMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    StateMaster stateMaster = new StateMaster();
    StateMasterComposite composite = new StateMasterComposite();
    composite.setCrtupdDate(JobServiceImpl.batchDate);
    composite.setStateId(fieldSet.readInt(0));
    stateMaster.setStateDescription(fieldSet.readString(1));
    stateMaster.setStateMasterComposite(composite);
    stateMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    stateMaster.setCrtupdStatus('N');
    stateMaster.setCrtupdUser("Batch");
    stateMaster.setRecordActive(false);
    return stateMaster;
  }
}
