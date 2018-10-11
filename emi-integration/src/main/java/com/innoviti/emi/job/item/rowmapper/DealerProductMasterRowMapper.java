package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.DealerProductMaster;
import com.innoviti.emi.entity.master.DealerProductMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;


public class DealerProductMasterRowMapper implements FieldSetMapper<DealerProductMaster> {

  @Override
  public DealerProductMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    DealerProductMaster dealerProductMaster = new DealerProductMaster();
    DealerProductMasterComposite id = new DealerProductMasterComposite();
    id.setSupplierId(fieldSet.readInt(0));
    id.setCode(fieldSet.readString(1));
    id.setCrtupdDate(JobServiceImpl.batchDate);
    dealerProductMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    dealerProductMaster.setCrtupdStatus('N');
    dealerProductMaster.setCrtupdUser("Batch");
    dealerProductMaster.setRecordActive(false);
    dealerProductMaster.setDealerProductMasterComposite(id);
    return dealerProductMaster;
  }
}
