package com.innoviti.emi.entity.column.mapper.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.ModelComposite;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.master.ModelMaster;
import com.innoviti.emi.entity.master.ModelProductMaster;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.CategoryRepository;
import com.innoviti.emi.repository.core.ManufacturerRepository;
import com.innoviti.emi.repository.core.ModelRepository;
import com.innoviti.emi.repository.core.ProductRepository;
import com.innoviti.emi.repository.master.ModelProductMasterRepository;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;

@Component
public class ModelMasterColumnMapper implements ColumnMapper<ModelMaster, Model> {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ManufacturerRepository manufacturerRespository;

  @Autowired
  private ModelProductMasterRepository modelProductMasterRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;
  
  @Autowired
  private ModelRepository modelRepository;
  
  @Override
  public Model mapColumn(ModelMaster modelMaster) {
    
    //Ignore the record if model number is null
    String modelNumber = modelMaster.getModelNumber();
    if (modelNumber == null || modelNumber.isEmpty()) {
      return null;
    }
    Integer modelId = modelMaster.getModelMasterComposite().getModelId();
   
    Model model = new Model();
    model.setBajajModelCode(String.valueOf(modelId));
    model.setBajajModelExpiryDate(modelMaster.getModelExpiryDate());
    model.setBajajModelNo(modelNumber);
    model.setBajajModelSellingPrice(modelMaster.getSellingPrice());

    if (modelNumber.length() < 50) {
      model.setModelDisplayNo(modelNumber);
    } else {
      model.setModelDisplayNo(modelNumber.substring(0, 49));
    }
    ModelProductMaster modelProductMaster = modelProductMasterRepository
        .findLatestProductMasterByModel(
            modelId);
    if (modelProductMaster == null) {
      throw new NotFoundException("No model product mapping : " + modelId);
    }
    model.setCrtupdReason(modelMaster.getCrtupdReason());
    model.setCrtupdStatus("N");
    model.setCrtupdDate(new Date());
    model.setCrtupdUser(modelMaster.getCrtupdUser());
    Category category =
        categoryRepository.findLatestCategoryByBajajCategoryCode(String.valueOf(modelMaster.getCategoryId()));
    if (category == null) {
      throw new NotFoundException("No category");
    }
    model.setCategory(category);
    Manufacturer manufacturer = manufacturerRespository
        .findLatestByBajajManufacturerCode(
            String.valueOf(modelMaster.getManufacturerId()));
    if (manufacturer == null) {
      throw new NotFoundException("No manufacturer");
    }
    model.setManufacturer(manufacturer);
    String productCode = modelProductMaster.getModelProductMasterComposite().getCode();
    Pageable topOne = new PageRequest(0, 1);
    List<Product> products = productRepository.getProductByBajajProductAndStatus(productCode, topOne);
    if (products == null || products.isEmpty()) {
      throw new NotFoundException("No product");
    }
    Model foundModel = modelRepository.findTop1ByBajajModelCode(String.valueOf(modelId));
    String innoModelCode = null;
   
    if(foundModel != null){
      innoModelCode = foundModel.getModelComposite().getInnoModelCode();
    }
    else{
      SequenceType sequenceType = SequenceType.MODEL;
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(sequenceType.getSequenceName());
      String newInsertId = String.valueOf(seqNum);
      innoModelCode =
          sequenceType.getSequenceIdentifier() + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);
    }
    

    model.setProduct(products.get(0));
    model.setRecordActive(true);
    model.setInnoModelCode(modelMaster.getModelMasterComposite().getModelId().toString());
    ModelComposite modelComposite = new ModelComposite();
    modelComposite.setInnoModelCode(innoModelCode);
    modelComposite.setCrtupdDate(modelMaster.getModelMasterComposite().getCrtupdDate());
    model.setModelComposite(modelComposite);
    return model;
  }

}
