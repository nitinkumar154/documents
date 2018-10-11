package com.innoviti.emi.core.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innoviti.emi.core.resource.ProductResource;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.service.core.ProductService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

  private static Logger logger = Logger.getLogger(ProductController.class);

  @Autowired
  private ProductService productService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Product product) {
    Product createdProduct = productService.create(product);
    ProductResource productResource = new ProductResource(createdProduct);

    return ResponseEntity.created(URI.create(productResource.getCreateProductLink())).build();
  }

  @GetMapping(value = "/{innoProductTypeCode}")
  public ResponseEntity<ProductResource> getProductById(
      @PathVariable("innoProductTypeCode") String innoProductTypeCode) {

    Product product = productService.findProductByInnoProductTypeCode(innoProductTypeCode);

    ProductResource productResource = new ProductResource(product);
    productResource.addSelfLink();

    return ResponseEntity.<ProductResource>ok(productResource);
  }


  @GetMapping(value = "/query")
  public ResponseEntity<List<ProductResource>> getProduct(
      @QuerydslPredicate(root = Product.class) Predicate predicate) {
    Iterable<Product> Products = productService.findAll(predicate);
    List<ProductResource> resources = new ArrayList<>();
    for (Product Product : Products) {
      ProductResource ProductResource = new ProductResource(Product);
      ProductResource.addSelfLink();
      resources.add(ProductResource);
    }
    return ResponseEntity.<List<ProductResource>>ok(resources);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<ProductResource>> getProductList(Pageable pageable) {
    Page<Product> ProductPage = productService.findAllProducts(pageable);
    List<ProductResource> resources = new ArrayList<>();
    for (Product Product : ProductPage.getContent()) {
      ProductResource ProductResource = new ProductResource(Product);
      ProductResource.addSelfLink();
      resources.add(ProductResource);
    }
    PageMetadata pageMetaData = new PageMetadata(ProductPage.getSize(), ProductPage.getNumber(),
        ProductPage.getTotalElements(), ProductPage.getTotalPages());
    PagedResources<ProductResource> pagedResource = new PagedResources<>(resources, pageMetaData);
    return ResponseEntity.<PagedResources<ProductResource>>ok(pagedResource);
  }

  @GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<ProductResource>> getAllProductForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {

    Page<Product> products = productService.findAllProductsForDataMovement(recordStatus, pageable);
    List<ProductResource> resources = new ArrayList<>();
    for (Product product : products.getContent()) {
      ProductResource productResource = new ProductResource(product);
      productResource.addSelfLink();
      resources.add(productResource);
    }
    PageMetadata pageMetadata = new PageMetadata(products.getSize(), products.getNumber(),
        products.getTotalElements(), products.getTotalPages());
    PagedResources<ProductResource> pagedResource = new PagedResources<>(resources, pageMetadata);

    return ResponseEntity.<PagedResources<ProductResource>>ok(pagedResource);
  }

  @GetMapping(value = "/autoComplete")
  public ResponseEntity<List<ProductResource>> autoCompleteProducts(
      @RequestParam("term") String term) {

    logger.info("Entering autoCompleteProducts :: ProductController");
    List<Product> categories = productService.autoCompleteProducts(term);
    List<ProductResource> resources = new ArrayList<>();
    for (Product category : categories) {
      ProductResource categoryResource = new ProductResource(category);
      categoryResource.addSelfLink();
      resources.add(categoryResource);
    }
    logger.info("Exiting autoCompleteProducts :: ProductController");
    return ResponseEntity.<List<ProductResource>>ok(resources);
  }

}
