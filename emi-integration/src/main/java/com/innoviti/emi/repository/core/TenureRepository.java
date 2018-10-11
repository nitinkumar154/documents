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

import com.innoviti.emi.entity.core.QTenure;
import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.entity.core.TenureComposite;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface TenureRepository extends BaseRepository<Tenure, TenureComposite>,
    QueryDslPredicateExecutor<Tenure>, QuerydslBinderCustomizer<QTenure> {

  Tenure findTop1ByTenureCompositeInnoTenureCodeOrderByTenureCompositeCrtupdDateDesc(
      String innoTenureCode);

  Tenure findByTenureMonth(String tenureMonth);
  
  Tenure findTop1ByTenureMonthOrderByTenureCompositeCrtupdDateDesc(String tenureMonth);

  @Override
  default void customize(QuerydslBindings bindings, QTenure root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }

  @Query(
      value = "SELECT * FROM emi_tenures e INNER JOIN "
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate "
          + "\n#pageable\n",
      countQuery = "SELECT count(*) FROM emi_tenures e INNER JOIN "
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate",
      nativeQuery = true)

  public Page<Tenure> findAllTenures(Pageable pageable);

  @Query("SELECT MAX(m.tenureComposite.crtupdDate) FROM Tenure m WHERE m.crtupdStatus='L' ORDER BY m.tenureComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO emi_tenures (`emi_tenure_code`, `emi_tenure_months`, `emi_tenure_display_name`,"
          + "`record_update_timestamp`, `record_update_reason`, `record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT e.emi_tenure_code, e.emi_tenure_months, e.emi_tenure_display_name, NOW(), e.record_update_reason, "
          + "'L', e.record_update_user, e.is_record_active " + "FROM emi_tenures e INNER JOIN"
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate",
      nativeQuery = true)
  void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO emi_tenures (`emi_tenure_code`, `emi_tenure_months`, `emi_tenure_display_name`,"
          + "`record_update_timestamp`, `record_update_reason`, `record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT e.emi_tenure_code, e.emi_tenure_months, e.emi_tenure_display_name, NOW(), e.record_update_reason, "
          + "'L', e.record_update_user, e.is_record_active " + "FROM emi_tenures e INNER JOIN"
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate "
          + "AND e.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(
      value = "SELECT * FROM emi_tenures e INNER JOIN "
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM emi_tenures e INNER JOIN "
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate",
      nativeQuery = true)
  Page<Tenure> findAllTenuresForDataMovement(@Param("recordStatus") String recordStatus,
      Pageable pageable);

  @Query(
      value = "SELECT * FROM emi_tenures e INNER JOIN "
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE emi_tenure_display_name LIKE %:term% "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') "
          + "GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate ",
      nativeQuery = true)
  List<Tenure> findAllTenuresWithTerm(@Param("term") String term);

  @Query(
      value = "SELECT * FROM emi_tenures e INNER JOIN "
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE emi_tenure_months = :tenureMonth "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') "
          + "GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate ",
      nativeQuery = true)
  List<Tenure> findTenureByName(@Param("tenureMonth") String tenureMonth);

  @Query(
      value = "SELECT * FROM emi_tenures e INNER JOIN "
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate "
          + "AND e.record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM emi_tenures e INNER JOIN "
          + "(SELECT emi_tenure_code, MAX(record_update_timestamp) AS maxdate FROM emi_tenures "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY emi_tenure_code) em "
          + "ON e.emi_tenure_code = em.emi_tenure_code AND e.record_update_timestamp = em.maxdate "
          + "AND e.record_update_timestamp > :latestDateForEntity ",
      nativeQuery = true)
  Page<Tenure> findAllTenuresForDataMovement(@Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

}
