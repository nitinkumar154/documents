package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.DealerManufacturerMaster;
import com.innoviti.emi.entity.master.DealerManufacturerMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;


public class DealerManufacturerMasterRowMapper implements FieldSetMapper<DealerManufacturerMaster> {

  @Override
  public DealerManufacturerMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    DealerManufacturerMaster dealerManufacturerMaster = new DealerManufacturerMaster();
    DealerManufacturerMasterComposite dealerManufacturerMasterComposite =
        new DealerManufacturerMasterComposite();
    dealerManufacturerMasterComposite.setSupplierId(fieldSet.readInt(0));
    dealerManufacturerMasterComposite.setManufacturerId(fieldSet.readInt(1));
    dealerManufacturerMasterComposite.setCrtupdDate(JobServiceImpl.batchDate);
    dealerManufacturerMaster
        .setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    dealerManufacturerMaster.setCrtupdStatus('N');
    dealerManufacturerMaster.setCrtupdUser("Batch");
    dealerManufacturerMaster.setRecordActive(false);
    dealerManufacturerMaster
        .setDealerManufacturerMasterComposite(dealerManufacturerMasterComposite);
    return dealerManufacturerMaster;
  }
}
