package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TenureComposite implements Serializable {

  private static final long serialVersionUID = 2670167459776417665L;

  @Column(name = "emi_tenure_code", length = 5, nullable = false)
  private String innoTenureCode;

  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getInnoTenureCode() {
    return innoTenureCode;
  }

  public void setInnoTenureCode(String innoTenureCode) {
    this.innoTenureCode = innoTenureCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtuptDate) {
    this.crtupdDate = crtuptDate;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(innoTenureCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }
  
}
