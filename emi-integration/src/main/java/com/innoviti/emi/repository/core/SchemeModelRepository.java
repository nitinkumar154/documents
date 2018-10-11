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

import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.QSchemeModel;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelComposite;
import com.innoviti.emi.repository.BaseRepository;
import com.querydsl.core.types.dsl.StringPath;

public interface SchemeModelRepository extends BaseRepository<SchemeModel, SchemeModelComposite>,
    QueryDslPredicateExecutor<SchemeModel>, QuerydslBinderCustomizer<QSchemeModel> {

  public SchemeModel findTop1BySchemeModelCompositeInnoSchemeModelCodeOrderBySchemeModelCompositeCrtupdDateDesc(
      String innoSchemeModelCode);

  @Override
  default void customize(QuerydslBindings bindings, QSchemeModel root) {
    bindings.bind(String.class)
        .first((StringPath path, String value) -> path.containsIgnoreCase(value));
  }
  public SchemeModel findTop1BySchemeAndModelOrderBySchemeModelCompositeCrtupdDateDesc(Scheme scheme, Model model);
  
  public SchemeModel findTop1BySchemeOrderBySchemeModelCompositeCrtupdDateDesc(Scheme scheme);
  public SchemeModel findTop1ByModelOrderBySchemeModelCompositeCrtupdDateDesc(Model model);
  
  @Query(value = "SELECT s FROM SchemeModel s WHERE s.scheme.schemeComposite.innoIssuerSchemeCode=?1")
  public List<SchemeModel> getAllSchemeModelBySchemeCode(String schemeCode);
  
  @Query(value = "SELECT s FROM SchemeModel s WHERE s.scheme.schemeComposite.innoIssuerSchemeCode=?1 AND s.model.modelComposite.innoModelCode=?2")
  public List<SchemeModel> getSchemeModelBySchemeAndModelCode(String innoIssuerSchemeCode, String innoModelCode);
  @Query(
      value = "SELECT * FROM issuer_scheme_model i INNER JOIN "
          + "(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
          + "GROUP BY issuer_scheme_model_code) ism "
          + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.record_update_timestamp = ism.maxdate "
          + "\n#pageable\n",
      countQuery = "SELECT count(*) FROM issuer_schemes s INNER JOIN "
          + "(SELECT issuer_scheme_code, MAX(record_update_timestamp) AS maxdate FROM issuer_schemes "
          + "GROUP BY issuer_scheme_code) ism "
          + "ON s.issuer_scheme_code = ism.issuer_scheme_code AND s.record_update_timestamp = ism.maxdate",
      nativeQuery = true)

  public Page<SchemeModel> findAllSchemesModels(Pageable pageable);

  @Query("SELECT MAX(m.schemeModelComposite.crtupdDate) FROM SchemeModel m WHERE m.crtupdStatus='L' ORDER BY m.schemeModelComposite.crtupdDate DESC")
  public Date getLatestRecordForPreparingData();

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO issuer_scheme_model (`issuer_scheme_model_code`, `issuer_scheme_code`, `scheme_record_update_timestamp`, `model_code`, `model_record_update_timestamp`, "
          + "`record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT i.issuer_scheme_model_code, i.issuer_scheme_code, i.scheme_record_update_timestamp, i.model_code, i.model_record_update_timestamp, "
          + "NOW(), i.record_update_reason, " + "'L', i.record_update_user, i.is_record_active "
          + "FROM issuer_scheme_model i INNER JOIN "
          + "(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY issuer_scheme_model_code) ism "
          + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.record_update_timestamp = ism.maxdate",
      nativeQuery = true)
  public void prepareDataForMovement();


  public List<SchemeModel> findByModelIn(List<Model> models);

  @Transactional
  @Modifying
  @Query(
      value = "INSERT INTO issuer_scheme_model (`issuer_scheme_model_code`, `issuer_scheme_code`, `scheme_record_update_timestamp`, `model_code`, `model_record_update_timestamp`, "
          + "`record_update_timestamp`, `record_update_reason`, "
          + "`record_update_status`, `record_update_user`, `is_record_active`) "
          + "SELECT i.issuer_scheme_model_code, i.issuer_scheme_code, i.scheme_record_update_timestamp, i.model_code, i.model_record_update_timestamp, "
          + "NOW(), i.record_update_reason, " + "'L', i.record_update_user, i.is_record_active "
          + "FROM issuer_scheme_model i INNER JOIN "
          + "(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model  "
          + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY issuer_scheme_model_code) ism "
          + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.record_update_timestamp = ism.maxdate "
          + "AND i.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public void prepareDataForMovement(@Param("latestDateForEntity") Date latestDateForEntity);

  @Query(value = "SELECT * FROM issuer_scheme_model i INNER JOIN "
      + "(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model  "
      + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_scheme_model_code) ism "
      + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.record_update_timestamp = ism.maxdate "
      + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM issuer_scheme_model i INNER JOIN "
          + "(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model  "
          + "WHERE record_update_status = :recordStatus GROUP BY issuer_scheme_model_code) ism "
          + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.record_update_timestamp = ism.maxdate",
      nativeQuery = true)
  public Page<SchemeModel> findAllSchemeModelsForDataMovement(
      @Param("recordStatus") String recordStatus, Pageable pageable);

  public List<SchemeModel> findBySchemeIn(List<Scheme> schemes);

  @Query(
      value = "SELECT * FROM issuer_scheme_model "
          + "WHERE issuer_scheme_code IN :schemeList AND record_update_status = 'N' \n#pageable\n",
      countQuery = "SELECT count(*) FROM issuer_schemes "
          + "WHERE issuer_scheme_code IN :schemeList AND record_update_status = 'N' ",
      nativeQuery = true)
  public Page<SchemeModel> findAllSchemeModelsFromScheme(
      @Param("schemeList") List<String> schemeList, Pageable pageable);

  @Query(value = "SELECT * FROM issuer_scheme_model i INNER JOIN "
      + "(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model  "
      + "WHERE record_update_status = :recordStatus " + "GROUP BY issuer_scheme_model_code) ism "
      + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.record_update_timestamp = ism.maxdate "
      + "AND i.record_update_timestamp > :latestDateForEntity "
      + "ORDER BY record_update_timestamp DESC \n-- #pageable\n",
      countQuery = "SELECT count(*) FROM issuer_scheme_model i INNER JOIN "
          + "(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model  "
          + "WHERE record_update_status = :recordStatus GROUP BY issuer_scheme_model_code) ism "
          + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.record_update_timestamp = ism.maxdate "
          + "AND i.record_update_timestamp > :latestDateForEntity",
      nativeQuery = true)
  public Page<SchemeModel> findAllSchemeModelsForDataMovement(
      @Param("recordStatus") String recordStatus,
      @Param("latestDateForEntity") Date latestDateForEntity, Pageable pageable);

  @Query(
      value = "SELECT * FROM issuer_scheme_model WHERE model_code = :innoModelCode "
          + "AND issuer_scheme_code = :innoIssuerSchemeCode AND record_update_status = 'N' LIMIT 1",
      nativeQuery = true)
  public List<SchemeModel> findSchemeMapping(
      @Param("innoIssuerSchemeCode") String innoIssuerSchemeCode,
      @Param("innoModelCode") String innoModelCode);
}
