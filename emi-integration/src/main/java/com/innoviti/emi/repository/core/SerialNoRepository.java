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

import com.innoviti.emi.entity.core.QSerialNo;
import com.innoviti.emi.entity.core.SerialNo;
import com.innoviti.emi.entity.core.SerialNoComposite;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface SerialNoRepository extends BaseRepository<SerialNo, SerialNoComposite>,
    QueryDslPredicateExecutor<SerialNo>, QuerydslBinderCustomizer<QSerialNo> {

  SerialNo findTop1BySerialNoCompositeInnoModelSerialNumberOrderBySerialNoCompositeCrtupdDateDesc(
      String innoModelSerialNumber);

  @Override
  default void customize(QuerydslBindings bindings, QSerialNo root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }

  @Query(
      value = "SELECT * FROM model_serial_numbers m INNER JOIN "
          + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY model_serial_number) ms "
          + "ON m.model_serial_number = ms.model_serial_number AND m.record_update_timestamp = ms.maxdate "
          + "\n#pageable\n",
      countQuery = "SELECT count(*) FROM model_serial_numbers m INNER JOIN "
          + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY model_serial_number) ms "
          + "ON m.model_serial_number = ms.model_serial_number "
          + "AND m.record_update_timestamp = ms.maxdate",
      nativeQuery = true)
  public Page<SerialNo> findAllSerialNo(Pageable pageable);

  @Query("SELECT MAX(m.serialNoComposite.crtupdDate) FROM SerialNo m WHERE m.crtupdStatus='L' ORDER BY m.serialNoComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO model_serial_numbers (`model_serial_number`, `manufacturer_model_serial_number`, `model_code`, `model_record_update_timestamp`, "
          + "`is_emi_used`, `record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT m.model_serial_number, m.manufacturer_model_serial_number, m.model_code, m.model_record_update_timestamp, "
          + "m.is_emi_used, NOW(), m.record_update_reason, "
          + "'L', m.record_update_user, m.is_record_active "
          + "FROM model_serial_numbers m INNER JOIN "
          + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY model_serial_number) ms "
          + "ON m.model_serial_number = ms.model_serial_number "
          + "AND m.record_update_timestamp = ms.maxdate",
      nativeQuery = true)
  void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO model_serial_numbers (`model_serial_number`, `manufacturer_model_serial_number`, `model_code`, `model_record_update_timestamp`, "
          + "`is_emi_used`, `record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT m.model_serial_number, m.manufacturer_model_serial_number, m.model_code, m.model_record_update_timestamp, "
          + "m.is_emi_used, NOW(), m.record_update_reason, "
          + "'L', m.record_update_user, m.is_record_active "
          + "FROM model_serial_numbers m INNER JOIN "
          + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY model_serial_number) ms "
          + "ON m.model_serial_number = ms.model_serial_number AND m.record_update_timestamp = ms.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(
      value = "SELECT * FROM model_serial_numbers m INNER JOIN "
          + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY model_serial_number) ms "
          + "ON m.model_serial_number = ms.model_serial_number "
          + "AND m.record_update_timestamp = ms.maxdate "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM model_serial_numbers m INNER JOIN "
          + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY model_serial_number) ms "
          + "ON m.model_serial_number = ms.model_serial_number "
          + "AND m.record_update_timestamp = ms.maxdate",
      nativeQuery = true)
  Page<SerialNo> findAllSerialNoForDataMovement(@Param("recordStatus") String recordStatus,
      Pageable pageable);

  @Query(value = "SELECT * FROM model_serial_numbers m INNER JOIN "
      + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
      + "WHERE manufacturer_model_serial_number = :serialNoDisplayName "
      + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY model_serial_number) ms "
      + "ON m.model_serial_number = ms.model_serial_number "
      + "AND m.record_update_timestamp = ms.maxdate ", nativeQuery = true)
  List<SerialNo> findSerialNoByName(@Param("serialNoDisplayName") String serialNoDisplayName);

  @Query(
      value = "SELECT * FROM model_serial_numbers m INNER JOIN "
          + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY model_serial_number) ms "
          + "ON m.model_serial_number = ms.model_serial_number "
          + "AND m.record_update_timestamp = ms.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM model_serial_numbers m INNER JOIN "
          + "(SELECT model_serial_number, MAX(record_update_timestamp) AS maxdate FROM model_serial_numbers "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY model_serial_number) ms "
          + "ON m.model_serial_number = ms.model_serial_number "
          + "AND m.record_update_timestamp = ms.maxdate "
          + "AND m.record_update_timestamp > :latestDateForEntity ",
      nativeQuery = true)
  Page<SerialNo> findAllSerialNoForDataMovement(@Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

}
