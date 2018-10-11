package com.innoviti.emi.entity.column.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.constant.CashbackType;
import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeComposite;
import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.IssuerBankRepository;
import com.innoviti.emi.repository.core.SchemeRepository;
import com.innoviti.emi.repository.core.TenureRepository;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.service.core.TenureService;
import com.innoviti.emi.service.impl.core.JobServiceImpl;
import com.innoviti.emi.util.Util;

@Component
public class SchemeMasterCoulmnMapper implements ColumnMapper<SchemeMaster, Scheme> {

  @Autowired
  private TenureRepository tenureRepository;

  @Autowired
  private IssuerBankRepository issuerBankRepository;

  @Autowired
  private TenureService tenureService;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;
  
  @Autowired
  private SchemeRepository schemeRepository;

  @Override
  public Scheme mapColumn(SchemeMaster schemeMaster) {
    Integer bajajSchemeCode = schemeMaster.getSchemeMasterComposite().getSchemeId();
   
    Scheme scheme = new Scheme();
    scheme.setAdvanceEmi(schemeMaster.getAdvanceEmi());
    scheme.setProcessingFees(schemeMaster.getProcessingFee().isEmpty()
        || schemeMaster.getProcessingFee().equalsIgnoreCase(" ") ? "0"
            : schemeMaster.getProcessingFee());
    scheme
        .setBajajIssuerSchemeCode(bajajSchemeCode.toString());
    String protalDesc = schemeMaster.getPortalDescription();
    if (protalDesc == null || protalDesc.isEmpty()) {
      scheme.setSchemeDisplayName(schemeMaster.getSchemeDescription());
    } else {
      scheme.setSchemeDisplayName(protalDesc);
    }

    scheme.setRoi(String.valueOf(schemeMaster.getInterestRate()));
    scheme.setMaxAmount(schemeMaster.getMaxAmount());
    scheme.setMinAmount(schemeMaster.getMinAmount());
    scheme.setGenScheme(schemeMaster.getGeneralScheme());
    scheme.setSchemeStartDate(schemeMaster.getSchemeStartDate());
    scheme.setSchemeEndDate(schemeMaster.getSchemeExpiryDate());

    IssuerBank issuerBank = issuerBankRepository.findTop1ByEmiBankCodeOrderByIssuerBankCompositeCrtupdDateDesc(12);
    if (issuerBank == null) {
      throw new NotFoundException("No issuer");
    }
    scheme.setIssuerBank(issuerBank);
    scheme.setCashbackFlag(issuerBank.getIssuerDefaultCashbackFlag());

    Tenure tenure = tenureRepository.findTop1ByTenureMonthOrderByTenureCompositeCrtupdDateDesc(schemeMaster.getTenure());
    if (tenure == null) {
      tenure = tenureService.saveTenureForBatch(schemeMaster.getTenure());
    }
    Scheme foundScheme = schemeRepository.findTop1ByBajajIssuerSchemeCode(String.valueOf(bajajSchemeCode));
    String innoIssuerSchemeCode = null;
    if(foundScheme != null){
      innoIssuerSchemeCode = foundScheme.getSchemeComposite().getInnoIssuerSchemeCode();
    }
    else{
      SequenceType sequenceType = SequenceType.SCHEME;
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(sequenceType.getSequenceName());
      String newInsertId = String.valueOf(seqNum);
      innoIssuerSchemeCode =
          sequenceType.getSequenceIdentifier() + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);
    }
    SchemeComposite schemeComposite = new SchemeComposite();
    schemeComposite.setInnoIssuerSchemeCode(innoIssuerSchemeCode);
    schemeComposite.setCrtupdDate(schemeMaster.getSchemeMasterComposite().getCrtupdDate());
    scheme.setSchemeComposite(schemeComposite);

    scheme.setTenure(tenure);
    scheme.setCashbackFlag(CashbackType.PRE);
    scheme.setRecordActive(true);
    scheme.setCrtupdReason(schemeMaster.getCrtupdReason());
    scheme.setCrtupdUser(schemeMaster.getCrtupdUser());
    scheme.setCrtupdStatus("N");
    scheme.setCrtupdDate(JobServiceImpl.batchDate);
    return scheme;
  }
}
