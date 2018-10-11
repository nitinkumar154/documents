package com.innoviti.emi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.CategoryComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.CategoryRepository;
import com.innoviti.emi.service.core.CategoryService;
import com.innoviti.emi.setup.SetupWithJPA;
import com.innoviti.emi.util.TruncateTablesService;

public class CategoryServiceTest extends SetupWithJPA {

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private CategoryRepository categoryRepository;
  
  @Before
  public void setUp() {
    TruncateTablesService truncateTablesService = new TruncateTablesService(entityManager);
    truncateTablesService.truncateAll();
  }

  private List<Category> createCategoryList(int numOfCategoryItem) {

    List<Category> categoryList = new ArrayList<>();

    if (numOfCategoryItem <= 0)
      throw new IllegalArgumentException("Number of items to create must be at least 1 !!");

    while (numOfCategoryItem-- > 0) {

      Category category = new Category();
      CategoryComposite categoryComposite = new CategoryComposite();
      categoryComposite.setInnoCategoryCode("Cat_" + numOfCategoryItem);
      categoryComposite.setCrtupdDate(new Date());
      category.setInnoCategoryCode("Cat_" + numOfCategoryItem);
      category.setCategoryComposite(categoryComposite);
      category.setCrtupdDate(new Date());
      category.setBajajCategoryCode("Bajaj_" + numOfCategoryItem);
      category.setCategoryDesc("CatDesc_" + numOfCategoryItem);
      category.setCategoryDisplayName("CatDisp_" + numOfCategoryItem);
      category.setCrtupdReason("config");
      category.setCrtupdStatus("A");
      category.setCrtupdUser("admin");
      category.setRecordActive(true);

      categoryList.add(category);
    }
    return categoryList;
  }

  @Test
  public void testCreateCategory() {
    int numOfCategoryItem = 10;
    List<Category> categoryList = createCategoryList(numOfCategoryItem);
    for (Category category : categoryList) {
      Category createdScheme = categoryService.create(category);
      Assert.assertNotNull(createdScheme);
    }
  }

  @Test
  public void testFindCategoryByCode() {
    Category createdCategory = categoryService.create(createCategoryList(1).get(0));
    Category lookedUpCategory = categoryService.findCategoryByInnoCategoryCode(
        createdCategory.getCategoryComposite().getInnoCategoryCode());
    Assert.assertNotNull(lookedUpCategory);
  }

  @Test
  public void testUpdatingCategory() {
    Category createdCategory = categoryService.create(createCategoryList(1).get(0));
    Category lookupCategory = categoryService.findCategoryByInnoCategoryCode(
        createdCategory.getCategoryComposite().getInnoCategoryCode());
    CategoryComposite lookupCatComposite = new CategoryComposite();

    lookupCatComposite.setCrtupdDate(new Date());
    lookupCatComposite.setInnoCategoryCode(lookupCategory.getCategoryComposite().getInnoCategoryCode());
    lookupCategory.setCategoryComposite(lookupCatComposite);
    lookupCategory.setCategoryDesc("DescUpdate");
    lookupCategory.setCategoryDisplayName("CatDispUpdate");

    Assert.assertNotNull("Error while updating category !!",
        categoryService.update(lookupCategory));
  }

  @Test(expected = NotFoundException.class)
  public void deleteByInnoIssuerSchemeCode() {
    Category category = new Category();
    CategoryComposite categoryComposite = new CategoryComposite();

    categoryComposite.setInnoCategoryCode("CatDel1");
    categoryComposite.setCrtupdDate(new Date());

    category.setCategoryComposite(categoryComposite);
    category.setBajajCategoryCode("BajDelt");
    category.setCategoryDesc("CatDeleteIt");
    category.setCategoryDisplayName("Deleted Cat");
    category.setCrtupdReason("config");
    category.setCrtupdStatus("A");
    category.setCrtupdUser("admin");
    category.setRecordActive(true);

    Category categoryToDelete = categoryRepository.saveAndFlush(category);

    categoryService.deleteById(categoryToDelete.getCategoryComposite());

    Assert.assertNull("Category not deleted !",
        categoryService.findById(category.getCategoryComposite()));
  }

  @Test(expected = BadRequestException.class)
  public void deleteByNullId() {
    categoryService.deleteById(null);
  }

}
