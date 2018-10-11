package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.DealerMaster;
import com.innoviti.emi.entity.master.DealerMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;


public class DealerMasterRowMapper implements FieldSetMapper<DealerMaster> {

  @Override
  public DealerMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    DealerMaster dealerMaster = new DealerMaster();
    DealerMasterComposite composite = new DealerMasterComposite();
    composite.setSupplierId(fieldSet.readInt(0));
    composite.setCrtupdDate(JobServiceImpl.batchDate);
    dealerMaster.setSupplierDesc(fieldSet.readString(1));
    dealerMaster.setSupplierDealerFlag(fieldSet.readString(2));
    dealerMaster.setDealerGroupId(fieldSet.readInt(3));
    dealerMaster.setDealerGroupDesc(fieldSet.readString(4));
    dealerMaster.setPan(fieldSet.readString(5));
    dealerMaster.setContactPerson(fieldSet.readString(6));
    dealerMaster.setAddress1(fieldSet.readString(7));
    dealerMaster.setAddress2(fieldSet.readString(8));
    dealerMaster.setAddress3(fieldSet.readString(9));
    dealerMaster.setAddress4(fieldSet.readString(10));
    dealerMaster.setStdIsd(fieldSet.readString(11));
    dealerMaster.setPhone1(fieldSet.readString(12));
    dealerMaster.setMobile(fieldSet.readString(13));
    String city = fieldSet.readString(14);
    if (city == null || city.isEmpty()) {
      throw new IllegalArgumentException("City null");
    }
    dealerMaster.setCity(city);
    dealerMaster.setDealerEmail(fieldSet.readString(15));
    dealerMaster.setClassification(fieldSet.readString(16));
    dealerMaster.setLoyalityProgApplicable(fieldSet.readString(17));
    dealerMaster.setSupplierType(fieldSet.readString(18));
    dealerMaster.setSupplierBranch(fieldSet.readString(19));
    dealerMaster.setAssetCatgId(fieldSet.readInt(3));
    dealerMaster.setProcessType(fieldSet.readString(21));
    dealerMaster.setPreferredLimitFlag(fieldSet.readString(22));
    dealerMaster.setStoreId(fieldSet.readString(23));
    dealerMaster.setCoBrandLimitFlag(fieldSet.readString(24));
    dealerMaster.setCoBrandCardCode(fieldSet.readString(25));
    dealerMaster.setServingCities(fieldSet.readString(26));
    String state = fieldSet.readString(27);
    if (state == null || state.isEmpty()) {
      throw new IllegalArgumentException("State null");
    }
    dealerMaster.setState(fieldSet.readString(27));
    dealerMaster.setZipCode(fieldSet.readInt(3));
    dealerMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    dealerMaster.setCrtupdStatus('N');
    dealerMaster.setCrtupdUser("Batch");
    dealerMaster.setRecordActive(false);
    dealerMaster.setDealerMasterComposite(composite);
    return dealerMaster;
  }
}
