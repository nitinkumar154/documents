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
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeComposite;
import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.service.core.IssuerBankService;
import com.innoviti.emi.service.core.SchemeService;
import com.innoviti.emi.service.core.TenureService;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class SchemeServiceTest extends SetupWithJPA {

  @Autowired
  SchemeService schemeService;
  
  @Autowired
  TenureService tenureService;
  
  @Autowired
  IssuerBankService issuerBankService;
  
  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  private IssuerBank getIssuerBank(){
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
  private Tenure getTenure(){
    Tenure tenure = new Tenure();
    tenure.setTenureDisplayName("Tenure Name");
    tenure.setCrtupdReason("Configuration");
    tenure.setCrtupdStatus("1");
    tenure.setCrtupdUser("Admin");
    tenure.setRecordActive(true);
    tenure.setCrtupdDate(new Date());
    tenure.setInnoTenureCode("232");
    tenure.setTenureMonth("3");
    
    return tenure;
  }
  private Scheme getScheme(){
    Scheme scheme = new Scheme();
    SchemeComposite schemeComposite = new SchemeComposite();

    scheme.setInnoIssuerSchemeCode("Delt123");
    final Date date = new Date();
    scheme.setCrtupdDate(date);

    scheme.setSchemeComposite(schemeComposite);
    scheme.setBajajIssuerSchemeCode("BSch123");
    scheme.setAdvanceEmi("6");
    scheme.setSchemeDisplayName("119/DEL/21/");
    scheme.setCashbackFlag(CashbackType.PRE);
    scheme.setRecordActive(true);
    scheme.setCrtupdReason("config");
    scheme.setCrtupdUser("admin");
    scheme.setCrtupdStatus("N");
    
    return scheme;
  }
  private Scheme saveScheme(){
    Tenure createdTenure = tenureService.create(getTenure());
    Assert.assertNotNull(createdTenure);
    IssuerBank createdIssuerBank = issuerBankService.create(getIssuerBank());
    Assert.assertNotNull(createdIssuerBank);
    
    Scheme scheme = getScheme();
    scheme.setInnoTenureCode(createdTenure.getTenureComposite().getInnoTenureCode());
    scheme.setInnoIssuerBankCode(createdIssuerBank.getIssuerBankComposite().getInnoIssuerBankCode());
    Scheme createdScheme = schemeService.create(scheme);
    Assert.assertNotNull(createdScheme);
    
    return createdScheme;
  }
  @Test
  public void createSchemeTest(){
    saveScheme();
  }
  @Test
  public void findSchemeTest(){
    Scheme createdScheme = saveScheme();
    Scheme foundScheme = schemeService.findById(createdScheme.getSchemeComposite());
    Assert.assertNotNull(foundScheme);
    Assert.assertEquals("Scheme not found", foundScheme.getSchemeComposite(), createdScheme.getSchemeComposite());
  }
  @Test
  public void findSchemeByTest(){
    Scheme createdScheme = saveScheme();
    Scheme foundScheme = schemeService.findSchemeByInnoIssuerSchemeCode(createdScheme.getSchemeComposite().getInnoIssuerSchemeCode());
    Assert.assertNotNull(foundScheme);
    Assert.assertEquals("Scheme not found", foundScheme.getSchemeComposite(), createdScheme.getSchemeComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteSchemeTest(){
    Scheme createdScheme = saveScheme();
    schemeService.deleteById(createdScheme.getSchemeComposite());
    schemeService.findById(createdScheme.getSchemeComposite());
  }
}
