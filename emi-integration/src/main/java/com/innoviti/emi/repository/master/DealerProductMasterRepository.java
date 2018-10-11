package com.innoviti.emi.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.innoviti.emi.entity.master.DealerProductMaster;
import com.innoviti.emi.entity.master.DealerProductMasterComposite;
import com.innoviti.emi.repository.BaseRepository;

public interface DealerProductMasterRepository extends BaseRepository<DealerProductMaster, DealerProductMasterComposite>{
  DealerProductMaster findTop1ByDealerProductMasterCompositeSupplierIdOrderByDealerProductMasterCompositeSupplierIdDesc(Integer supplierId);
  DealerProductMaster findTop1ByDealerProductMasterCompositeSupplierIdAndDealerProductMasterCompositeCode(Integer supplierId, String productTypeCode);
  
  List<DealerProductMaster> findByDealerProductMasterCompositeCode(String productCode);
  
  @Query(value = "SELECT * FROM dealer_product_bfl i INNER JOIN "
          +"(SELECT code, MAX(record_update_date) AS maxdate FROM dealer_product_bfl "
          +"WHERE record_update_status = 'N' OR record_update_status = 'U' "
          +"GROUP BY code) ism "
          +"ON i.code = ism.code AND i.record_update_date = ism.maxdate WHERE i.supplierid= ?1", nativeQuery = true)
  List<DealerProductMaster> findByDealerProductMasterCompositeSupplierId(Integer supplierId);
}
