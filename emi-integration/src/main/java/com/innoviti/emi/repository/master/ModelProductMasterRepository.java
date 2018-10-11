package com.innoviti.emi.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.innoviti.emi.entity.master.ModelProductMaster;
import com.innoviti.emi.repository.BaseRepository;

public interface ModelProductMasterRepository extends BaseRepository<ModelProductMaster, String> {
  ModelProductMaster findTop1ByModelProductMasterCompositeModelIdOrderByModelProductMasterCompositeCrtupdDateDesc(Integer modelId);
  
  @Query(value = "SELECT * FROM model_product_bfl m INNER JOIN "+
          "(SELECT code, MAX(record_update_date) AS maxdate FROM model_product_bfl "+
          "WHERE code = ?1 GROUP BY code) mn "+
           "ON m.code = mn.code AND m.record_update_date = mn.maxdate "+
           "ORDER BY record_update_date", nativeQuery=true)
  List<ModelProductMaster> findLatestRecordByProductCode(String productCode);
  
  @Query(value = "SELECT * FROM model_product_bfl where modelid=?1 AND (record_update_status='N' OR record_update_status='U') ORDER BY record_update_date DESC "
      + "LIMIT 1", nativeQuery = true)
  ModelProductMaster findLatestProductMasterByModel(Integer modelId);
}
