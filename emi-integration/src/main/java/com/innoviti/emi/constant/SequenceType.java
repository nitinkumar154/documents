package com.innoviti.emi.constant;

public enum SequenceType {
  CATEGORY("Category", "CAT"),
  ISSUER_BANK("IssuerBank","ISB"),
  MANUFACTURER("Manufacturer", "MAN"),
  MODEL("Model", "MOD"),
  SCHEME("Scheme", "SCH"),
  SCHEME_MODEL("SchemeModel", "SHM"),
  SCHEME_MODEL_TERMINAL("SchemeModelTerminal","SMT"),
  SERIAL_NO("SerialNo", "SRN"),
  TENURE("Tenure", "TR"),
  PRODUCT("Product","PRD");    
  
  private String sequenceName;
  private String sequenceIdentifier;
  
  private SequenceType(String sequenceName, String sequenceIdentifier){
    this.sequenceIdentifier = sequenceIdentifier;
    this.sequenceName = sequenceName;
  }

  public String getSequenceName() {
    return sequenceName;
  }

  public String getSequenceIdentifier() {
    return sequenceIdentifier;
  }
  
  
}
