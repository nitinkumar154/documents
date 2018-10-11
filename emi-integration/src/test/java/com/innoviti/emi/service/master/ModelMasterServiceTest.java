package com.innoviti.emi.service.master;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.ModelMaster;
import com.innoviti.emi.entity.master.ModelMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class ModelMasterServiceTest extends SetupWithJPA {

  @Autowired
  ModelMasterService modelMasterService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  private ModelMaster createModel(){
    ModelMaster input = new ModelMaster();
    ModelMasterComposite composite = new ModelMasterComposite();
    composite.setModelId(123456);
    composite.setCrtupdDate(new Date());
    input.setModelNumber("ABCD12345");
    input.setCategoryId(23456);
    input.setManufacturerId(345678);
    input.setSellingPrice(new BigDecimal("12345.68"));
    input.setModelExpiryDate(new Date(System.currentTimeMillis()));
    input.setSizeId("BACDEF");
    input.setMake("CDEFGH");
    input.setProductCode("ABC");
    input.setModelMasterComposite(composite);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    return input;
  }
  @Test
  public void createModelTest(){
    ModelMaster input = createModel();
    ModelMaster modelCreated = modelMasterService.create(input);
    Assert.assertEquals("Model created", input.getModelMasterComposite(), modelCreated.getModelMasterComposite());
  }
  @Test
  public void findModelTest(){
    ModelMaster input = createModel();
    ModelMaster modelCreated = modelMasterService.create(input);
    Assert.assertEquals("Model created", input.getModelMasterComposite(), modelCreated.getModelMasterComposite());
    
    ModelMaster foundModel = modelMasterService.findById(modelCreated.getModelMasterComposite());
    Assert.assertEquals("Model found", foundModel.getModelMasterComposite(), modelCreated.getModelMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteModelTest(){
    ModelMaster input = createModel();
    ModelMaster modelCreated = modelMasterService.create(input);
    Assert.assertEquals("Model created", input.getModelMasterComposite(), modelCreated.getModelMasterComposite());
    modelMasterService.deleteById(input.getModelMasterComposite());
    modelMasterService.findById(modelCreated.getModelMasterComposite());
  }
}
