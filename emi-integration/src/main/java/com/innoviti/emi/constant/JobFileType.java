package com.innoviti.emi.constant;

public enum JobFileType {
  SCHEME_MASTER("SCHM"), 
  DEALER_MASTER("DLRM"), 
  CITY_MASTER("CITM"),
  STATE_MASTER("STAM"), 
  BRANCH_MASTER("BRNM"), 
  MANUFACTURER_MASTER("MNFM"),
  ASSET_CATEGORY_MASTER("ASTM"), 
  DEALER_MANUFACTURER_MASTER("DMFM"),
  MODEL_MASTER("MDLM"), 
  SCHEME_MODEL_MASTER("SHMM"), 
  DEALER_PRODUCT_MASTER("DPRM"),
  SCHEME_DEALER_MASTER("SHDM"), 
  SCHEME_BRANCH_MASTER("SHBM"), 
  MODEL_PRODUCT_MASTER("MDPM"),
  SCHEME_MODEL_TEMINAL_MAPPING("SMTM");
  
  private String fileType;
  
  private JobFileType(String filetype){
    this.fileType = filetype;
  }

  public String getFileType() {
    return fileType;
  }
  
}
