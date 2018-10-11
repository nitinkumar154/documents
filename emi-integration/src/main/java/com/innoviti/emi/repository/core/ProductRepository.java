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

import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.ProductComposite;
import com.innoviti.emi.entity.core.QProduct;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface ProductRepository extends BaseRepository<Product, ProductComposite>,
    QueryDslPredicateExecutor<Product>, QuerydslBinderCustomizer<QProduct> {

  public Product findTop1ByProductCompositeProductCodeOrderByProductCompositeCrtupdDateDesc(
      String innoProductTypeCode);

  @Query(value = "SELECT s FROM Product s WHERE s.bajajProductTypeCode = ?1 AND (s.crtupdStatus = 'N' OR s.crtupdStatus='U') "
      + "ORDER BY s.productComposite.crtupdDate DESC")
  public List<Product> getProductByBajajProductAndStatus(
      String bajajProductCode, Pageable page);

  public Product findTop1ByBajajProductTypeCode(String bajajProductCode);

  public List<Product> findByBajajProductTypeCodeIn(List<String> bajajProductCodes);

  @Query("SELECT s FROM Product s where s.bajajProductTypeCode=?1 AND s.productComposite.productCode!=?2")
  public List<Product> findByProductCodeNotDefault(String bajajProductCode, String innoProductCode,
      Pageable pageable);

  @Override
  default void customize(QuerydslBindings bindings, QProduct root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }

  @Query(
      value = "SELECT * FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate "
          + " \n#pageable\n",
      countQuery = "SELECT COUNT(*) FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate",
      nativeQuery = true)
  public Page<Product> findAllProducts(Pageable pageable);

  @Query("SELECT MAX(m.productComposite.crtupdDate) FROM Product m WHERE m.crtupdStatus='L' ORDER BY m.productComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO products (`product_code`, `product_type_code`, `bajaj_product_type_code`, "
          + "`issuer_product_display_name`, `record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT p.product_code, p.product_type_code, p.bajaj_product_type_code, p.issuer_product_display_name, NOW(), "
          + "p.record_update_reason, 'L', p.record_update_user, p.is_record_active "
          + "FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate",
      nativeQuery = true)
  public void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO products (`product_code`, `product_type_code`, `bajaj_product_type_code`, "
          + "`issuer_product_display_name`, `record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT p.product_code, p.product_type_code, p.bajaj_product_type_code, p.issuer_product_display_name, NOW(), "
          + "p.record_update_reason, 'L', p.record_update_user, p.is_record_active "
          + "FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate "
          + "AND p.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(
      value = "SELECT * FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products  "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable \n",
      countQuery = "SELECT count(*) FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products  "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate",
      nativeQuery = true)
  public Page<Product> findAllCategoriesForDataMovement(@Param("recordStatus") String recordStatus,
      Pageable pageable);

  @Query(
      value = "SELECT * FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products "
          + "WHERE issuer_product_display_name LIKE %:term% "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') "
          + "GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate ",
      nativeQuery = true)
  public List<Product> findAllProductsWithTerm(@Param("term") String term);

  @Query(
      value = "SELECT * FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products "
          + "WHERE product_type_code = :productDisplayName "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate ",
      nativeQuery = true)
  public List<Product> findProductByName(@Param("productDisplayName") String productDisplayName);

  @Query(
      value = "SELECT * FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products  "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate "
          + "AND p.record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable \n",
      countQuery = "SELECT count(*) FROM products p INNER JOIN "
          + "(SELECT product_code, MAX(record_update_timestamp) AS maxdate FROM products  "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY product_code) pr "
          + "ON p.product_code = pr.product_code AND p.record_update_timestamp = pr.maxdate "
          + "AND p.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public Page<Product> findAllCategoriesForDataMovement(@Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

}
