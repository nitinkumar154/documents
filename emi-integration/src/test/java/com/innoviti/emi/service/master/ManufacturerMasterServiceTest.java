package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.ManufacturerMaster;
import com.innoviti.emi.entity.master.ManufacturerMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class ManufacturerMasterServiceTest extends SetupWithJPA {

  @Autowired
  private ManufacturerMasterService manufacturerService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  
  private ManufacturerMaster createManufacturer(){
    ManufacturerMaster manufacturerMaster = new ManufacturerMaster();
    ManufacturerMasterComposite composite = new ManufacturerMasterComposite();
    composite.setManufacturerId(212);
    composite.setCrtupdDate(new Date());
    manufacturerMaster.setManufacturerDesc("ManufacturerDesc1");
    manufacturerMaster.setManufacturerMasterComposite(composite);
    manufacturerMaster.setCrtupdReason("test");
    manufacturerMaster.setCrtupdStatus('N');
    manufacturerMaster.setCrtupdUser("test");
    
    return manufacturerMaster;
  }
  @Test
  public void createManufacturerTest() {
    ManufacturerMaster manufacturerMaster = createManufacturer();
    ManufacturerMaster manufacturerCreated = manufacturerService.create(manufacturerMaster);
    Assert.assertEquals("Manufacturer created", manufacturerMaster.getManufacturerMasterComposite(), 
        manufacturerCreated.getManufacturerMasterComposite());
  }
  @Test
  public void findManufacturerTest(){
    ManufacturerMaster manufacturerMaster = createManufacturer();
    ManufacturerMaster manufacturerCreated = manufacturerService.create(manufacturerMaster);
    Assert.assertEquals("Manufacturer created", manufacturerMaster.getManufacturerMasterComposite(), 
        manufacturerCreated.getManufacturerMasterComposite());
    
    ManufacturerMaster foundManufacturer = manufacturerService.findById(manufacturerMaster.getManufacturerMasterComposite());
    
    Assert.assertEquals("Manufacturer found", foundManufacturer.getManufacturerMasterComposite(), 
        manufacturerCreated.getManufacturerMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteManufacturerTest(){
    ManufacturerMaster manufacturerMaster = createManufacturer();
    ManufacturerMaster manufacturerCreated = manufacturerService.create(manufacturerMaster);
    Assert.assertEquals("Manufacturer created", manufacturerMaster.getManufacturerMasterComposite(), 
        manufacturerCreated.getManufacturerMasterComposite());
    
    manufacturerService.deleteById(manufacturerCreated.getManufacturerMasterComposite());
    manufacturerService.findById(manufacturerMaster.getManufacturerMasterComposite());
  }
}
