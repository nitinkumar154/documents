package com.innoviti.emi.job.item.rowmapper;

import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.entity.master.SchemeMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;
import com.innoviti.emi.util.Util;

public class SchemeMasterRowMapper implements FieldSetMapper<SchemeMaster> {

  @Override
  public SchemeMaster mapFieldSet(FieldSet fieldSet) throws BindException {

    SchemeMaster schemeMaster = new SchemeMaster();
    SchemeMasterComposite composite = new SchemeMasterComposite();
    composite.setCrtupdDate(JobServiceImpl.batchDate);
    composite.setSchemeId(fieldSet.readInt(0));
    schemeMaster.setSchemeDescription(fieldSet.readString(1));
    schemeMaster.setTenure(fieldSet.readString(2));
    schemeMaster.setProcessingFee(fieldSet.readString(3));
    schemeMaster.setProduct(fieldSet.readString(4));
    schemeMaster.setAdvanceEmi(fieldSet.readString(5));
    schemeMaster.setDealerSubvention(fieldSet.readString(6));
    schemeMaster.setManfacturerSubvention(fieldSet.readString(7));
    schemeMaster.setInterestRate(fieldSet.readDouble(8));

    String str1 = fieldSet.readString(9);
    Date startDate = Util.getDateFormat(str1);
    schemeMaster.setSchemeStartDate(startDate);

    String str2 = fieldSet.readString(10);
    Date endDate = Util.getDateFormat(str2);
    schemeMaster.setSchemeExpiryDate(endDate);

    schemeMaster.setPortalDescription(fieldSet.readString(11));
    schemeMaster.setMaxAmount(fieldSet.readBigDecimal(12));
    schemeMaster.setMinAmount(fieldSet.readBigDecimal(13));
    schemeMaster.setGeneralScheme(fieldSet.readString(14));
    schemeMaster.setSpecialScheme(fieldSet.readString(15));
    schemeMaster.setDealerMapping(fieldSet.readString(16));
    schemeMaster.setModelMapping(fieldSet.readString(17));
    schemeMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    schemeMaster.setCrtupdUser("Batch");
    schemeMaster.setCrtupdStatus('N');
    schemeMaster.setRecordActive(false);
    schemeMaster.setSchemeMasterComposite(composite);
    return schemeMaster;
  }

}
