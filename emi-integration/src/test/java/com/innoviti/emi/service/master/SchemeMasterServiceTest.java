package com.innoviti.emi.service.master;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.entity.master.SchemeMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class SchemeMasterServiceTest extends SetupWithJPA {

  @Autowired
  private SchemeMasterService schemeMasterService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }

  private SchemeMaster createScheme() {
    SchemeMaster input = new SchemeMaster();
    SchemeMasterComposite composite = new SchemeMasterComposite();
    composite.setSchemeId(123456);
    composite.setCrtupdDate(new Date());
    input.setSchemeDescription("Scheme Description goes here");
    input.setTenure("TNR01");
    input.setProcessingFee("675.00");
    input.setProduct("Product 1");
    input.setAdvanceEmi("1");
    input.setDealerSubvention("1.5");
    input.setManfacturerSubvention("0.75");
    input.setInterestRate(12.75D);
    input.setSchemeStartDate(new Date());
    input.setSchemeExpiryDate(new Date());
    input.setPortalDescription("Description 1");
    input.setMaxAmount(new BigDecimal("70000"));
    input.setMinAmount(new BigDecimal("5000"));
    input.setGeneralScheme("YES");
    input.setSpecialScheme("N");
    input.setDealerMapping("Y");
    input.setModelMapping("N");
    input.setSchemeMasterComposite(composite);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    return input;
  }
  @Test
  public void createSchemeTest(){
    SchemeMaster input = createScheme();
    SchemeMaster createdScheme = schemeMasterService.create(input);
    Assert.assertNotNull(createdScheme);
    Assert.assertEquals("Scheme created", input.getSchemeMasterComposite(), createdScheme.getSchemeMasterComposite());
  }
  @Test
  public void findSchemeTest(){
    SchemeMaster input = createScheme();
    SchemeMaster createdScheme = schemeMasterService.create(input);
    Assert.assertNotNull(createdScheme);
    Assert.assertEquals("Scheme created", input.getSchemeMasterComposite(), createdScheme.getSchemeMasterComposite());
    
    SchemeMaster foundSchemeMaster = schemeMasterService.findById(input.getSchemeMasterComposite());
    Assert.assertNotNull(foundSchemeMaster);
    Assert.assertEquals("Scheme found", createdScheme.getSchemeMasterComposite(), foundSchemeMaster.getSchemeMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteSchemeTest(){
    SchemeMaster input = createScheme();
    SchemeMaster createdScheme = schemeMasterService.create(input);
    Assert.assertNotNull(createdScheme);
    Assert.assertEquals("Scheme created", input.getSchemeMasterComposite(), createdScheme.getSchemeMasterComposite());
    
    schemeMasterService.deleteById(createdScheme.getSchemeMasterComposite());
    schemeMasterService.findById(createdScheme.getSchemeMasterComposite());
  }
  
}
