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
import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.CategoryComposite;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.CategoryRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.CategoryService;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class CategoryServiceImpl extends CrudServiceHelper<Category, CategoryComposite>
    implements CategoryService {

  private CategoryRepository categoryRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  private static final String SEQ_NAME = "Category";
  private static final String IDENTIFIER = "CAT";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRespository) {
    super(categoryRespository);
    this.categoryRepository = categoryRespository;
  }

  @Override
  public Category create(Category u) {
    CategoryComposite categoryComposite = new CategoryComposite();
    if (u.getInnoCategoryCode() == null) {
      if (!categoryExists(u.getCategoryDisplayName()))
        throw new AlreadyMappedException(
            "Requested category -> " + u.getCategoryDisplayName() + " already exists", 422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);
      String innoCategoryCode = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);

      categoryComposite.setInnoCategoryCode(innoCategoryCode);
    } else {
      categoryComposite.setInnoCategoryCode(u.getInnoCategoryCode());
    }
    categoryComposite.setCrtupdDate(u.getCrtupdDate());
    u.setCategoryComposite(categoryComposite);

    return helperCreate(u);
  }

  private boolean categoryExists(String categoryDisplayName) {
    if (categoryDisplayName == null)
      throw new BadRequestException("Requested field should not be null !!");
    return categoryRepository.findCategoryByName(categoryDisplayName.trim()).isEmpty();
  }

  @Override
  public void deleteById(CategoryComposite id) {
    helperDeleteById(id);
  }

  @Override
  public Category findById(CategoryComposite id) {
    return helperFindById(id);
  }

  @Override
  public Category update(Category u) {
    return helperUpdate(u);
  }

  @Override
  public List<Category> findAll() {
    return helperFindAll();
  }

  @Override
  public Category findCategoryByInnoCategoryCode(String innoCategoryCode) {
    if (innoCategoryCode == null || innoCategoryCode.isEmpty())
      throw new BadRequestException("Requested id cannot be blank", 400);

    Category categories = categoryRepository
        .findTop1ByCategoryCompositeInnoCategoryCodeOrderByCategoryCompositeCrtupdDateDesc(
            innoCategoryCode);
    if (categories == null) {
      throw new NotFoundException("Categories not found for id : " + innoCategoryCode, 404);
    }
    return categories;
  }

  @Override
  public Iterable<Category> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Query string cannot be blank", 400);
    }
    Iterable<Category> categories = categoryRepository.findAll(predicate);
    if (categories == null) {
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    }
    return categories;
  }

  @Override
  public Page<Category> findAllCategories(Pageable pageable) {
    Page<Category> categories = categoryRepository.findAllCategories(pageable);
    if (categories == null || !categories.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return categories;
  }

  @Override
  public Page<Category> findAllCategoriesForDataMovement(String recordStatus, Pageable pageable) {
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.CATEGORY.getSequenceName()).getTimeStamp();
    Page<Category> categories = null;
    if (latestDateForEntity != null) {
      categories = categoryRepository.findAllCategoriesForDataMovement(recordStatus,
          latestDateForEntity, pageable);
    } else {
      categories = categoryRepository.findAllCategoriesForDataMovement(recordStatus, pageable);
    }
    if (categories == null || !categories.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return categories;
  }

  @Override
  public List<Category> autoCompleteCategories(String term) {
    List<Category> categories = categoryRepository.findAllCategoryWithTerm(term);
    if (categories == null || !categories.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);

    return categories;
  }

}
