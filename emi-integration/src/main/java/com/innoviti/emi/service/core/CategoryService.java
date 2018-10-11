package com.innoviti.emi.service.core;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.CategoryComposite;
import com.innoviti.emi.service.CrudService;
import com.querydsl.core.types.Predicate;

public interface CategoryService extends CrudService<Category, CategoryComposite> {

  Category findCategoryByInnoCategoryCode(String innoCategoryCode);

  Iterable<Category> findAll(Predicate predicate);

  Page<Category> findAllCategories(Pageable pageable);

  Page<Category> findAllCategoriesForDataMovement(String recordStatus, Pageable pageable);

  List<Category> autoCompleteCategories(String term);

}
