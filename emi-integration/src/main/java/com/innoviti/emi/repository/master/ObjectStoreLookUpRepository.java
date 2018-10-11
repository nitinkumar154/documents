package com.innoviti.emi.repository.master;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.innoviti.emi.entity.objectstore.ObjectStoreLookUp;
import com.innoviti.emi.repository.BaseRepository;

public interface ObjectStoreLookUpRepository extends BaseRepository<ObjectStoreLookUp, String> {
  
  @Query("select o from ObjectStoreLookUp o where o.fileName LIKE CONCAT(:fileName, '%') and o.isProcessed='N' ")
  public ObjectStoreLookUp findByFileNameAndIsProcessed(@Param("fileName") String fileName);
  
  @Modifying
  @Query("update ObjectStoreLookUp o set o.isProcessed='Y' where o.key = :fileKey")
  public int updateIsProcessedByFileKey(@Param("fileKey") String fileKey);
    
  public ObjectStoreLookUp findByKey(String key);
  
  @Query("select o from ObjectStoreLookUp o where o.fileName LIKE CONCAT(:fileName, '%') ")
  public ObjectStoreLookUp findByFileName(@Param("fileName") String fileName);


}
