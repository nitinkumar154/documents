package com.innoviti.emi.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.innoviti.emi.entity.master.SchemeModelMaster;
import com.innoviti.emi.entity.master.SchemeModelMasterComposite;
import com.innoviti.emi.repository.BaseRepository;

public interface SchemeModelMasterRepository extends BaseRepository<SchemeModelMaster, SchemeModelMasterComposite>{

  List<SchemeModelMaster> findBySchemeModelMasterCompositeSchemeId(Integer schemeId);
  
  @Query(value = "SELECT * FROM scheme_model_bfl i INNER JOIN "
      +"(SELECT schemeid, MAX(record_update_date) AS maxdate FROM scheme_model_bfl "
      +"WHERE record_update_status = 'N' OR record_update_status = 'U' "
      +"GROUP BY schemeid) ism "
      +"ON i.record_update_date = ism.maxdate WHERE i.schemeid= ?1", nativeQuery = true)
  List<SchemeModelMaster> findLatestSchemeModelMasterByScheme(Integer schemeId);
}
