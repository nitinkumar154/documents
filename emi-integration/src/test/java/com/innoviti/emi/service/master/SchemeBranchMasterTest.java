package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.SchemeBranchMaster;
import com.innoviti.emi.entity.master.SchemeBranchMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class SchemeBranchMasterTest  extends SetupWithJPA {
  
  @Autowired
  private SchemeBranchMasterService schemeBranchMasterService;
  
  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  
  private SchemeBranchMaster createSchemeBranch(){
    SchemeBranchMasterComposite id = new SchemeBranchMasterComposite();
    id.setBranchId(123456);
    id.setSchemeId(234567);
    id.setCrtupdDate(new Date());
    SchemeBranchMaster input = new  SchemeBranchMaster();
    input.setSchemeBranchMasterComposite(id);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    
    return input;
  }
  @Test
  public void createSchemeBranchTest(){
    SchemeBranchMaster input = createSchemeBranch();
    SchemeBranchMaster schemeBranchCreated = schemeBranchMasterService.create(input);
    
    Assert.assertEquals("Scheme branch created", input.getSchemeBranchMasterComposite(), 
        schemeBranchCreated.getSchemeBranchMasterComposite());
  }
  @Test
  public void findSchemeBranchTest(){
    SchemeBranchMaster input = createSchemeBranch();
    SchemeBranchMaster createdSchemeBranch = schemeBranchMasterService.create(input);
    
    Assert.assertEquals("Scheme branch created", input.getSchemeBranchMasterComposite(), 
        createdSchemeBranch.getSchemeBranchMasterComposite());
    
    SchemeBranchMaster foundSchemeBranch = schemeBranchMasterService.
        findById(createdSchemeBranch.getSchemeBranchMasterComposite());
    
    Assert.assertEquals("Scheme branch found", foundSchemeBranch.getSchemeBranchMasterComposite(), 
        createdSchemeBranch.getSchemeBranchMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteSchemeBranchTest(){
    SchemeBranchMaster input = createSchemeBranch();
    SchemeBranchMaster createdSchemeBranch = schemeBranchMasterService.create(input);
    
    Assert.assertEquals("Scheme branch created", input.getSchemeBranchMasterComposite(), 
        createdSchemeBranch.getSchemeBranchMasterComposite());
    
    schemeBranchMasterService.deleteById(createdSchemeBranch.getSchemeBranchMasterComposite());
    schemeBranchMasterService.findById(createdSchemeBranch.getSchemeBranchMasterComposite());
  }
}