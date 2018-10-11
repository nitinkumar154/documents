package com.innoviti.emi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.ProductComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.service.core.ProductService;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class ProductServiceTest extends SetupWithJPA {

  @Autowired
  ProductService productService;

  @Autowired
  private EntityManager entityManager;

  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }

  private List<Product> createProductList(int numOfProductItem) {

    List<Product> productList = new ArrayList<>();

    while (numOfProductItem-- > 0) {

      Product product = new Product();
      product.setProductCode("Prod_" + numOfProductItem);
      product.setCrtupdDate(new Date());
      product.setBajajProductTypeCode("Bajaj_" + numOfProductItem);
      product.setCrtupdReason("config");
      product.setCrtupdUser("admin");
      product.setCrtupdStatus("Y");
      product.setInnoProductTypeCode("REDMI");
      productList.add(product);
    }
    return productList;
  }

  @Test
  public void testCreateProduct() {
    int numOfProduxtItem = 10;
    List<Product> productList = createProductList(numOfProduxtItem);
    for (Product product : productList) {
      Product createdProduct = productService.create(product);
      Assert.assertNotNull(createdProduct);
    }
  }

  @Test
  public void testFindProductById() {
    Product createdProduct = productService.create(createProductList(1).get(0));
    Product lookedUpProduct = productService
        .findProductByInnoProductTypeCode(createdProduct.getProductComposite().getProductCode());
    Assert.assertNotNull(lookedUpProduct);
  }


  @Test(expected = NotFoundException.class)
  public void deleteByInnoIssuerSchemeCode() {
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
    Product productToDelete = productService.create(product);
    Assert.assertNotNull(productToDelete);
    productService.deleteById(productToDelete.getProductComposite());

    Assert.assertNull("Product not deleted !",
        productService.findById(productToDelete.getProductComposite()));

  }

  @Test(expected = BadRequestException.class)
  public void deleteByNullId() {
    productService.deleteById(null);
  }

}
