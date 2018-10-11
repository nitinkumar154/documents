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

import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.IssuerBankComposite;
import com.innoviti.emi.entity.core.QIssuerBank;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface IssuerBankRepository extends BaseRepository<IssuerBank, IssuerBankComposite>,
    QueryDslPredicateExecutor<IssuerBank>, QuerydslBinderCustomizer<QIssuerBank> {

  @Query("SELECT i FROM IssuerBank i WHERE i.issuerBankComposite.innoIssuerBankCode = ?1")
  public List<IssuerBank> findByIssuerBankBankCode(String innoIssuerBankCode);

  public IssuerBank findTop1ByIssuerBankCompositeInnoIssuerBankCodeOrderByIssuerBankCompositeCrtupdDateDesc(
      String innoIssuerBankCode);

  public IssuerBank findByEmiBankCode(Integer innoIssuerBankCode);
  
  public IssuerBank findTop1ByEmiBankCodeOrderByIssuerBankCompositeCrtupdDateDesc(Integer innoIssuerBankCode);

  @Override
  default void customize(QuerydslBindings bindings, QIssuerBank root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }

  @Query(
      value = "SELECT * FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate "
          + "\n#pageable\n",
      countQuery = "SELECT COUNT(*) FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate",
      nativeQuery = true)
  public Page<IssuerBank> findAllIssuerBanks(Pageable pageable);

  @Query("SELECT MAX(m.issuerBankComposite.crtupdDate) FROM IssuerBank m WHERE m.crtupdStatus='L' ORDER BY m.issuerBankComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO issuer_banks (`issuer_bank_code`, `issuer_bank_description`, `issuer_bank_disclaimer`, `issuer_bank_display_name`, "
          + "`issuer_default_cashback_flag`, `issuer_min_emi_amount`, `issuer_terms_conditions`, `emi_bank_code`, `record_update_timestamp`, "
          + "`record_update_reason`, `record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT i.issuer_bank_code, i.issuer_bank_description, i.issuer_bank_disclaimer, i.issuer_bank_display_name, "
          + "i.issuer_default_cashback_flag, i.issuer_min_emi_amount, i.issuer_terms_conditions, i.emi_bank_code, NOW(), i.record_update_reason, "
          + "'L', i.record_update_user, i.is_record_active " + "FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate",
      nativeQuery = true)
  public void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO issuer_banks (`issuer_bank_code`, `issuer_bank_description`, `issuer_bank_disclaimer`, `issuer_bank_display_name`, "
          + "`issuer_default_cashback_flag`, `issuer_min_emi_amount`, `issuer_terms_conditions`, `emi_bank_code`, `record_update_timestamp`, "
          + "`record_update_reason`, `record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT i.issuer_bank_code, i.issuer_bank_description, i.issuer_bank_disclaimer, i.issuer_bank_display_name, "
          + "i.issuer_default_cashback_flag, i.issuer_min_emi_amount, i.issuer_terms_conditions, i.emi_bank_code, NOW(), i.record_update_reason, "
          + "'L', i.record_update_user, i.is_record_active " + "FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate "
          + "AND i.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(
      value = "SELECT * FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate",
      nativeQuery = true)
  public Page<IssuerBank> findAllIssuerBanksForDataMovement(
      @Param("recordStatus") String recordStatus, Pageable pageable);

  @Query(
      value = "SELECT * FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE issuer_bank_display_name = :issuerBankDisplayName "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate",
      nativeQuery = true)
  public List<IssuerBank> findIssuerByName(
      @Param("issuerBankDisplayName") String issuerBankDisplayName);

  @Query(
      value = "SELECT * FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate "
          + "AND i.record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM issuer_banks i INNER JOIN "
          + "(SELECT issuer_bank_code, MAX(record_update_timestamp) AS maxdate FROM issuer_banks "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_bank_code) isb "
          + "ON i.issuer_bank_code = isb.issuer_bank_code AND i.record_update_timestamp = isb.maxdate "
          + "AND i.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public Page<IssuerBank> findAllIssuerBanksForDataMovement(
      @Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

}
