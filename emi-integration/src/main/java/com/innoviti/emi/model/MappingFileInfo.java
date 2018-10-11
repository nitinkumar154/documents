package com.innoviti.emi.model;

import java.io.Serializable;

public class MappingFileInfo implements Serializable{

  private static final long serialVersionUID = 8763527550866083042L;

  private String fileName;
  private String creationTime;
  
  public String getFileName() {
    return fileName;
  }
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  public String getCreationTime() {
    return creationTime;
  }
  public void setCreationTime(String creationTime) {
    this.creationTime = creationTime;
  }
  
  
}
