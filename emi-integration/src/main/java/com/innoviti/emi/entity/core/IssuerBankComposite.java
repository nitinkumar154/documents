package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IssuerBankComposite implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "issuer_bank_code", length = 20, nullable = false)
  private String innoIssuerBankCode;

  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getInnoIssuerBankCode() {
    return innoIssuerBankCode;
  }

  public void setInnoIssuerBankCode(String innoIssuerBankCode) {
    this.innoIssuerBankCode = innoIssuerBankCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(innoIssuerBankCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }


}
