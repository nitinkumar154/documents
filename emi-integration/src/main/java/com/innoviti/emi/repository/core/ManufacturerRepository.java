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

import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.ManufacturerComposite;
import com.innoviti.emi.entity.core.QManufacturer;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface ManufacturerRepository extends BaseRepository<Manufacturer, ManufacturerComposite>,
    QueryDslPredicateExecutor<Manufacturer>, QuerydslBinderCustomizer<QManufacturer> {

  public Manufacturer findTop1ByManufacturerCompositeInnoManufacturerCodeOrderByManufacturerCompositeCrtupdDateDesc(
      String innoManufacturerCode);

  public Manufacturer findTop1ByBajajManufacturerCodeOrderByManufacturerCompositeCrtupdDateDesc(
      String bajajManufacturerCode);

  public Manufacturer findTop1ByBajajManufacturerCode(String bajajManufacturerCode);
  
  @Query(value="SELECT * FROM manufacturers WHERE bajaj_manufacturer_code=?1 AND ( record_update_status = 'N' OR record_update_status = 'U') "
      + " ORDER BY record_update_timestamp DESC LIMIT 1", nativeQuery = true)
  public Manufacturer findLatestByBajajManufacturerCode(String bajajManufacturerCode);
  @Override
  default void customize(QuerydslBindings bindings, QManufacturer root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }

  @Query(
      value = "SELECT * FROM manufacturers m INNER JOIN "
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate "
          + "\n#pageable\n",
      countQuery = "SELECT COUNT(*) FROM manufacturers m INNER JOIN "
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate",
      nativeQuery = true)
  public Page<Manufacturer> findAllManufacturers(Pageable pageable);

  @Query("SELECT MAX(m.manufacturerComposite.crtupdDate) FROM Manufacturer m WHERE m.crtupdStatus='L' ORDER BY m.manufacturerComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO manufacturers (`manufacturer_code`, `bajaj_manufacturer_code`, `manufacturer_description`, `manufacturer_display_name`,"
          + "`record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT m.manufacturer_code, m.bajaj_manufacturer_code, m.manufacturer_description, m.manufacturer_display_name, NOW(), "
          + "m.record_update_reason, 'L', m.record_update_user, m.is_record_active "
          + "FROM manufacturers m INNER JOIN"
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate",
      nativeQuery = true)
  public void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO manufacturers (`manufacturer_code`, `bajaj_manufacturer_code`, `manufacturer_description`, `manufacturer_display_name`,"
          + "`record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT m.manufacturer_code, m.bajaj_manufacturer_code, m.manufacturer_description, m.manufacturer_display_name, NOW(), "
          + "m.record_update_reason, 'L', m.record_update_user, m.is_record_active "
          + "FROM manufacturers m INNER JOIN"
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(
      value = "SELECT * FROM manufacturers m INNER JOIN "
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM manufacturers m INNER JOIN "
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate",
      nativeQuery = true)
  public Page<Manufacturer> findAllManufacturersForDataMovement(
      @Param("recordStatus") String recordStatus, Pageable pageable);

  @Query(
      value = "SELECT * FROM manufacturers m INNER JOIN "
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM manufacturers m INNER JOIN "
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public Page<Manufacturer> findAllManufacturersForDataMovement(
      @Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

  @Query(
      value = "SELECT * FROM manufacturers m INNER JOIN "
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE manufacturer_display_name LIKE %:term% "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') "
          + "GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate ",
      nativeQuery = true)
  public List<Manufacturer> findAllManufacturerWithTerm(@Param("term") String term);

  @Query(
      value = "SELECT * FROM manufacturers m INNER JOIN "
          + "(SELECT manufacturer_code, MAX(record_update_timestamp) AS maxdate FROM manufacturers "
          + "WHERE manufacturer_display_name = :manufacturerDisplayName "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY manufacturer_code) mn "
          + "ON m.manufacturer_code = mn.manufacturer_code AND m.record_update_timestamp = mn.maxdate ",
      nativeQuery = true)
  public List<Manufacturer> findManufacturerByName(
      @Param("manufacturerDisplayName") String manufacturerDisplayName);

}
