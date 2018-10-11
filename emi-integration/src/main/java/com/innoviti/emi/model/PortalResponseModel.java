package com.innoviti.emi.model;

import java.io.Serializable;

public class PortalResponseModel implements Serializable{
  private static final long serialVersionUID = 8535750687609706235L;
  
  private String responseCode;
  private String responseMsg;
  
  public String getResponseCode() {
    return responseCode;
  }
  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }
  public String getResponseMsg() {
    return responseMsg;
  }
  public void setResponseMsg(String responseMsg) {
    this.responseMsg = responseMsg;
  }
}
