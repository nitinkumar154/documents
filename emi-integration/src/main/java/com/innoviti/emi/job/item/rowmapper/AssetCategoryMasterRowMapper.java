package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.AssetCategoryMaster;
import com.innoviti.emi.entity.master.AssetCategoryMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;


public class AssetCategoryMasterRowMapper implements FieldSetMapper<AssetCategoryMaster> {

  @Override
  public AssetCategoryMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    AssetCategoryMaster assetCategoryMaster = new AssetCategoryMaster();
    AssetCategoryMasterComposite composite = new AssetCategoryMasterComposite();
    composite.setId(fieldSet.readInt(0));
    composite.setCrtupdDate(JobServiceImpl.batchDate);
    assetCategoryMaster.setDescription(fieldSet.readString(1));
    assetCategoryMaster.setCibilCheck(fieldSet.readString(2));
    assetCategoryMaster.setRiskClassification(fieldSet.readString(3));
    assetCategoryMaster.setDigitalFlag(fieldSet.readString(4));
    assetCategoryMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    assetCategoryMaster.setCrtupdStatus('N');
    assetCategoryMaster.setCrtupdUser("Batch");
    assetCategoryMaster.setRecordActive(false);
    assetCategoryMaster.setAssetCategoryMasterComposite(composite);
    return assetCategoryMaster;
  }
}
