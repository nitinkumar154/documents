package com.innoviti.emi.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.constant.CashbackType;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.IssuerBankComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.service.core.IssuerBankService;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class IssuerBankTest extends SetupWithJPA{
  
  @Autowired
  public IssuerBankService issuerBankService;
  
  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  
  private IssuerBank createIssuerBank(){
    IssuerBankComposite issuerBankComposite = new IssuerBankComposite();
    issuerBankComposite.setCrtupdDate(new Date());
    issuerBankComposite.setInnoIssuerBankCode("41");
    IssuerBank issuerBank = new IssuerBank();
    issuerBank.setCrtupdDate(new Date());
    issuerBank.setInnoIssuerBankCode("41");
    issuerBank.setCrtupdReason("configuration");
    issuerBank.setCrtupdStatus("T");
    issuerBank.setCrtupdUser("Admin");
    issuerBank.setRecordActive(true);
    issuerBank.setIssuerBankComposite(issuerBankComposite);
    issuerBank.setIssuerBankDesc("ICICI");
    issuerBank.setIssuerBankDisclaimer("ICICI");
    issuerBank.setIssuerBankDisplayName("ICICI");
    issuerBank.setIssuerDefaultCashbackFlag(CashbackType.POST);
    issuerBank.setIssuerMinEmiAmount(new BigDecimal(200));
    issuerBank.setEmiBankCode(12);
   
    return issuerBank;
  }
  
  @Test
  public void createIssuerBankTest(){
    IssuerBank issuerBank = createIssuerBank();
    IssuerBank createdIssuerBank = issuerBankService.create(issuerBank);
    Assert.assertNotNull(createdIssuerBank);
    Assert.assertEquals("Issuer bank not created", issuerBank.getIssuerBankComposite(), createdIssuerBank.getIssuerBankComposite());
  }
  @Test
  public void findIssuerBankTest(){
    IssuerBank issuerBank = createIssuerBank();
    IssuerBank createdIssuerBank = issuerBankService.create(issuerBank);
    Assert.assertNotNull(createdIssuerBank);
    Assert.assertEquals("Issuer bank not created", issuerBank.getIssuerBankComposite(), createdIssuerBank.getIssuerBankComposite());
    
    IssuerBank foundIssuerBank = issuerBankService.findById(issuerBank.getIssuerBankComposite());
    Assert.assertNotNull(foundIssuerBank);
    Assert.assertEquals("Issuer bank not found", foundIssuerBank.getIssuerBankComposite(), createdIssuerBank.getIssuerBankComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteIssuerBankTest(){
    IssuerBank issuerBank = createIssuerBank();
    IssuerBank createdIssuerBank = issuerBankService.create(issuerBank);
    Assert.assertNotNull(createdIssuerBank);
    Assert.assertEquals("Issuer bank not created", issuerBank.getIssuerBankComposite(), createdIssuerBank.getIssuerBankComposite());
    
    issuerBankService.deleteById(createdIssuerBank.getIssuerBankComposite());
    issuerBankService.findById(issuerBank.getIssuerBankComposite());
  }
}
