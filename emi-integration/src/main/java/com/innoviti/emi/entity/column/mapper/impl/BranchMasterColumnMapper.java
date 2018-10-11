package com.innoviti.emi.entity.column.mapper.impl;

import org.springframework.stereotype.Component;

import com.innoviti.emi.constant.CashbackType;
import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.IssuerBankComposite;
import com.innoviti.emi.entity.master.BranchMaster;

@Component
public class BranchMasterColumnMapper implements ColumnMapper<BranchMaster, IssuerBank> {

  @Override
  public IssuerBank mapColumn(BranchMaster branchMaster) {
    IssuerBank bank = new IssuerBank();
    bank.setIssuerBankDisplayName(branchMaster.getBranchName());
    bank.setIssuerBankDisclaimer("NA");
    bank.setIssuerTermsConditions(null);
    bank.setIssuerDefaultCashbackFlag(CashbackType.POST);
    bank.setIssuerMinEmiAmount(null);
    bank.setRecordActive(true);
    bank.setCrtupdReason(branchMaster.getCrtupdReason());
    bank.setCrtupdStatus("N");
    bank.setCrtupdUser(branchMaster.getCrtupdUser());
    IssuerBankComposite issuerBankComposite = new IssuerBankComposite();
    issuerBankComposite.setInnoIssuerBankCode(branchMaster.getBankCode());
    issuerBankComposite.setCrtupdDate(branchMaster.getBranchMasterComposite().getCrtupdDate());
    bank.setIssuerBankComposite(issuerBankComposite);

    return bank;
  }
}
