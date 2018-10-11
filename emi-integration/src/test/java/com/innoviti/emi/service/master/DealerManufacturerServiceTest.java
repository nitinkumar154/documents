package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.DealerManufacturerMaster;
import com.innoviti.emi.entity.master.DealerManufacturerMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class DealerManufacturerServiceTest extends SetupWithJPA {

  @Autowired
  private DealerManufacturerMasterService dealerManufacturerMasterService;


  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  
  private DealerManufacturerMaster createDealerManufacturer(){
    DealerManufacturerMaster input = new DealerManufacturerMaster();
    DealerManufacturerMasterComposite dealerManufacturerMasterComposite =
        new DealerManufacturerMasterComposite();
    dealerManufacturerMasterComposite.setManufacturerId(123456);
    dealerManufacturerMasterComposite.setSupplierId(23456);
    dealerManufacturerMasterComposite.setCrtupdDate(new Date());
    input.setDealerManufacturerMasterComposite(dealerManufacturerMasterComposite);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    return input;
  }
  @Test
  public void createDealerManufacturerTest(){
    DealerManufacturerMaster input = createDealerManufacturer();
    DealerManufacturerMaster createdDealerManufacturer = dealerManufacturerMasterService.create(input);
    
    Assert.assertEquals("Dealer manufacturer created", input.getDealerManufacturerMasterComposite(), createdDealerManufacturer.getDealerManufacturerMasterComposite());
  }
  @Test
  public void findDealerManufacturerTest(){
    DealerManufacturerMaster input = createDealerManufacturer();
    DealerManufacturerMaster createdDealerManufacturer = dealerManufacturerMasterService.create(input);
    
    Assert.assertEquals("Dealer manufacturer created", input.getDealerManufacturerMasterComposite(), createdDealerManufacturer.getDealerManufacturerMasterComposite());
    
    DealerManufacturerMaster foundDealerManucterer = dealerManufacturerMasterService.findById(input.getDealerManufacturerMasterComposite());
    Assert.assertEquals("Dealer manufacturer found", foundDealerManucterer.getDealerManufacturerMasterComposite(), createdDealerManufacturer.getDealerManufacturerMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteDealerManufacturerTest(){
    DealerManufacturerMaster input = createDealerManufacturer();
    DealerManufacturerMaster createdDealerManufacturer = dealerManufacturerMasterService.create(input);
    
    Assert.assertEquals("Dealer manufacturer created", input.getDealerManufacturerMasterComposite(), createdDealerManufacturer.getDealerManufacturerMasterComposite());
    
    dealerManufacturerMasterService.deleteById(input.getDealerManufacturerMasterComposite());
    dealerManufacturerMasterService.findById(input.getDealerManufacturerMasterComposite());
  }
 
}
