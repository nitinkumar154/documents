package com.innoviti.emi.repository.master;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.innoviti.emi.entity.master.SchemeDealerMaster;
import com.innoviti.emi.entity.master.SchemeDealerMasterComposite;
import com.innoviti.emi.repository.BaseRepository;

public interface SchemeDealerMasterRepository extends BaseRepository<SchemeDealerMaster, SchemeDealerMasterComposite>{
  SchemeDealerMaster findTop1BySchemeDealerMasterCompositeSchemeIdOrderBySchemeDealerMasterCompositeCrtupdDateDesc(Integer schemeId);
  
  @Query("select u from SchemeDealerMaster u where u.schemeDealerMasterComposite.schemeId=?1 group by u.schemeDealerMasterComposite.supplierId")
  List<SchemeDealerMaster> findBySchemeIdGroupBySupplierId(Integer schemeId);
  
  @Query(value = "SELECT * FROM scheme_dealer_bfl m INNER JOIN "+
          "(SELECT scheme_id, MAX(record_update_date) AS maxdate FROM scheme_dealer_bfl "+ 
           "WHERE GROUP BY scheme_id) mn "+ 
           "ON m.scheme_id = mn.scheme_id AND m.record_update_date = mn.maxdate m.scheme_id = ?1"+ 
           "ORDER BY record_update_date" , nativeQuery = true)
  List<SchemeDealerMaster> findLatestRecordBySchemeId(Integer schemeId);
  
  @Query(value="SELECT s FROM SchemeDealerMaster s WHERE s.schemeDealerMasterComposite.schemeId=?1 AND s.schemeDealerMasterComposite.supplierId = ?2"
      + " ORDER BY s.schemeDealerMasterComposite.crtupdDate DESC")
  List<SchemeDealerMaster> findLatestBySchemeIdAndSupplierId(Integer schemeId, Integer supplierId, Pageable pageable);
  
  @Query(value = "SELECT * FROM scheme_dealer_bfl WHERE scheme_id=?1 AND (record_update_status = 'N' OR record_update_status='U') "
      + "ORDER BY record_update_date DESC LIMIT 1", nativeQuery = true)
  SchemeDealerMaster findLastestSchemeDealerBySchemeId(Integer schemeId);
}
