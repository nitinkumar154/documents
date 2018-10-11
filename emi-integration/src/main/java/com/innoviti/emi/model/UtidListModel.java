package com.innoviti.emi.model;

import java.io.Serializable;
import java.util.Set;

public class UtidListModel implements Serializable{

  private static final long serialVersionUID = -1423943855816859548L;
  
  private String dealerId;
  private String statusMsg;
  private Set<UtidBtid> utidBtidList;
 
  
  public String getDealerId() {
    return dealerId;
  }
  public void setDealerId(String dealerId) {
    this.dealerId = dealerId;
  }
  public String getStatusMsg() {
    return statusMsg;
  }
  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }
  public Set<UtidBtid> getUtidBtidList() {
    return utidBtidList;
  }
  public void setUtidBtidList(Set<UtidBtid> utidBtidList) {
    this.utidBtidList = utidBtidList;
  }
}
