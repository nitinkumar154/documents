package com.innoviti.emi.service.impl.core;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.constant.ErrorMessages;
import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.ProductComposite;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.ProductRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.ProductService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class ProductServiceImpl extends CrudServiceHelper<Product, ProductComposite>
    implements ProductService {

  private ProductRepository productRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;
  
  private static final String SEQ_NAME = "Product";
  private static final String IDENTIFIER = "PRD";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository) {
    super(productRepository);
    this.productRepository = productRepository;
  }

  @Override
  public Product create(Product u) {
    ProductComposite productComposite = new ProductComposite();
    if (u.getProductCode() == null) {
      if (!productExists(u.getInnoProductTypeCode()))
        throw new AlreadyMappedException(
            "Requested Product -> " + u.getInnoProductTypeCode() + " already exists", 422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);
      String innoProductCode = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);

      productComposite.setProductCode(innoProductCode);
    } else {
      productComposite.setProductCode(u.getProductCode());
    }
    productComposite.setCrtupdDate(u.getCrtupdDate());
    u.setProductComposite(productComposite);
    return helperCreate(u);
  }

  private boolean productExists(String productDisplayName) {
    if (productDisplayName == null)
      throw new BadRequestException("Requested field should not be null !!");
    return productRepository.findProductByName(productDisplayName.trim()).isEmpty();
  }

  @Override
  public void deleteById(ProductComposite id) {
    helperDeleteById(id);
  }

  @Override
  public Product findById(ProductComposite id) {
    return helperFindById(id);
  }

  @Override
  public Product update(Product u) {
    return null;
  }

  @Override
  public List<Product> findAll() {
    return helperFindAll();
  }

  @Override
  public Product findProductByInnoProductTypeCode(String innoProductTypeCode) {
    if (innoProductTypeCode == null || innoProductTypeCode.isEmpty())
      throw new BadRequestException("Requested id cannot be blank", 400);

    Product lookedUpProduct = productRepository
        .findTop1ByProductCompositeProductCodeOrderByProductCompositeCrtupdDateDesc(
            innoProductTypeCode);
    if (lookedUpProduct == null) {
      throw new NotFoundException("Categories not found for id : " + innoProductTypeCode, 404);
    }
    return lookedUpProduct;
  }

  @Override
  public Iterable<Product> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Query string cannot be blank", 400);
    }
    Iterable<Product> products = productRepository.findAll(predicate);
    if (products == null) {
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    }
    return products;
  }

  @Override
  public Page<Product> findAllProducts(Pageable pageable) {

    Page<Product> products = productRepository.findAllProducts(pageable);
    if (products == null || !products.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    return products;
  }

  @Override
  public Page<Product> findAllProductsForDataMovement(String recordStatus, Pageable pageable) {
    Page<Product> products = null;
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.PRODUCT.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      products = productRepository.findAllCategoriesForDataMovement(recordStatus,
          latestDateForEntity, pageable);
    } else {
      products = productRepository.findAllCategoriesForDataMovement(recordStatus, pageable);
    }
    if (products == null || !products.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    return products;
  }

  @Override
  public List<Product> autoCompleteProducts(String term) {
    List<Product> products = productRepository.findAllProductsWithTerm(term);
    if (products == null || !products.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);
    return products;
  }
}
