package com.innoviti.emi.service.master;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.master.AssetCategoryMaster;
import com.innoviti.emi.entity.master.AssetCategoryMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.setup.SetupWithJPA;

public class AssetCategoryMasterServiceTest extends SetupWithJPA {

  @Autowired
  AssetCategoryMasterService service;

  private AssetCategoryMaster createAssetCategoryMaster(int id, String desc){
    AssetCategoryMaster input = new AssetCategoryMaster();
    AssetCategoryMasterComposite composite = new AssetCategoryMasterComposite();
    composite.setId(id);
    composite.setCrtupdDate(new Date());
    input.setDescription(desc);
    input.setCibilCheck("Y");
    input.setDigitalFlag("Y");
    input.setRiskClassification("Y");
    input.setAssetCategoryMasterComposite(composite);
    input.setCrtupdReason("test");
    input.setCrtupdStatus('N');
    input.setCrtupdUser("test");
    return input;
  }
  @Test
  public void createWithNullTest() {
    Exception exception = null;
    try {
      service.create(null);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("No exception inserting null object !!", exception);
  }

  @Test
  public void createWithNewInstanceTest() {
    Exception exception = null;
    try {
      service.create(new AssetCategoryMaster());
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("No exception inserting null object !!", exception);
  }

  @Test
  public void createWithIdOnlyTest() {
    Exception exception = null;
    try {
      AssetCategoryMaster input = new AssetCategoryMaster();
      AssetCategoryMasterComposite composite = new AssetCategoryMasterComposite();
      composite.setId(1234567);
      input.setAssetCategoryMasterComposite(composite);
      service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("No exception inserting with id only !!", exception);
  }

  @Test
  public void createWithDescrptionOnlyTest() {
    Exception exception = null;
    try {
      AssetCategoryMaster input = new AssetCategoryMaster();
      input.setDescription("ABCD");
      service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("No exception inserting description as null !!", exception);
  }

  @Test
  public void createWithDescriptionAsNullTest() {
    Exception exception = null;
    try {
      AssetCategoryMaster input = createAssetCategoryMaster(12345678, null);
      service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("No exception inserting null description !!", exception);
  }

  @Test
  public void createWithDescriptionAsBlankTest() {
    Exception exception = null;
    try {
      AssetCategoryMaster input = createAssetCategoryMaster(12345678, "");
      service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("No exception inserting null description !!", exception);
  }

  
  @Test
  public void createWithDescriptionNotSetTest() {
    Exception exception = null;
    try {
      AssetCategoryMaster input = new AssetCategoryMaster();
      AssetCategoryMasterComposite composite = new AssetCategoryMasterComposite();
      composite.setId(12345678);
      input.setCibilCheck("Y");
      input.setDigitalFlag("Y");
      input.setRiskClassification("Y");
      service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("No exception inserting null description !!", exception);
  }

  @Test
  public void createWithIdAndDescriptionOnlyTest() {
    Exception exception = null;
    try {
      AssetCategoryMaster input = new AssetCategoryMaster();
      AssetCategoryMasterComposite composite = new AssetCategoryMasterComposite();
      composite.setId(12345678);
      input.setDescription("ABCD");
      input.setAssetCategoryMasterComposite(composite);
      service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull(
        "No exception inserting without cibil_check, risk_classification, digital_flag !!",
        exception);
  }

  @Test
  public void createWithRiskClassificationOnlyTest() {
    Exception exception = null;
    try {
      AssetCategoryMaster input = new AssetCategoryMaster();
      AssetCategoryMasterComposite composite = new AssetCategoryMasterComposite();
      composite.setId(12345678);
      input.setDescription("ABCD");
      input.setRiskClassification("Y");
      input.setAssetCategoryMasterComposite(composite);
      service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("No exception inserting without cibil_check, digital_flag  !!", exception);
  }

  @Test
  public void createAllPositiveTest() {
    Exception exception = null;
    AssetCategoryMaster input = createAssetCategoryMaster(12345678, "Description");
    AssetCategoryMaster output = null;
    try {
      service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNull("Encountered exception inserting object !!", exception);
    try {
      output = service.findById(input.getAssetCategoryMasterComposite());
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNull("Encountered exception fetching object !!", exception);
    Assert.assertNotNull("Output object was null", output);
    Assert.assertEquals("Input and output object Id not equal  !!",
        output.getAssetCategoryMasterComposite().getId(),
        input.getAssetCategoryMasterComposite().getId());
    Assert.assertEquals("Input and output object Description not equal  !!",
        output.getDescription(), input.getDescription());
    Assert.assertEquals("Input and output object CibilCheck not equal  !!", output.getCibilCheck(),
        input.getCibilCheck());
    Assert.assertEquals("Input and output object DigitalFlag not equal  !!",
        output.getDigitalFlag(), input.getDigitalFlag());
    Assert.assertEquals("Input and output object RiskClassification not equal  !!",
        output.getRiskClassification(), input.getRiskClassification());
  }

  @Test
  public void deleteByIdWithNewInsertionTest() {
    Integer id = 12345678;
    Exception exception = null;
    AssetCategoryMaster output = null;
    AssetCategoryMaster input = createAssetCategoryMaster(id, "ABCD");
    try {
      output = service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNull("Encountered exception while testing delete", exception);
    Assert.assertNotNull("Insertion unsuccessful  !!", output);
    try {
      service.deleteById(input.getAssetCategoryMasterComposite());
      output = service.findById(input.getAssetCategoryMasterComposite());
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("Expected exception while deletion !!", exception);
    Assert.assertTrue("Encountered some other exception while deletion  !!",
        exception instanceof NotFoundException);
  }


  @Test
  public void deleteByIdWithNullTest() {
    Exception exception = null;
    try {
      service.deleteById(null);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("Expected exception while deletion !!", exception);
    Assert.assertTrue("Encountered some other exception while deletion  !!",
        exception instanceof BadRequestException);
  }

  @Test
  public void findByIdWithNewInsertionTest() {
    Integer id = 12345678;
    Exception exception = null;
    AssetCategoryMaster output = null;
    AssetCategoryMaster input = createAssetCategoryMaster(id, "ABCD");
    try {
      output = service.create(input);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNull("Encountered exception while testing delete", exception);
    Assert.assertNotNull("Insertion unsuccessful  !!", output);
    try {
      output = service.findById(input.getAssetCategoryMasterComposite());
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNull("Encountered exception while fetching record !!", exception);
    Assert.assertNotNull("Fetching unsuccessful  !!", output);
    Assert.assertEquals("Input and output object Id not equal  !!",
        output.getAssetCategoryMasterComposite().getId(),
        input.getAssetCategoryMasterComposite().getId());
    Assert.assertEquals("Input and output object Description not equal  !!",
        output.getDescription(), input.getDescription());
    Assert.assertEquals("Input and output object CibilCheck not equal  !!", output.getCibilCheck(),
        input.getCibilCheck());
    Assert.assertEquals("Input and output object DigitalFlag not equal  !!",
        output.getDigitalFlag(), input.getDigitalFlag());
    Assert.assertEquals("Input and output object RiskClassification not equal  !!",
        output.getRiskClassification(), input.getRiskClassification());
  }

  @Test
  public void findByIdWithNullKeyTest() {
    Exception exception = null;
    try {
      service.findById(null);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("Encountered exception while fetching record !!", exception);
    Assert.assertTrue("Insertion unsuccessful  !!", exception instanceof BadRequestException);
  }

  @Test
  public void updateWithNullEntityTest() {
    AssetCategoryMaster output = null;
    Exception exception = null;
    try {
      output = service.update(null);
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNotNull("Encountered exception while fetching record !!", exception);
    Assert.assertNull("Successfully updated null !!", output);
    Assert.assertTrue("Encountered some other exception updating null  !!",
        exception instanceof BadRequestException);
  }

 

  @Test
  public void findAllWithInsertedDataTest() {
    Map<Integer, AssetCategoryMaster> inputObjectMap = new HashMap<>();
    List<AssetCategoryMaster> outputObjectList = new LinkedList<>();
    Exception exception = null;
    for (int i = 0; i < 5; i++) {
      AssetCategoryMaster object = createAssetCategoryMaster(i, "Description 0000" + i);
      inputObjectMap.put(object.getAssetCategoryMasterComposite().getId(), object);
      try {
        service.create(object);
      } catch (Exception e) {
        exception = e;
      }
      Assert.assertNull("Encountered exception while inserting object !!", exception);
    }
    try {
      outputObjectList = service.findAll();
    } catch (Exception e) {
      exception = e;
    }
    Assert.assertNull("Encountered exception while fetching objects !!", exception);
    for (AssetCategoryMaster object : outputObjectList) {
      AssetCategoryMaster inputObject =
          inputObjectMap.get(object.getAssetCategoryMasterComposite().getId());
      Assert.assertNotNull("Input and output object Id do not match !!", inputObject);
      Assert.assertEquals("Input and output object Description not equal  !!",
          object.getDescription(), inputObject.getDescription());
      Assert.assertEquals("Input and output object CibilCheck not equal  !!",
          object.getCibilCheck(), inputObject.getCibilCheck());
      Assert.assertEquals("Input and output object DigitalFlag not equal  !!",
          object.getDigitalFlag(), inputObject.getDigitalFlag());
      Assert.assertEquals("Input and output object RiskClassification not equal  !!",
          object.getRiskClassification(), inputObject.getRiskClassification());
    }
  }

}
