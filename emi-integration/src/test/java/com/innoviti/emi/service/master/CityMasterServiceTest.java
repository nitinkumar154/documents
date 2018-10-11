package com.innoviti.emi.service.master;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.CityMaster;
import com.innoviti.emi.entity.master.CityMasterComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class CityMasterServiceTest extends SetupWithJPA {

  @Autowired
  private CityMasterService cityMasterService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  
  private CityMaster createCity(){
    CityMaster input = new CityMaster();
    CityMasterComposite composite = new CityMasterComposite();
    composite.setCityId("C10");
    composite.setCrtupdDate(new Date());
    input.setCityName("ABCDEFGH");
    input.setStateId(123456);
    input.setCityType("ABCD");
    input.setRiskPlloc("ABC");
    input.setCityTypeId(3);
    input.setCityCategory("ABC");
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    input.setCityMasterComposite(composite);
    
    return input;
  }
  @Test
  public void createCityTest(){
    CityMaster input = createCity();
    CityMaster createdCity = cityMasterService.create(input);
    Assert.assertEquals("City created", input.getCityMasterComposite(), createdCity.getCityMasterComposite());
  }
  @Test
  public void findCityTest(){
    CityMaster input = createCity();
    CityMaster createdCity = cityMasterService.create(input);
    Assert.assertEquals("City created", input.getCityMasterComposite(), createdCity.getCityMasterComposite());
    
    CityMaster foundCity = cityMasterService.findById(input.getCityMasterComposite());
    
    Assert.assertEquals("City found", createdCity.getCityMasterComposite(), foundCity.getCityMasterComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deletCityTest() {
    CityMaster input = createCity();
    CityMaster createdCity = cityMasterService.create(input);
    Assert.assertEquals("City created", input.getCityMasterComposite(), createdCity.getCityMasterComposite());
    cityMasterService.deleteById(input.getCityMasterComposite());
    
    cityMasterService.findById(input.getCityMasterComposite());
  }
}
