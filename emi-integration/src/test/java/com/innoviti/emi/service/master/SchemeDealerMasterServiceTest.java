package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.SchemeDealerMaster;
import com.innoviti.emi.entity.master.SchemeDealerMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class SchemeDealerMasterServiceTest extends SetupWithJPA {

  @Autowired
  private SchemeDealerMasterService schemeDealerMasterService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  private SchemeDealerMaster createSchemeDealerMaster(){
    SchemeDealerMaster input = new  SchemeDealerMaster();
    SchemeDealerMasterComposite id = new SchemeDealerMasterComposite();
    id.setSupplierId(123456);
    id.setSchemeId(234567);
    id.setCrtupdDate(new Date());
    input.setSchemeDealerMasterComposite(id);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    
    return input;
  }
  @Test
  public void createSchemeDealerTest(){
    SchemeDealerMaster schemeDealerMaster = createSchemeDealerMaster();
    SchemeDealerMaster createdSchemeDealer = schemeDealerMasterService.create(schemeDealerMaster);
    
    Assert.assertEquals("Scheme Dealer created", schemeDealerMaster.getSchemeDealerMasterComposite(), 
        createdSchemeDealer.getSchemeDealerMasterComposite());
  }
  @Test
  public void findSchemeDealerTest(){
    SchemeDealerMaster schemeDealerMaster = createSchemeDealerMaster();
    SchemeDealerMaster createdSchemeDealer = schemeDealerMasterService.create(schemeDealerMaster);
    
    Assert.assertEquals("Scheme Dealer created", schemeDealerMaster.getSchemeDealerMasterComposite(), 
        createdSchemeDealer.getSchemeDealerMasterComposite());
    
    SchemeDealerMaster foundSchemeDealer = schemeDealerMasterService.findById(createdSchemeDealer.getSchemeDealerMasterComposite());
    Assert.assertNotNull(foundSchemeDealer);
    Assert.assertEquals("Scheme Dealer found", foundSchemeDealer.getSchemeDealerMasterComposite(), 
        createdSchemeDealer.getSchemeDealerMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteSchemeDealerTest(){
    SchemeDealerMaster schemeDealerMaster = createSchemeDealerMaster();
    SchemeDealerMaster createdSchemeDealer = schemeDealerMasterService.create(schemeDealerMaster);
    
    Assert.assertEquals("Scheme Dealer created", schemeDealerMaster.getSchemeDealerMasterComposite(), 
        createdSchemeDealer.getSchemeDealerMasterComposite());
    schemeDealerMasterService.deleteById(schemeDealerMaster.getSchemeDealerMasterComposite());
    
    schemeDealerMasterService.findById(createdSchemeDealer.getSchemeDealerMasterComposite());
  }

}
