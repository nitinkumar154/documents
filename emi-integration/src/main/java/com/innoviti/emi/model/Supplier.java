package com.innoviti.emi.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.repository.master.DealerManufacturerMasterRepository;

@Component
public class Supplier {
  @Autowired
  private DealerManufacturerMasterRepository dealerManufacturerMasterRepository;
  
  private volatile static List<Integer> supplierIds;
  
  public List<Integer> initializeSupplierIdList(){
    if(supplierIds == null){
      synchronized (Supplier.class) {
        if(supplierIds == null){
          supplierIds = dealerManufacturerMasterRepository.getSupplierIdFromDealerManfacturerAndDealerScheme();
         
        }
      }
    }
    return supplierIds;
  }
 public synchronized Integer getSupplierId(){
   if(supplierIds == null || supplierIds.isEmpty()){
     return null;
   }
   return supplierIds.remove(supplierIds.size() - 1);
 }
 public static void clearSupplierList(){
   if(supplierIds != null){
     supplierIds.clear();
   }
   supplierIds = null;
 }
}
