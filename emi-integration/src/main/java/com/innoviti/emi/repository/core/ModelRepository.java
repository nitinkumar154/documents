package com.innoviti.emi.repository.core;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.ModelComposite;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.QModel;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface ModelRepository extends BaseRepository<Model, ModelComposite>,
    QueryDslPredicateExecutor<Model>, QuerydslBinderCustomizer<QModel> {

  public Model findTop1ByModelCompositeInnoModelCodeOrderByModelCompositeCrtupdDateDesc(
      String innoModelCode);

  public Model findTop1ByBajajModelCodeOrderByModelCompositeCrtupdDateDesc(String bajajModelCode);

  public List<Model> findByProductIn(List<Product> products);

  public List<Model> findByProduct(Product product);

  public List<Model> findByProductAndManufacturerBajajManufacturerCodeIn(Product product,
      List<String> manufacturerIds);

  public List<Model> findByProductProductCompositeProductCode(String productCode);
  
  public Model findTop1ByBajajModelCode(String bajajModelCode);
  
  @Query(value = "SELECT * from models WHERE bajaj_model_code= ?1 AND (record_update_status = 'N' OR record_update_status = 'U') "
      + "ORDER BY record_update_timestamp DESC LIMIT 1", nativeQuery = true)
  public Model findLatestModelByBajajModelCode(String bajajModelCode);

  @Query(value = "SELECT * FROM models m INNER JOIN "
      + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
      + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
      + "GROUP BY model_code) mn "
      + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate where m.product_code=?1",

      nativeQuery = true)
  public List<Model> findByProductCode(String productCode);

  @Query("SELECT s from Model s where s.product=?1 AND s.bajajModelExpiryDate >= ?2 ")
  public List<Model> findByProductAndModelExpiryDate(Product product,
      @Temporal(TemporalType.DATE) Date expiryDate);
  // DO NOT DELETE
  /*
   * @Query( value = "SELECT md.* FROM Models md WHERE md.manufacturer_code = " +
   * "(SELECT manufacturer_code FROM manufacturers WHERE manufacturer_code = ?2 " +
   * "ORDER BY record_update_timestamp DESC LIMIT 1) AND md.product_type_code = " +
   * "(SELECT product_type_code FROM products WHERE product_type_code = ?3 " +
   * "ORDER BY record_update_timestamp DESC LIMIT 1) AND md.category_code = " +
   * "(SELECT category_code FROM categories WHERE category_code = ?4 " +
   * "ORDER BY record_update_timestamp DESC LIMIT 1) AND  md.model_code = ?1 ORDER BY record_update_timestamp DESC"
   * , nativeQuery = true) public Model findModelWithAllData(String innoModelCode, String
   * innoManufacturerCode, String innoProductTypeCode, String innoCategoryCode);
   */

  @Override
  default void customize(QuerydslBindings bindings, QModel root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }

  @Query(
      value = "SELECT * FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
          + "WHERE model_code <> 'XXXXXXXXXXGEN' AND (record_update_status = 'N' OR record_update_status = 'U') "
          + "GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate "
          + "\n#pageable\n",
      countQuery = "SELECT COUNT(*) FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
          + "WHERE model_code <> 'XXXXXXXXXXGEN' AND (record_update_status = 'N' OR record_update_status = 'U') "
          + "GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate",
      nativeQuery = true)
  public Page<Model> findAllModels(Pageable pageable);

  @Query("SELECT m FROM Model m WHERE CONCAT(m.modelComposite.crtupdDate, m.modelComposite.innoModelCode) IN "
      + "(SELECT CONCAT(MAX(ml.modelComposite.crtupdDate), ml.modelComposite.innoModelCode) FROM Model ml "
      + "WHERE ml.modelComposite.innoModelCode "
      + "IN :modelCodeList GROUP BY ml.modelComposite.innoModelCode) GROUP BY m.modelComposite.innoModelCode")
  public Iterable<Model> findAllModels(@Param("modelCodeList") List<String> modelCodeList);

  @Query(value = "SELECT * FROM models m INNER JOIN "
      + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models WHERE "
      + "model_code <> 'XXXXXXXXXXGEN' AND product_code = :productCode AND manufacturer_code = :manufacturerCode "
      + "AND category_code = :categoryCode AND (record_update_status = 'N' OR record_update_status = 'U') "
      + "GROUP BY model_code) ml ON "
      + "ml.model_code = m.model_code AND m.record_update_timestamp = maxdate " + " \n#pageable\n",
      countQuery = "SELECT count(*) FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models WHERE "
          + "model_code <> 'XXXXXXXXXXGEN' AND product_code = :productCode AND manufacturer_code = :manufacturerCode "
          + "AND category_code = :categoryCode "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY model_code) ml ON "
          + "ml.model_code = m.model_code AND m.record_update_timestamp = maxdate ",
      nativeQuery = true)
  public Page<Model> findAllModelsByFilter(@Param("productCode") String productCode,
      @Param("manufacturerCode") String manufacturerCode,
      @Param("categoryCode") String categoryCode, Pageable pageable);

  @Query("SELECT MAX(m.modelComposite.crtupdDate) FROM Model m WHERE m.crtupdStatus='L' ORDER BY m.modelComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO models (`model_code`, `bajaj_model_code`, `bajaj_model_number`, `product_code`, "
          + "`product_record_update_timestamp`, `manufacturer_code`, `manufacturer_record_update_timestamp`, `category_code`, "
          + "`category_record_update_timestamp`, `model_display_number`, `bajaj_model_selling_price`, `bajaj_model_expiry_date`, `model_min_selling_price`, "
          + "`model_max_selling_price`, `record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT m.model_code, m.bajaj_model_code, m.bajaj_model_number, m.product_code, "
          + "m.product_record_update_timestamp, m.manufacturer_code, m.manufacturer_record_update_timestamp, m.category_code, "
          + "m.category_record_update_timestamp, m.model_display_number, m.bajaj_model_selling_price, m.bajaj_model_expiry_date, m.model_min_selling_price, "
          + "m.model_max_selling_price, NOW(), m.record_update_reason, "
          + "'L', m.record_update_user, m.is_record_active " + "FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate",
      nativeQuery = true)
  public void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO models (`model_code`, `bajaj_model_code`, `bajaj_model_number`, `product_code`, "
          + "`product_record_update_timestamp`, `manufacturer_code`, `manufacturer_record_update_timestamp`, `category_code`, "
          + "`category_record_update_timestamp`, `model_display_number`, `bajaj_model_selling_price`, `bajaj_model_expiry_date`, `model_min_selling_price`, "
          + "`model_max_selling_price`, `record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT m.model_code, m.bajaj_model_code, m.bajaj_model_number, m.product_code, "
          + "m.product_record_update_timestamp, m.manufacturer_code, m.manufacturer_record_update_timestamp, m.category_code, "
          + "m.category_record_update_timestamp, m.model_display_number, m.bajaj_model_selling_price, m.bajaj_model_expiry_date, m.model_min_selling_price, "
          + "m.model_max_selling_price, NOW(), m.record_update_reason, "
          + "'L', m.record_update_user, m.is_record_active " + "FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(
      value = "SELECT * FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate",
      nativeQuery = true)
  public Page<Model> findAllModelsForDataMovement(@Param("recordStatus") String recordStatus,
      Pageable pageable);

  @Query(
      value = "SELECT * FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
          + "WHERE model_display_number LIKE %:term% "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') "
          + "GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate ",
      nativeQuery = true)
  public List<Model> findAllModelsWithTerm(@Param("term") String term);

  @Query(
      value = "SELECT * FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
          + "WHERE model_display_number = :modelDisplayName "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate ",
      nativeQuery = true)
  public List<Model> findModelByName(@Param("modelDisplayName") String modelDisplayName);

  @Query(
      value = "SELECT * FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM models m INNER JOIN "
          + "(SELECT model_code, MAX(record_update_timestamp) AS maxdate FROM models "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY model_code) mn "
          + "ON m.model_code = mn.model_code AND m.record_update_timestamp = mn.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public Page<Model> findAllModelsForDataMovement(@Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

}
