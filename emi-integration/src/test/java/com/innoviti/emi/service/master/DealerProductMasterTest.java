package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.DealerProductMaster;
import com.innoviti.emi.entity.master.DealerProductMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class DealerProductMasterTest extends SetupWithJPA {
  
  @Autowired
  private DealerProductMasterService dealerProductMasterService;
  
  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  private DealerProductMaster createDealerProduct(){
    DealerProductMaster input = new DealerProductMaster();
    DealerProductMasterComposite id = new DealerProductMasterComposite();
    id.setCode("LG123456");
    id.setSupplierId(87454487);
    id.setCrtupdDate(new Date());
    input.setDealerProductMasterComposite(id);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    return input;
  }
  @Test
  public void createDealerProductMasterTest(){
    DealerProductMaster input = createDealerProduct();
    DealerProductMaster createdDealer = dealerProductMasterService.create(input);
    
    Assert.assertEquals("Dealer created", input.getDealerProductMasterComposite(), createdDealer.getDealerProductMasterComposite());
  }
  @Test
  public void findDealerProductTest(){
    DealerProductMaster input = createDealerProduct();
    DealerProductMaster createdDealer = dealerProductMasterService.create(input);
    
    Assert.assertEquals("Dealer created", input.getDealerProductMasterComposite(), createdDealer.getDealerProductMasterComposite());
    
    DealerProductMaster foundDealer = dealerProductMasterService.findById(createdDealer.getDealerProductMasterComposite());
    
    Assert.assertEquals("Dealer found", foundDealer.getDealerProductMasterComposite(), createdDealer.getDealerProductMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteDealerProductTest(){
    DealerProductMaster input = createDealerProduct();
    DealerProductMaster createdDealer = dealerProductMasterService.create(input);
    
    Assert.assertEquals("Dealer created", input.getDealerProductMasterComposite(), createdDealer.getDealerProductMasterComposite());
    dealerProductMasterService.deleteById(input.getDealerProductMasterComposite());
    dealerProductMasterService.findById(createdDealer.getDealerProductMasterComposite());
    
  }
}
