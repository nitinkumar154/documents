package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.SchemeModelMaster;
import com.innoviti.emi.entity.master.SchemeModelMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class SchemeModelMasterServiceTest extends SetupWithJPA {

  @Autowired
  private SchemeModelMasterService schemeModelMasterService;
  
  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  private SchemeModelMaster createSchemeModel(){
    SchemeModelMaster input = new  SchemeModelMaster();
    SchemeModelMasterComposite id = new SchemeModelMasterComposite();
    id.setCategoryId("LG001");
    id.setManufacturer("LG");
    id.setModelId("ABCDEF");
    id.setSchemeId(9876);
    id.setCrtupdDate(new Date());
    input.setSchemeModelMasterComposite(id);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    return input;
  }
  @Test
  public void createSchemeModelTest(){
    SchemeModelMaster input = createSchemeModel();
    SchemeModelMaster createdSchemeModel = schemeModelMasterService.create(input);
    Assert.assertNotNull(createdSchemeModel);
    Assert.assertEquals("Scheme model created", input.getSchemeModelMasterComposite(),
        createdSchemeModel.getSchemeModelMasterComposite());
  }
  @Test
  public void findSchemeModelTest(){
    SchemeModelMaster input = createSchemeModel();
    SchemeModelMaster createdSchemeModel = schemeModelMasterService.create(input);
    Assert.assertNotNull(createdSchemeModel);
    Assert.assertEquals("Scheme model created", input.getSchemeModelMasterComposite(),
        createdSchemeModel.getSchemeModelMasterComposite());
    
    SchemeModelMaster foundSchemeModel = schemeModelMasterService.findById(createdSchemeModel.getSchemeModelMasterComposite());
    Assert.assertNotNull(foundSchemeModel);
    Assert.assertEquals("Scheme model found", foundSchemeModel.getSchemeModelMasterComposite(),
        createdSchemeModel.getSchemeModelMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteSchemeModelTest(){
    SchemeModelMaster input = createSchemeModel();
    SchemeModelMaster createdSchemeModel = schemeModelMasterService.create(input);
    Assert.assertNotNull(createdSchemeModel);
    Assert.assertEquals("Scheme model created", input.getSchemeModelMasterComposite(),
        createdSchemeModel.getSchemeModelMasterComposite());
    
    schemeModelMasterService.deleteById(createdSchemeModel.getSchemeModelMasterComposite());
    schemeModelMasterService.findById(createdSchemeModel.getSchemeModelMasterComposite());
  }
}
