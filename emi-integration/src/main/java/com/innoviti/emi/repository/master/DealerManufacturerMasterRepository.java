package com.innoviti.emi.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.innoviti.emi.entity.master.DealerManufacturerMaster;
import com.innoviti.emi.entity.master.DealerManufacturerMasterComposite;
import com.innoviti.emi.repository.BaseRepository;

public interface DealerManufacturerMasterRepository extends BaseRepository<DealerManufacturerMaster, DealerManufacturerMasterComposite> {
  DealerManufacturerMaster findTop1ByDealerManufacturerMasterCompositeSupplierIdOrderByDealerManufacturerMasterCompositeCrtupdDateDesc(Integer supplierId);
  List<DealerManufacturerMaster> findByDealerManufacturerMasterCompositeManufacturerId(Integer manufacturerId);
  
  @Query(value="select supplierid from dealer_manufacturer_bfl UNION select supplier_id from scheme_dealer_bfl", nativeQuery=true)
  List<Integer> getSupplierIdFromDealerManfacturerAndDealerScheme();
  
  @Query(value = "SELECT DISTINCT(s.dealerManufacturerMasterComposite.manufacturerId) FROM DealerManufacturerMaster s "
      + "WHERE s.dealerManufacturerMasterComposite.supplierId=?1")
  List<Integer> findManufacturerBySupplierId(Integer supplierId);
}
