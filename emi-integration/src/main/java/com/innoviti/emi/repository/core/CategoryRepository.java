package com.innoviti.emi.repository.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.CategoryComposite;
import com.innoviti.emi.entity.core.QCategory;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface CategoryRepository extends BaseRepository<Category, CategoryComposite>,
    QueryDslPredicateExecutor<Category>, QuerydslBinderCustomizer<QCategory> {

  public Category findTop1ByCategoryCompositeInnoCategoryCodeOrderByCategoryCompositeCrtupdDateDesc(
      String innoCategoryCode);

  public Category findTop1ByBajajCategoryCodeOrderByCategoryCompositeCrtupdDateDesc(
      String bajajCategoryCode);

  public Category findTop1ByBajajCategoryCode(String bajajCategoryCode);
  
  @Query(value = "SELECT * FROM categories WHERE bajaj_category_code = ?1 AND (record_update_status='N' OR record_update_status='U') ORDER BY record_update_timestamp DESC "
      + " LIMIT 1", nativeQuery=true)
  public Category findLatestCategoryByBajajCategoryCode(String categoryCode);
  
  @Override
  default void customize(QuerydslBindings bindings, QCategory root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }

  @Query(
      value = "SELECT * FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate  "
          + "\n#pageable\n",
      countQuery = "SELECT COUNT(*) FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate",
      nativeQuery = true)
  public Page<Category> findAllCategories(Pageable pageable);

  @Query(
      value = "SELECT * FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE record_update_status = :recordStatus GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE record_update_status = :recordStatus GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate",
      nativeQuery = true)
  public Page<Category> findAllCategoriesForDataMovement(@Param("recordStatus") String recordStatus,
      Pageable pageable);

  @Query("SELECT MAX(m.categoryComposite.crtupdDate) FROM Category m WHERE m.crtupdStatus='L' ORDER BY m.categoryComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO categories (`category_code`, `bajaj_category_code`, `category_description`, `category_display_name`,"
          + "`record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT c.category_code, c.bajaj_category_code, c.category_description, c.category_display_name, "
          + "NOW(), c.record_update_reason, 'L', c.record_update_user, c.is_record_active "
          + "FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE record_update_status = 'N' or record_update_status = 'U' GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate",
      nativeQuery = true)
  public void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO categories (`category_code`, `bajaj_category_code`, `category_description`, `category_display_name`,"
          + "`record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT c.category_code, c.bajaj_category_code, c.category_description, c.category_display_name, "
          + "NOW(), c.record_update_reason, 'L', c.record_update_user, c.is_record_active "
          + "FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE record_update_status = 'N' or record_update_status = 'U' GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate "
          + "AND c.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(
      value = "SELECT * FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE category_display_name LIKE %:term% "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate",
      nativeQuery = true)
  public List<Category> findAllCategoryWithTerm(@Param("term") String term);

  @Query(
      value = "SELECT * FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE category_display_name = :categoryName "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate",
      nativeQuery = true)
  public List<Category> findCategoryByName(@Param("categoryName") String categoryName);

  @Query(
      value = "SELECT * FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE record_update_status = :recordStatus GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate "
          + "AND c.record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM categories c INNER JOIN "
          + "(SELECT category_code, MAX(record_update_timestamp) as maxdate FROM categories "
          + "WHERE record_update_status = :recordStatus GROUP BY category_code) ct "
          + "ON c.category_code = ct.category_code AND c.record_update_timestamp = ct.maxdate "
          + "AND c.record_update_timestamp > :latestDateForEntity ",
      nativeQuery = true)
  public Page<Category> findAllCategoriesForDataMovement(@Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);
}
