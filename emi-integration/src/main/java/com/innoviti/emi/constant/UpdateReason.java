package com.innoviti.emi.constant;

public enum UpdateReason {

  BAJAJ_BATCH_UPDATE("Bajaj_Batch_Update"), CONFIGURATION("Configuration");

  private String recordUpdateReason;

  private UpdateReason(String recordUpdateReason) {
    this.recordUpdateReason = recordUpdateReason;
  }

  public String getRecordUpdateReason() {
    return recordUpdateReason;
  }
}
