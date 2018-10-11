package com.innoviti.emi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UtidBtid implements Serializable{

  private static final long serialVersionUID = 23934091663334452L;
  
  private String utid;
  private String btid;
  private String innoSchemeModelCode;
  private String onUsOffUs;
  private String flag;
  
  public String getUtid() {
    return utid;
  }
  public void setUtid(String utid) {
    this.utid = utid;
  }
  public String getBtid() {
    return btid;
  }
  public void setBtid(String btid) {
    this.btid = btid;
  }
  public String getInnoSchemeModelCode() {
    return innoSchemeModelCode;
  }
  public void setInnoSchemeModelCode(String innoSchemeModelCode) {
    this.innoSchemeModelCode = innoSchemeModelCode;
  }
  public String getOnUsOffUs() {
    return onUsOffUs;
  }
  public void setOnUsOffUs(String onUsOffUs) {
    this.onUsOffUs = onUsOffUs;
  }
  public String getFlag() {
    return flag;
  }
  public void setFlag(String flag) {
    this.flag = flag;
  }
  
}
