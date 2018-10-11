package com.innoviti.emi.service.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.ProductComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface ProductService extends CrudService<Product, ProductComposite> {

  Product findProductByInnoProductTypeCode(String innoProductTypeCode);

  Iterable<Product> findAll(Predicate predicate);

  Page<Product> findAllProducts(Pageable pageable);

  Page<Product> findAllProductsForDataMovement(String recordStatus, Pageable pageable);

  List<Product> autoCompleteProducts(String term);

}
