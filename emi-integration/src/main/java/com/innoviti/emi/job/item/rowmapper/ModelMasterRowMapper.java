package com.innoviti.emi.job.item.rowmapper;

import java.math.BigDecimal;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.ModelMaster;
import com.innoviti.emi.entity.master.ModelMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;
import com.innoviti.emi.util.Util;


public class ModelMasterRowMapper implements FieldSetMapper<ModelMaster> {

  @Override
  public ModelMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    ModelMaster modelMaster = new ModelMaster();

    ModelMasterComposite composite = new ModelMasterComposite();
    composite.setModelId(fieldSet.readInt(0));
    composite.setCrtupdDate(JobServiceImpl.batchDate);

    modelMaster.setModelNumber(fieldSet.readString(1));
    modelMaster.setCategoryId(fieldSet.readInt(2));
    modelMaster.setManufacturerId(fieldSet.readInt(3));
    modelMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    modelMaster.setCrtupdStatus('N');
    modelMaster.setCrtupdUser("Batch");
    modelMaster.setRecordActive(false);
    modelMaster.setModelExpiryDate(Util.getDateFormat(fieldSet.readString(5)));
    BigDecimal sellingPrice = fieldSet.readBigDecimal(4);
    if (sellingPrice == null) {
      throw new IllegalArgumentException("Selling price null");
    }
    modelMaster.setSellingPrice(sellingPrice);
    modelMaster.setSizeId(fieldSet.readString(6));
    modelMaster.setMake(fieldSet.readString(7));
    modelMaster.setModelMasterComposite(composite);
    return modelMaster;
  }
}
