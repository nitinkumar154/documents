package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.DealerMaster;
import com.innoviti.emi.entity.master.DealerMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class DealerMasterServiceTest extends SetupWithJPA {

  @Autowired
  private DealerMasterService dealerMasterService;
  
  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }

  private DealerMaster createDealerMaster(){
    DealerMasterComposite dealerMasterComposite = new DealerMasterComposite();
    dealerMasterComposite.setSupplierId(987652);
    dealerMasterComposite.setCrtupdDate(new Date());
    DealerMaster input = new DealerMaster();
    input.setDealerMasterComposite(dealerMasterComposite);
    input.setSupplierDesc("AADI ENTERPRISES # RCOM");
    input.setSupplierDealerFlag("S");
    input.setDealerGroupId(36561);
    input.setDealerGroupDesc("AADI ENTERPRISES # RCOM");
    input.setPan("NULL");
    input.setContactPerson("NULL");
    input.setAddress1("5 87 102");
    input.setAddress2("MAIN ROAD LAKSHMI PURAM");
    input.setAddress3("NULL");
    input.setAddress4("NULL");
    input.setStdIsd("863");
    input.setPhone1("9348444888");
    input.setMobile("9348444888");
    input.setCity("44");
    input.setDealerEmail("NULL");
    input.setClassification("NULL");
    input.setLoyalityProgApplicable("Y");
    input.setSupplierType("OR");
    input.setSupplierBranch("123");
    input.setAssetCatgId(0);
    input.setProcessType("");
    input.setPreferredLimitFlag("Y");
    input.setStoreId("");
    input.setCoBrandLimitFlag("");
    input.setCoBrandCardCode("");
    input.setServingCities("");
    input.setState("32");
    input.setZipCode(522007);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    return input;
  }
  @Test
  public void createDealerTest(){
    DealerMaster input = createDealerMaster();
    DealerMaster createdDealer = dealerMasterService.create(input);
    Assert.assertEquals("Dealer created", input.getDealerMasterComposite(), createdDealer.getDealerMasterComposite());
  }
  @Test
  public void findDealerTest(){
    DealerMaster input = createDealerMaster();
    DealerMaster createdDealer = dealerMasterService.create(input);
    Assert.assertEquals("Dealer created", input.getDealerMasterComposite(), createdDealer.getDealerMasterComposite());
    
    DealerMaster foundDealer = dealerMasterService.findById(input.getDealerMasterComposite());
    Assert.assertEquals("Dealer created", foundDealer.getDealerMasterComposite(), createdDealer.getDealerMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteDealerTest(){
    DealerMaster input = createDealerMaster();
    DealerMaster createdDealer = dealerMasterService.create(input);
    Assert.assertEquals("Dealer created", input.getDealerMasterComposite(), createdDealer.getDealerMasterComposite());
    
    dealerMasterService.deleteById(input.getDealerMasterComposite());
    dealerMasterService.findById(input.getDealerMasterComposite());
  }
 
}
