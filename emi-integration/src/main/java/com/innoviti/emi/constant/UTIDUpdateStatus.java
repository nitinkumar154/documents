package com.innoviti.emi.constant;

public enum UTIDUpdateStatus {
  NOT_SENT("NOT_SENT"), SENT("SENT"), ACKD("ACKD");
  
  private String status;
  
  private UTIDUpdateStatus (String status) {
    this.status = status;
  }
  
  public String getStatus () {
    return status;
  }
}
