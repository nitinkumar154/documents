package com.innoviti.emi.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.entity.master.SchemeMasterComposite;
import com.innoviti.emi.repository.BaseRepository;

public interface SchemeMasterRepository extends BaseRepository<SchemeMaster, SchemeMasterComposite> {

  SchemeMaster findBySchemeMasterCompositeSchemeId(Integer schemeId);
  
  List<SchemeMaster> findByProduct(String productCode);
  
  @Query(value = "SELECT * FROM scheme_bfl s INNER JOIN "
          +"(SELECT schemeid, MAX(record_update_date) AS maxdate FROM scheme_bfl " 
          +"WHERE (gen_sch ='N' AND dealer_mapping = 'Y' AND model_mapping = 'N') "
          +"AND  product=?1 "
          +"GROUP BY schemeid) ism "
          +"ON s.schemeid = ism.schemeid AND s.record_update_date = ism.maxdate", nativeQuery = true) 
  List<SchemeMaster> findInvoiceFinancingSchemesByProduct(String productCode);

  @Query(value = "SELECT * FROM scheme_bfl s INNER JOIN " 
      + "(SELECT schemeid, MAX(record_update_date) AS maxdate FROM scheme_bfl "
      + "WHERE (gen_sch ='Y') "
      + "OR (gen_sch='N' AND dealer_mapping = 'N' AND model_mapping = 'N') "
      +  "GROUP BY schemeid) ism "
      + "ON s.schemeid = ism.schemeid AND s.record_update_date = ism.maxdate", nativeQuery = true)
  List<SchemeMaster> findGeneralSchemes();
  
  SchemeMaster findTop1BySchemeMasterCompositeSchemeIdOrderBySchemeMasterCompositeCrtupdDateDesc(Integer schemeId);
}
