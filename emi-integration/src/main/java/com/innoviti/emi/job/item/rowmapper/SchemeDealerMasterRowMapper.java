package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.SchemeDealerMaster;
import com.innoviti.emi.entity.master.SchemeDealerMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;


public class SchemeDealerMasterRowMapper implements FieldSetMapper<SchemeDealerMaster> {

  @Override
  public SchemeDealerMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    SchemeDealerMaster schemeDealerMaster = new SchemeDealerMaster();
    SchemeDealerMasterComposite id = new SchemeDealerMasterComposite();
    id.setSchemeId(fieldSet.readInt(0));
    id.setSupplierId(fieldSet.readInt(1));
    id.setCrtupdDate(JobServiceImpl.batchDate);
    schemeDealerMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    schemeDealerMaster.setCrtupdStatus('N');
    schemeDealerMaster.setCrtupdUser("Batch");
    schemeDealerMaster.setRecordActive(false);
    schemeDealerMaster.setSchemeDealerMasterComposite(id);
    return schemeDealerMaster;
  }
}
