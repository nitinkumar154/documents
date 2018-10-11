package com.innoviti.emi.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.constant.CashbackType;
import com.innoviti.emi.constant.OnUsOffUsStatus;
import com.innoviti.emi.constant.UTIDUpdateStatus;
import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.CategoryComposite;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.IssuerBankComposite;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.ProductComposite;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeComposite;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.service.core.CategoryService;
import com.innoviti.emi.service.core.IssuerBankService;
import com.innoviti.emi.service.core.ManufacturerService;
import com.innoviti.emi.service.core.ModelService;
import com.innoviti.emi.service.core.ProductService;
import com.innoviti.emi.service.core.SchemeModelService;
import com.innoviti.emi.service.core.SchemeModelTerminalService;
import com.innoviti.emi.service.core.SchemeService;
import com.innoviti.emi.service.core.TenureService;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class SchemModelTerminalServiceTest extends SetupWithJPA {

  @Autowired
  private SchemeModelService schemeModelService;
  
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

  @Autowired
  SchemeService schemeService;
  
  @Autowired
  TenureService tenureService;
  
  @Autowired
  IssuerBankService issuerBankService;

  @Autowired
  private SchemeModelTerminalService schemeModelTerminalService;

  @Before
  public void setup() {
    TruncateTablesService truncateTableService = new TruncateTablesService(entityManager);
    truncateTableService.truncateAll();
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
  private IssuerBank getIssuerBank(){
    IssuerBankComposite issuerBankComposite = new IssuerBankComposite();
    issuerBankComposite.setCrtupdDate(new Date());
    issuerBankComposite.setInnoIssuerBankCode("41");
    IssuerBank issuerBank = new IssuerBank();
    issuerBank.setCrtupdDate(new Date());
    issuerBank.setInnoIssuerBankCode("41");
    issuerBank.setCrtupdReason("configuration");
    issuerBank.setCrtupdStatus("T");
    issuerBank.setCrtupdUser("Admin");
    issuerBank.setRecordActive(true);
    issuerBank.setIssuerBankComposite(issuerBankComposite);
    issuerBank.setIssuerBankDesc("ICICI");
    issuerBank.setIssuerBankDisclaimer("ICICI");
    issuerBank.setIssuerBankDisplayName("ICICI");
    issuerBank.setIssuerDefaultCashbackFlag(CashbackType.POST);
    issuerBank.setIssuerMinEmiAmount(new BigDecimal(200));
    issuerBank.setEmiBankCode(12);
   
    return issuerBank;
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
  private Scheme getScheme(){
    Scheme scheme = new Scheme();
    SchemeComposite schemeComposite = new SchemeComposite();

    scheme.setInnoIssuerSchemeCode("Delt123");
    final Date date = new Date();
    scheme.setCrtupdDate(date);

    scheme.setSchemeComposite(schemeComposite);
    scheme.setBajajIssuerSchemeCode("BSch123");
    scheme.setAdvanceEmi("6");
    scheme.setSchemeDisplayName("119/DEL/21/");
    scheme.setCashbackFlag(CashbackType.PRE);
    scheme.setRecordActive(true);
    scheme.setCrtupdReason("config");
    scheme.setCrtupdUser("admin");
    scheme.setCrtupdStatus("N");
    
    return scheme;
  }
  private Scheme saveScheme(){
    Tenure createdTenure = tenureService.create(getTenure());
    Assert.assertNotNull(createdTenure);
    IssuerBank createdIssuerBank = issuerBankService.create(getIssuerBank());
    Assert.assertNotNull(createdIssuerBank);
    
    Scheme scheme = getScheme();
    scheme.setInnoTenureCode(createdTenure.getTenureComposite().getInnoTenureCode());
    scheme.setInnoIssuerBankCode(createdIssuerBank.getIssuerBankComposite().getInnoIssuerBankCode());
    Scheme createdScheme = schemeService.create(scheme);
    Assert.assertNotNull(createdScheme);
    
    return createdScheme;
  }
  private SchemeModel getSchemeModel(){
    SchemeModel schemeModel=new SchemeModel();
    schemeModel.setCrtupdDate(new Date());
    schemeModel.setInnoSchemeModelCode("123");
    schemeModel.setCrtupdStatus("C");
    schemeModel.setCrtupdUser("admin");
    schemeModel.setCrtupdReason("testcase purpose");
    schemeModel.setRecordActive(true);
   
    return schemeModel;
  }
  private SchemeModel saveSchemeModel(){
    Model model = saveModel();
    Scheme scheme = saveScheme();
    SchemeModel schemeModel = getSchemeModel();
    schemeModel.setInnoIssuerSchemeCode(scheme.getSchemeComposite().getInnoIssuerSchemeCode());
    schemeModel.setInnoModelCode(model.getModelComposite().getInnoModelCode());
    SchemeModel createdschemeModel = schemeModelService.create(schemeModel);
    Assert.assertNotNull(createdschemeModel);
    Assert.assertEquals("Scheme model not created", schemeModel.getSchemeModelComposite(), createdschemeModel.getSchemeModelComposite());
    return createdschemeModel;
  }
  private SchemeModelTerminal getSchemeModelTermial(){
    SchemeModelTerminal schemeModelTerminal = new SchemeModelTerminal();
    schemeModelTerminal.setCrtupdDate(new Date());
    schemeModelTerminal.setUtid("89763534");
    schemeModelTerminal.setDealerId("90092345");
    schemeModelTerminal.setBajajProductTypeCode("REMI");
    schemeModelTerminal.setCrtupdReason("test");
    schemeModelTerminal.setCrtupdStatus("N");
    schemeModelTerminal.setCrtupdUser("test");
    schemeModelTerminal.setIssuerCustomField("cust");
    schemeModelTerminal.setIssuerSchemeTerminalSyncStatus(UTIDUpdateStatus.ACKD);
    schemeModelTerminal.setOnUsOffUs(OnUsOffUsStatus.OFF_US);
    schemeModelTerminal.setRecordActive(true);
    return schemeModelTerminal;
  }
  private SchemeModelTerminal saveSchemeModelTerminal(){
    SchemeModel createdSchemeModel = saveSchemeModel();
    SchemeModelTerminal schemeModelTerminal = getSchemeModelTermial();
    schemeModelTerminal.setInnoSchemeModelCode(createdSchemeModel.getSchemeModelComposite().getInnoSchemeModelCode());
    SchemeModelTerminal createdSchemeModelTerminal = schemeModelTerminalService.create(schemeModelTerminal);
    Assert.assertNotNull(createdSchemeModelTerminal);
    Assert.assertEquals("Scheme model terminal not created", schemeModelTerminal.getSchemeModelTerminalComposite(),
        createdSchemeModelTerminal.getSchemeModelTerminalComposite());
    
    return schemeModelTerminal;
  }
 
  @Test
  public void createSchemeModelTerminalTest() {
    saveSchemeModelTerminal();
  }
  @Test
  public void findSchemeModelTerminalTest(){
    SchemeModelTerminal savedSchemModelTerminal = saveSchemeModelTerminal();
    SchemeModelTerminal foundSchemModelTerminal = schemeModelTerminalService.findById(savedSchemModelTerminal.getSchemeModelTerminalComposite());
    Assert.assertNotNull(foundSchemModelTerminal);
    Assert.assertEquals("Scheme model terminal found", savedSchemModelTerminal.getSchemeModelTerminalComposite(),
        savedSchemModelTerminal.getSchemeModelTerminalComposite());
  }
  @Test
  public void findIfRecodExistByUtidAndCodeTest(){
    SchemeModelTerminal savedSchemModelTerminal = saveSchemeModelTerminal();
    boolean isExist = schemeModelTerminalService.
        findByCodeIfExists(savedSchemModelTerminal.getSchemeModelTerminalComposite().getSchemeModel().
            getSchemeModelComposite().getInnoSchemeModelCode(), 
            savedSchemModelTerminal.getSchemeModelTerminalComposite().getUtid());
    Assert.assertTrue(isExist);
   
  }
  public void deleteSchemeModelTerminalTest(){
    SchemeModelTerminal savedSchemModelTerminal = saveSchemeModelTerminal();
    schemeModelTerminalService.deleteById(savedSchemModelTerminal.getSchemeModelTerminalComposite());
    schemeModelTerminalService.findById(savedSchemModelTerminal.getSchemeModelTerminalComposite());
  }
}
