package com.innoviti.emi.repository.core;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.core.SchemeModelTerminalComposite;
import com.innoviti.emi.repository.BaseRepository;

public interface SchemeModelTerminalRepository
    extends BaseRepository<SchemeModelTerminal, SchemeModelTerminalComposite> {

  public List<SchemeModelTerminal> findTop1BySchemeModelTerminalCompositeUtidAndSchemeModelTerminalCompositeSchemeModelOrderBySchemeModelTerminalCompositeCrtupdDateDesc(
      String utId, String innoSchemeModelCode);

  // @Transactional
  // @Modifying
  // @Query(
  // value = "INSERT INTO `issuer_scheme_model_terminal` (`issuer_scheme_model_code`, `utid`,
  // `issuer_scheme_onus_offus`, `issuer_scheme_terminal_sync_status`, "
  // + "`issuer_custom_field`, `fk_scheme_model_date`, `record_update_timestamp`,
  // `record_update_reason`, "
  // + "`record_update_status`, `record_update_user`, `is_record_active`) "
  // + "SELECT `issuer_scheme_model_code`, `utid`, `issuer_scheme_onus_offus`,
  // `issuer_scheme_terminal_sync_status`, "
  // + "`issuer_custom_field`, `fk_scheme_model_date`, NOW(), `record_update_reason`, "
  // + "'L', `record_update_user`, `is_record_active` FROM issuer_scheme_model_terminal i WHERE
  // i.record_update_timestamp = "
  // + "(SELECT MAX(ism.record_update_timestamp) FROM issuer_scheme_model_terminal ism "
  // + "WHERE ism.issuer_scheme_model_code = i.issuer_scheme_model_code AND ism.utid = i.utid) "
  // + "GROUP BY issuer_scheme_model_code, utid",
  // nativeQuery = true)
  // public void prepareDataForMovement();

  @Query("SELECT MAX(m.schemeModelTerminalComposite.crtupdDate) FROM SchemeModelTerminal m WHERE m.crtupdStatus='L' ORDER BY m.schemeModelTerminalComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO `issuer_scheme_model_terminal` (`issuer_scheme_model_code`, `utid`, `dealer_id`, `bajaj_product_type_code`, `issuer_scheme_onus_offus`, `issuer_scheme_terminal_sync_status`, "
          + "`issuer_custom_field`, `fk_scheme_model_date`, `record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT i.issuer_scheme_model_code, i.utid, i.dealer_id, i.bajaj_product_type_code, i.issuer_scheme_onus_offus, i.issuer_scheme_terminal_sync_status, "
          + "i.issuer_custom_field, i.fk_scheme_model_date, NOW(), i.record_update_reason, "
          + "'L', i.record_update_user, i.is_record_active FROM issuer_scheme_model_terminal i INNER JOIN"
          + "(SELECT issuer_scheme_model_code, utid, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model_terminal  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY issuer_scheme_model_code, utid) ism "
          + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.utid = ism.utid "
          + "AND i.record_update_timestamp = ism.maxdate",
      nativeQuery = true)
  public void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO `issuer_scheme_model_terminal` (`issuer_scheme_model_code`, `utid`, `dealer_id`, `bajaj_product_type_code`, `issuer_scheme_onus_offus`, `issuer_scheme_terminal_sync_status`, "
          + "`issuer_custom_field`, `fk_scheme_model_date`, `record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT i.issuer_scheme_model_code, i.utid, i.dealer_id, i.bajaj_product_type_code, i.issuer_scheme_onus_offus, i.issuer_scheme_terminal_sync_status, "
          + "i.issuer_custom_field, i.fk_scheme_model_date, NOW(), i.record_update_reason, "
          + "'L', i.record_update_user, i.is_record_active FROM issuer_scheme_model_terminal i INNER JOIN"
          + "(SELECT issuer_scheme_model_code, utid, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model_terminal  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY issuer_scheme_model_code, utid) ism "
          + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.utid = ism.utid "
          + "AND i.record_update_timestamp = ism.maxdate "
          + "AND i.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  public Page<SchemeModelTerminal> findByCrtupdStatus(String recordStatus, Pageable pageable);

  @Query(
      value = "SELECT * FROM issuer_scheme_model_terminal WHERE issuer_scheme_model_code = ?1 AND utid = ?2",
      nativeQuery = true)
  public Iterable<SchemeModelTerminal> findBySchemeCodeAndUtid(String innoSchemeModelCode,
      String utid);
  
  @Query(value = "SELECT * FROM issuer_scheme_model_terminal WHERE issuer_scheme_model_code = ?1",
	      nativeQuery = true)
	  public List<SchemeModelTerminal> findBySchemeCode(String innoSchemeModelCode);

  @Query(
      value = "SELECT * FROM issuer_scheme_model_terminal WHERE record_update_status = :recordStatus "
          + "AND record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM issuer_scheme_model_terminal WHERE record_update_status = :recordStatus "
          + "AND record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public Page<SchemeModelTerminal> findByCrtupdStatus(@Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

}
