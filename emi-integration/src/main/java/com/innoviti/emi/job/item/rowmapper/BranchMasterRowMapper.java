package com.innoviti.emi.job.item.rowmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.master.BranchMaster;
import com.innoviti.emi.entity.master.BranchMasterComposite;
import com.innoviti.emi.service.impl.core.JobServiceImpl;

public class BranchMasterRowMapper implements FieldSetMapper<BranchMaster> {

  @Override
  public BranchMaster mapFieldSet(FieldSet fieldSet) throws BindException {
    BranchMaster branchMaster = new BranchMaster();
    BranchMasterComposite composite = new BranchMasterComposite();
    composite.setBranchCode(fieldSet.readString(0));
    composite.setCrtupdDate(JobServiceImpl.batchDate);
    branchMaster.setBranchMasterComposite(composite);
    branchMaster.setMainBranch(fieldSet.readString(1));
    branchMaster.setBranchName(fieldSet.readString(2));
    branchMaster.setBranchAddr1(fieldSet.readString(3));
    branchMaster.setBranchAddr2(fieldSet.readString(4));
    branchMaster.setBranchAddr3(fieldSet.readString(5));
    branchMaster.setBranchAddr4(fieldSet.readString(6));
    branchMaster.setBranchRegionCode(fieldSet.readString(7));
    branchMaster.setBranchZoneCode(fieldSet.readString(8));
    branchMaster.setBranchStateCode(fieldSet.readString(9));
    branchMaster.setBranchType(fieldSet.readString(10));
    branchMaster.setBankCode(fieldSet.readString(11));
    branchMaster.setBranchCity(fieldSet.readString(12));
    branchMaster.setBranchPin(fieldSet.readString(13));
    branchMaster.setBranchState(fieldSet.readString(14));
    branchMaster.setBranchEmailId(fieldSet.readString(15));
    branchMaster.setCorporateId(fieldSet.readString(16));
    branchMaster.setMainBranchFlag(fieldSet.readString(17));
    branchMaster.setBranchPhoneNumber(fieldSet.readString(18));
    branchMaster.setBranchContactPersonName(fieldSet.readString(19));
    branchMaster.setBranchAreaCode(fieldSet.readString(20));
    branchMaster.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    branchMaster.setCrtupdUser("Batch");
    branchMaster.setRecordActive(false);
    branchMaster.setCrtupdStatus('N');
    return branchMaster;
  }
}
