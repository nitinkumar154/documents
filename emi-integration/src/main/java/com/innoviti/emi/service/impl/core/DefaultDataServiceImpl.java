package com.innoviti.emi.service.impl.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.repository.core.CategoryRepository;
import com.innoviti.emi.repository.core.ManufacturerRepository;
import com.innoviti.emi.repository.core.ModelRepository;
import com.innoviti.emi.repository.core.ProductRepository;
import com.innoviti.emi.repository.core.SchemeModelRepository;
import com.innoviti.emi.repository.core.SchemeRepository;
import com.innoviti.emi.service.core.DefaultDataService;

@Service
public class DefaultDataServiceImpl implements DefaultDataService{

  @Autowired
  private CategoryRepository categoryRepository;
  
  @Autowired
  private ManufacturerRepository manufacturerRepository;
  
  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private ModelRepository modelRepository;
  
  @Autowired
  private SchemeRepository schemeRepository;
  
  @Autowired
  private SchemeModelRepository schemeModelRepository;
  
  private static volatile Model generalSchemeModel;
  
  private static volatile Model model;
  
  private static volatile Category category;
  
  private static volatile Manufacturer manufacturer;
  
  private static volatile Product product;
  
  @Override
  public Category geDefaultCategory() {
    if(category == null){
      synchronized (DefaultDataServiceImpl.class) {
        if(category == null){
          category = categoryRepository.
              findTop1ByCategoryCompositeInnoCategoryCodeOrderByCategoryCompositeCrtupdDateDesc("XXXXXXXXXXXXX");
        }
      }
    }
    return category;
  }

  @Override
  public Manufacturer getDefaultManufacturer() {
    if(manufacturer == null){
      synchronized (DefaultDataServiceImpl.class) {
        if(manufacturer == null){
          manufacturer = manufacturerRepository.findTop1ByManufacturerCompositeInnoManufacturerCodeOrderByManufacturerCompositeCrtupdDateDesc("XXXXXXXXXXXXX");
        } 
      }
    }
    return manufacturer;
  };

  @Override
  public Product getDefaultProduct() {
    if(product == null){
      synchronized (DefaultDataServiceImpl.class) {
        if(product == null){
          product = productRepository.
              findTop1ByProductCompositeProductCodeOrderByProductCompositeCrtupdDateDesc("XXXXXXXXXXXXX");
        }
      }
    }
    return product;
  }

  @Override
  public Model getDefaultModel() {
    if(model == null){
      synchronized (DefaultDataServiceImpl.class) {
        if(model == null){
          model =  modelRepository.
              findTop1ByModelCompositeInnoModelCodeOrderByModelCompositeCrtupdDateDesc("XXXXXXXXXXXXX");

        }
      }
    }
    return model;
  }

  @Override
  public Scheme getDefaultScheme() {
   return schemeRepository.findTop1BySchemeCompositeInnoIssuerSchemeCodeOrderBySchemeCompositeCrtupdDateDesc("XXXXXXXXXXXXX");
  }

  @Override
  public SchemeModel getDefaultSchemeModel() {
    return schemeModelRepository.
        findTop1BySchemeModelCompositeInnoSchemeModelCodeOrderBySchemeModelCompositeCrtupdDateDesc("XXXXXXXXXXXXX");
  }

  @Override
  public Model getDefaultGeneralSchemeModel() {
    if(generalSchemeModel == null){
      synchronized (DefaultDataServiceImpl.class) {
        if(generalSchemeModel == null){
          generalSchemeModel =  modelRepository.
              findTop1ByModelCompositeInnoModelCodeOrderByModelCompositeCrtupdDateDesc("XXXXXXXXXXGEN");
        }
      }
    }
    return generalSchemeModel;
  }
  public static void reinializeVariables(){
    generalSchemeModel = null;
    model = null;
    category = null;
    manufacturer = null;
    product = null;
  }
}
