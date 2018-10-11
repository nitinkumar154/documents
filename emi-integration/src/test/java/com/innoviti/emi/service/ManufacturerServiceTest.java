package com.innoviti.emi.service;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.ManufacturerComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.service.core.ManufacturerService;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class ManufacturerServiceTest extends SetupWithJPA {
 
  @Autowired
  private EntityManager entityManager;
  
  private Date date = new Date();
  
  @Before
  public void setup(){
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  @Autowired
  ManufacturerService manufacturerService;
  
  private ManufacturerComposite getCompositeKey(){
    ManufacturerComposite manufacturerComposite = new ManufacturerComposite();
    manufacturerComposite.setInnoManufacturerCode("1");
    manufacturerComposite.setCrtupdDate(date);
    
    return manufacturerComposite;
  }
  private Manufacturer createManufacturer() {
    Manufacturer manufacturer = new Manufacturer();
    manufacturer.setManufacturerComposite(getCompositeKey());
    manufacturer.setInnoManufacturerCode("1");
    manufacturer.setCrtupdDate(date);
    manufacturer.setBajajManufacturerCode("Bajaj_1");
    manufacturer.setManufacturerDesc("1st Manufacturer");
    manufacturer.setManufacturerDisplayName("baj");
    manufacturer.setCrtupdReason("configuration");
    manufacturer.setCrtupdUser("admin");
    manufacturer.setCrtupdStatus("Y");
    
    return manufacturer;
  }
  
  
  @Test
  public void testCreateManufacturer() {
    
    Manufacturer createdManufacturer = manufacturerService.create(createManufacturer());
    Assert.assertEquals("1", createdManufacturer.getManufacturerComposite().getInnoManufacturerCode());
  }
  
  @Test
  public void read(){
    Manufacturer createdManufacturer = manufacturerService.create(createManufacturer());
    Assert.assertNotNull(createdManufacturer);
    
    createdManufacturer = manufacturerService.findById(getCompositeKey());
    
    Assert.assertNotNull(createdManufacturer);
  }
  @Test(expected = NotFoundException.class)
  public void delete(){
    Manufacturer createdManufacturer = manufacturerService.create(createManufacturer());
    Assert.assertNotNull(createdManufacturer);
    
    manufacturerService.deleteById(getCompositeKey());
    manufacturerService.findById(getCompositeKey());
  }
}
