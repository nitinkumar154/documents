package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SchemeModelComposite implements Serializable {

  private static final long serialVersionUID = -5652167154754048776L;

  @Column(name = "issuer_scheme_model_code", nullable = false, length = 85)
  private String innoSchemeModelCode;

  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getInnoSchemeModelCode() {
    return innoSchemeModelCode;
  }

  public void setInnoSchemeModelCode(String innoSchemeModelCode) {
    this.innoSchemeModelCode = innoSchemeModelCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDt) {
    this.crtupdDate = crtupdDt;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(innoSchemeModelCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }

}
