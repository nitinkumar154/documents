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

import com.innoviti.emi.entity.core.QScheme;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeComposite;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface SchemeRepository extends BaseRepository<Scheme, SchemeComposite>,
    QueryDslPredicateExecutor<Scheme>, QuerydslBinderCustomizer<QScheme> {

  public Scheme findTop1BySchemeCompositeInnoIssuerSchemeCodeOrderBySchemeCompositeCrtupdDateDesc(
      String innoIssuerSchemeCode);

  @Query(value = "SELECT s FROM Scheme s WHERE s.bajajIssuerSchemeCode=?1 AND (s.crtupdStatus = 'N' OR s.crtupdStatus='U') "
      +"ORDER BY s.schemeComposite.crtupdDate DESC")
  public List<Scheme> findLatestSchemeByBajajSchemeCode(String bajajIssuerSchemeCode, Pageable page);

  public Scheme findTop1ByBajajIssuerSchemeCode(String bajajSchemeCode);

  // do NOT delete
  /*
   * @Query(value = "SELECT s.* FROM Scheme s WHERE s.issuer_bank_code = " +
   * "(SELECT issuer_bank_code FROM issuer_banks WHERE issuer_bank_code = ?2 " +
   * "ORDER BY record_update_timestamp DESC LIMIT 1) AND s.emi_tenure_code = " +
   * "(SELECT emi_tenure_code FROM emi_tenures WHERE emi_tenure_code = ?3 " +
   * "ORDER BY record_update_timestamp DESC LIMIT 1) AND  s.issuer_scheme_code = ?1 " +
   * "ORDER BY record_update_timestamp DESC", nativeQuery = true) public Scheme
   * findSchemeWithAllData(String innoIssuerSchemeCode, String innoIssuerBankCode, String
   * innoTenureCode);
   */

  @Override
  default void customize(QuerydslBindings bindings, QScheme root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }

  @Query(
      value = "SELECT * FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate "
          + " \n#pageable\n",
      countQuery = "SELECT count(*) FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate",

      nativeQuery = true)
  public Page<Scheme> findAllSchemes(Pageable pageable);

  @Query(
      value = "SELECT * FROM issuer_schemes i INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes WHERE "
          + "issuer_bank_code = :bankCode AND emi_tenure_code = :tenureCode "
          + "GROUP BY issuer_scheme_code) ism ON "
          + "ism.issuer_scheme_code = i.issuer_scheme_code AND i.record_update_timestamp = maxdate "
          + "\n#pageable\n",
      countQuery = "SELECT count(*) FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes WHERE "
          + " issuer_bank_code = :bankCode AND emi_tenure_code = :tenureCode "
          + "GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate",
      nativeQuery = true)
  public Page<Scheme> findAllSchemesByFilter(@Param("bankCode") String bankCode,
      @Param("tenureCode") String tenureCode, Pageable pageable);

  @Query("SELECT MAX(m.schemeComposite.crtupdDate) FROM Scheme m WHERE m.crtupdStatus='L' ORDER BY m.schemeComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO issuer_schemes (`issuer_scheme_code`, `bajaj_issuer_scheme_code`, `issuer_scheme_display_name`, `issuer_bank_code`, "
          + "`issuerBank_record_update_timestamp`, `emi_tenure_code`, `tenure_record_update_timestamp`, `issuer_scheme_processing_fees`, "
          + "`advance_emi`, `brand_subvention`, `merchant_subvention`, `bank_subvention`, `innoviti_subvention`, "
          + "`issuer_rate_of_interest`, `scheme_start_date`, `scheme_end_date`, `max_amount`, "
          + "`min_amount`, `general_scheme`, `issuer_cashback_flag`, "
          + "`record_update_timestamp`, `record_update_reason`, `record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT s.issuer_scheme_code, s.bajaj_issuer_scheme_code, s.issuer_scheme_display_name, s.issuer_bank_code, "
          + "s.issuerBank_record_update_timestamp, s.emi_tenure_code, s.tenure_record_update_timestamp, s.issuer_scheme_processing_fees, "
          + "s.advance_emi, s.brand_subvention, s.merchant_subvention, s.bank_subvention, s.innoviti_subvention, "
          + "s.issuer_rate_of_interest, s.scheme_start_date, s.scheme_end_date, s.max_amount, "
          + "s.min_amount, s.general_scheme, s.issuer_cashback_flag, NOW(), s.record_update_reason, "
          + "'L', s.record_update_user, s.is_record_active  " + "FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate",
      nativeQuery = true)
  public void prepareDataForMovement();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO issuer_schemes (`issuer_scheme_code`, `bajaj_issuer_scheme_code`, `issuer_scheme_display_name`, `issuer_bank_code`, "
          + "`issuerBank_record_update_timestamp`, `emi_tenure_code`, `tenure_record_update_timestamp`, `issuer_scheme_processing_fees`, "
          + "`advance_emi`, `brand_subvention`, `merchant_subvention`, `bank_subvention`, `innoviti_subvention`, "
          + "`issuer_rate_of_interest`, `scheme_start_date`, `scheme_end_date`, `max_amount`, "
          + "`min_amount`, `general_scheme`, `issuer_cashback_flag`, "
          + "`record_update_timestamp`, `record_update_reason`, `record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT s.issuer_scheme_code, s.bajaj_issuer_scheme_code, s.issuer_scheme_display_name, s.issuer_bank_code, "
          + "s.issuerBank_record_update_timestamp, s.emi_tenure_code, s.tenure_record_update_timestamp, s.issuer_scheme_processing_fees, "
          + "s.advance_emi, s.brand_subvention, s.merchant_subvention, s.bank_subvention, s.innoviti_subvention, "
          + "s.issuer_rate_of_interest, s.scheme_start_date, s.scheme_end_date, s.max_amount, "
          + "s.min_amount, s.general_scheme, s.issuer_cashback_flag, NOW(), s.record_update_reason, "
          + "'L', s.record_update_user, s.is_record_active  " + "FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate "
          + "AND s.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(
      value = "SELECT * FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate",
      nativeQuery = true)
  public Page<Scheme> findAllSchemesForDataMovement(@Param("recordStatus") String recordStatus,
      Pageable pageable);

  @Query(
      value = "SELECT * FROM issuer_schemes i INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes WHERE "
          + "issuer_bank_code <> 'ISB0000000012' " + "GROUP BY issuer_scheme_code) ism ON "
          + "ism.issuer_scheme_code = i.issuer_scheme_code AND i.record_update_timestamp = maxdate ",
      nativeQuery = true)
  public List<Scheme> findAllNonBajajSchemes();

  @Query(
      value = "SELECT * FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "WHERE issuer_scheme_display_name = :schemeDisplayName "
          + "AND (record_update_status = 'N' OR record_update_status = 'U') GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate",
      nativeQuery = true)
  public List<Scheme> findSchemeByName(@Param("schemeDisplayName") String schemeDisplayName);

  @Query(
      value = "SELECT * FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate "
          + "AND s.record_update_timestamp > :latestDateForEntity "
          + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate "
          + "AND s.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public Page<Scheme> findAllSchemesForDataMovement(@Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

}
