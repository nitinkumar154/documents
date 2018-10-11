package com.innoviti.emi.service;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.service.core.TenureService;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class TenureTest extends SetupWithJPA{

  @Autowired
  public TenureService tenureService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
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
  @Test
  public void createTenureTest() {
    Tenure tenure = getTenure();
    Tenure createdTenure = tenureService.create(tenure);
    Assert.assertNotNull(createdTenure);
    Assert.assertEquals("Tenure not created", tenure.getTenureComposite(), createdTenure.getTenureComposite());
  }
  @Test
  public void findTenureTest(){
    Tenure tenure = getTenure();
    Tenure createdTenure = tenureService.create(tenure);
    Assert.assertNotNull(createdTenure);
    Assert.assertEquals("Tenure not created", tenure.getTenureComposite(), createdTenure.getTenureComposite());

    Tenure foundTenure = tenureService.findById(createdTenure.getTenureComposite());
    Assert.assertNotNull(foundTenure);
    Assert.assertEquals("Tenure not found", foundTenure.getTenureComposite(), createdTenure.getTenureComposite());
  }
  @Test
  public void findTenurebyIdTest(){
    Tenure tenure = getTenure();
    Tenure createdTenure = tenureService.create(tenure);
    Assert.assertNotNull(createdTenure);
    Assert.assertEquals("Tenure not created", tenure.getTenureComposite(), createdTenure.getTenureComposite());

    Tenure foundTenure = tenureService.findTenureByInnoTenureCode(createdTenure.getTenureComposite().getInnoTenureCode());
    Assert.assertNotNull(foundTenure);
    Assert.assertEquals("Tenure not found", foundTenure.getTenureComposite().getInnoTenureCode(), 
        createdTenure.getTenureComposite().getInnoTenureCode());
  }
  @Test(expected = NotFoundException.class)
  public void  deleteTenureTest(){
    Tenure tenure = getTenure();
    Tenure createdTenure = tenureService.create(tenure);
    Assert.assertNotNull(createdTenure);
    Assert.assertEquals("Tenure not created", tenure.getTenureComposite(), createdTenure.getTenureComposite());
    tenureService.deleteById(tenure.getTenureComposite());
    tenureService.findById(createdTenure.getTenureComposite());
  }
}
