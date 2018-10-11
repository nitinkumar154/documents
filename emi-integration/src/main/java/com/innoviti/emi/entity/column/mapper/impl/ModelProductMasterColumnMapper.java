package com.innoviti.emi.entity.column.mapper.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.ProductComposite;
import com.innoviti.emi.repository.core.ProductRepository;
import com.innoviti.emi.service.core.DefaultDataService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;

@Component
public class ModelProductMasterColumnMapper implements ColumnMapper<String, Product> {

  @Autowired
  private SequenceGeneratorService sequenceGeneratorService;
  
  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private DefaultDataService defaultDataService;
  
  @Override
  public Product mapColumn(String bajajProductCode) {
 
    Product defaultProduct = defaultDataService.getDefaultProduct();
    String defaultProductCode = defaultProduct.getProductComposite().getProductCode();
    Pageable topOne = new PageRequest(0, 1);
    List<Product> foundProducts = productRepository.findByProductCodeNotDefault(bajajProductCode, defaultProductCode, topOne);
    //If bajaj product already exist and is not a default product ignore the record
    if(foundProducts != null && !foundProducts.isEmpty()){
      return null;
    }
    SequenceType sequenceType = SequenceType.PRODUCT;
    int seqNum = (int) sequenceGeneratorService.getSeqNumber(sequenceType.getSequenceName());
    String newInsertId = String.valueOf(seqNum);
    String productCode =
        sequenceType.getSequenceIdentifier() + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);

    Product product = new Product();
    product.setInnoProductTypeCode(bajajProductCode);
    product.setBajajProductTypeCode(bajajProductCode);
    product.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    product.setCrtupdStatus("N");
    product.setCrtupdUser("Batch");
    product.setRecordActive(true);
    product.setIssuerProductTypeName(bajajProductCode);
    ProductComposite productComposite = new ProductComposite();
    productComposite.setCrtupdDate(new Date());
    productComposite.setProductCode(productCode);
    product.setProductComposite(productComposite);
    return product;
  }
}
