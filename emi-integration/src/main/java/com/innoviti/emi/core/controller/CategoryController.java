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

import com.innoviti.emi.core.resource.CategoryResource;
import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.service.core.CategoryService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

  private static Logger logger = Logger.getLogger(CategoryController.class);

  @Autowired
  private CategoryService categoryService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Category category) {
    Category createdCategory = categoryService.create(category);
    CategoryResource categoryResource = new CategoryResource(createdCategory);

    return ResponseEntity.created(URI.create(categoryResource.getCreateCategoryLink())).build();
  }

  @GetMapping(value = "/{innoCategoryCode}")
  public ResponseEntity<CategoryResource> getCategroryById(
      @PathVariable("innoCategoryCode") String innoCategoryCode) {

    Category category = categoryService.findCategoryByInnoCategoryCode(innoCategoryCode);
    CategoryResource categoryResource = new CategoryResource(category);
    categoryResource.addSelfLink();

    return ResponseEntity.<CategoryResource>ok(categoryResource);
  }

  @GetMapping(value = "/query")
  public ResponseEntity<List<CategoryResource>> getCategrory(
      @QuerydslPredicate(root = Category.class) Predicate predicate) {
    Iterable<Category> categories = categoryService.findAll(predicate);
    List<CategoryResource> resources = new ArrayList<>();
    for (Category category : categories) {
      CategoryResource categoryResource = new CategoryResource(category);
      categoryResource.addSelfLink();
      resources.add(categoryResource);
    }
    return ResponseEntity.<List<CategoryResource>>ok(resources);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<CategoryResource>> getAllCategrory(Pageable pageable) {
    Page<Category> categoryPage = categoryService.findAllCategories(pageable);
    List<CategoryResource> resources = new ArrayList<>();
    for (Category category : categoryPage.getContent()) {
      CategoryResource categoryResource = new CategoryResource(category);
      categoryResource.addSelfLink();
      resources.add(categoryResource);
    }
    PageMetadata pageMetaData = new PageMetadata(categoryPage.getSize(), categoryPage.getNumber(),
        categoryPage.getTotalElements(), categoryPage.getTotalPages());
    PagedResources<CategoryResource> pagedResource = new PagedResources<>(resources, pageMetaData);
    return ResponseEntity.<PagedResources<CategoryResource>>ok(pagedResource);
  }

  @GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<CategoryResource>> getAllCategroryForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {

    Page<Category> categories =
        categoryService.findAllCategoriesForDataMovement(recordStatus, pageable);
    List<CategoryResource> resources = new ArrayList<>();
    for (Category category : categories.getContent()) {
      CategoryResource categoryResource = new CategoryResource(category);
      categoryResource.addSelfLink();
      resources.add(categoryResource);
    }
    PageMetadata pageMetadata = new PageMetadata(categories.getSize(), categories.getNumber(),
        categories.getTotalElements(), categories.getTotalPages());
    PagedResources<CategoryResource> pageResource = new PagedResources<>(resources, pageMetadata);
    return ResponseEntity.<PagedResources<CategoryResource>>ok(pageResource);
  }

  @GetMapping(value = "/autoComplete")
  public ResponseEntity<List<CategoryResource>> autoCompleteCategories(
      @RequestParam("term") String term) {

    logger.info("Entering autoCompleteCategories :: CategoryController");
    List<Category> categories = categoryService.autoCompleteCategories(term);
    List<CategoryResource> resources = new ArrayList<>();
    for (Category category : categories) {
      CategoryResource categoryResource = new CategoryResource(category);
      categoryResource.addSelfLink();
      resources.add(categoryResource);
    }
    logger.info("Exiting autoCompleteCategories :: CategoryController");
    return ResponseEntity.<List<CategoryResource>>ok(resources);
  }

}
