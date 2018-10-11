package com.innoviti.emi.service.master;

import java.util.List;

import com.innoviti.emi.entity.objectstore.ObjectStoreLookUp;
import com.innoviti.emi.service.CrudService;


public interface BajajEMIFileListService extends CrudService<ObjectStoreLookUp, String>{
  
  public List<ObjectStoreLookUp> getList();
  
  public List<ObjectStoreLookUp> getList(String date);
  
  public ObjectStoreLookUp getFileKey(String fileName);

}
