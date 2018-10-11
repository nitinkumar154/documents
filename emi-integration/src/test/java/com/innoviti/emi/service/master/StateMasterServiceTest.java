package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.StateMaster;
import com.innoviti.emi.entity.master.StateMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class StateMasterServiceTest extends SetupWithJPA {

  @Autowired
  private StateMasterService stateMasterService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  private StateMaster createState(){
    StateMaster input = new StateMaster();
    StateMasterComposite composite = new StateMasterComposite();
    composite.setStateId(12345);
    composite.setCrtupdDate(new Date());
    composite.setCrtupdDate(new Date());
    input.setStateDescription("ABCDEFGH");
    input.setStateMasterComposite(composite);
    
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    
    return input;
  }
  @Test
  public void createStateTest(){
    StateMaster input = createState();
    StateMaster createdState = stateMasterService.create(input);
    
    Assert.assertNotNull(createdState);
    Assert.assertEquals("State created", input.getStateMasterComposite(), createdState.getStateMasterComposite());
  }
  @Test
  public void findStateTest(){
    StateMaster input = createState();
    StateMaster createdState = stateMasterService.create(input);
    
    Assert.assertNotNull(createdState);
    Assert.assertEquals("State created", input.getStateMasterComposite(), createdState.getStateMasterComposite());
    
    StateMaster foundState = stateMasterService.findById(createdState.getStateMasterComposite());
    Assert.assertNotNull(createdState);
    Assert.assertEquals("State found", foundState.getStateMasterComposite(), createdState.getStateMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteStateTest(){
    StateMaster input = createState();
    StateMaster createdState = stateMasterService.create(input);
    
    Assert.assertNotNull(createdState);
    Assert.assertEquals("State created", input.getStateMasterComposite(), createdState.getStateMasterComposite());
    
    stateMasterService.deleteById(createdState.getStateMasterComposite());
    stateMasterService.findById(createdState.getStateMasterComposite());
  }
}
