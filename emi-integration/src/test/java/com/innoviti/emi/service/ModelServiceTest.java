package com.innoviti.emi.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.CategoryComposite;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.ProductComposite;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.service.core.CategoryService;
import com.innoviti.emi.service.core.ManufacturerService;
import com.innoviti.emi.service.core.ModelService;
import com.innoviti.emi.service.core.ProductService;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class ModelServiceTest extends SetupWithJPA{
  
  @Autowired
  private ModelService modelService;
  
  @Autowired
  private EntityManager entityManager;
  
  @Autowired
  private ProductService productService;
  
  @Autowired
  private ManufacturerService manufacturerService;
  
  @Autowired
  private CategoryService categoryService;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }
  
  private Product getProduct(){
    Product product = new Product();
    ProductComposite productComposite = new ProductComposite();

    product.setProductCode("ProNul");
    product.setCrtupdDate(new Date());

    product.setProductComposite(productComposite);
    product.setBajajProductTypeCode("BajNul");
    product.setCrtupdReason("config");
    product.setCrtupdUser("admin");
    product.setCrtupdStatus("Y");
    product.setInnoProductTypeCode("REDMI");
    
    return product;
  }
  private Manufacturer getManufacturer() {
    Manufacturer manufacturer = new Manufacturer();
    manufacturer.setInnoManufacturerCode("1");
    manufacturer.setCrtupdDate(new Date());
    manufacturer.setBajajManufacturerCode("Bajaj_1");
    manufacturer.setManufacturerDesc("1st Manufacturer");
    manufacturer.setManufacturerDisplayName("baj");
    manufacturer.setCrtupdReason("configuration");
    manufacturer.setCrtupdUser("admin");
    manufacturer.setCrtupdStatus("Y");
    
    return manufacturer;
  }
  private Category getCategory(){
    Category category = new Category();
    CategoryComposite categoryComposite = new CategoryComposite();
    categoryComposite.setInnoCategoryCode("CAT001");
    categoryComposite.setCrtupdDate(new Date());
    category.setInnoCategoryCode("CAT001");
    category.setCategoryComposite(categoryComposite);
    category.setCrtupdDate(new Date());
    category.setBajajCategoryCode("345262");
    category.setCategoryDesc("CatDesc");
    category.setCategoryDisplayName("CatDisp");
    category.setCrtupdReason("config");
    category.setCrtupdStatus("A");
    category.setCrtupdUser("admin");
    category.setRecordActive(true);
    
    return category;
  }
  private Model getModel(){
    Model model = new Model();
    model.setBajajModelCode("1211211");
    model.setBajajModelExpiryDate(new Date());
    model.setBajajModelNo("1233");
    model.setBajajModelSellingPrice(new BigDecimal(32.32));
    model.setCrtupdReason("dummy");
    model.setCrtupdStatus("Y");
    model.setRecordActive(true);
    model.setInnoMinSellingPrice(new BigDecimal("99.20"));
    model.setInnoMaxSellingPrice(new BigDecimal(22.56));
    model.setCrtupdUser("Admin");
    model.setModelDisplayNo("6789");
    model.setCrtupdDate(new Date());
    model.setInnoModelCode("1234");
    return model;
  }
  private Model saveModel(){
    
    Category category = categoryService.create(getCategory());
    Assert.assertNotNull(category);
    Manufacturer manufacturer = manufacturerService.create(getManufacturer());
    Assert.assertNotNull(manufacturer);
    Product product = productService.create(getProduct());
    Assert.assertNotNull(product);
    
    Model model = getModel();
    model.setInnoManufacturerCode(manufacturer.getManufacturerComposite().getInnoManufacturerCode());
    model.setInnoCategoryCode(category.getCategoryComposite().getInnoCategoryCode());
    model.setProductCode(product.getProductComposite().getProductCode());
    Model createdModel = modelService.create(model);
    Assert.assertNotNull(createdModel);
    Assert.assertEquals("Model not created", model.getModelComposite(), createdModel.getModelComposite());
    
    return createdModel;
  }
  @Test
  public void createModelTest(){
    saveModel();
  }
  @Test
  public void findModelTest(){
    Model createdModel = saveModel();
    Model foundModel = modelService.findById(createdModel.getModelComposite());
    Assert.assertNotNull(foundModel);
    Assert.assertEquals("Model not found", foundModel.getModelComposite(), createdModel.getModelComposite());
  }
  @Test
  public void findModelByModelIdTest(){
    Model createdModel = saveModel();
    Model foundModel = modelService.findModelByInnoModelCode(createdModel.getModelComposite().getInnoModelCode());
    Assert.assertNotNull(foundModel);
    Assert.assertEquals("Model not found", foundModel.getModelComposite(), createdModel.getModelComposite());
  }
  @Test(expected = NotFoundException.class)
  public void deleteModelTest(){
    Model createdModel = saveModel();
    modelService.deleteById(createdModel.getModelComposite());
    Model foundModel = modelService.findModelByInnoModelCode(createdModel.getModelComposite().getInnoModelCode());
    Assert.assertNotNull(foundModel);
    Assert.assertEquals("Model not found", foundModel.getModelComposite(), createdModel.getModelComposite());
  }
}
