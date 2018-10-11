package com.innoviti.emi.entity.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.entity.AuditColumns;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "emi_tenures")
public class Tenure extends  AuditColumns {

  private static final long serialVersionUID = -8941727360454380576L;

  @EmbeddedId
  private TenureComposite tenureComposite;

  @Transient
  private String innoTenureCode;

  @Column(name = "emi_tenure_months", length = 4)
  private String tenureMonth;

  @Column(name = "emi_tenure_display_name", length = 50, nullable = false)
  private String tenureDisplayName;

  @Transient
  private Date crtupdDate;

  public TenureComposite getTenureComposite() {
    return tenureComposite;
  }

  public void setTenureComposite(TenureComposite tenureComposite) {
    this.tenureComposite = tenureComposite;
  }

  public String getTenureMonth() {
    return tenureMonth;
  }

  public void setTenureMonth(String tenureMonth) {
    this.tenureMonth = tenureMonth;
  }

  public String getTenureDisplayName() {
    return tenureDisplayName;
  }

  public void setTenureDisplayName(String tenureDisplayName) {
    this.tenureDisplayName = tenureDisplayName;
  }

  

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
    builder.append(tenureComposite.toString());
    builder.append("|");
    builder.append(innoTenureCode);
    builder.append("|");
    builder.append(tenureMonth);
    builder.append("|");
    builder.append(tenureDisplayName);
    return builder.toString();
  }

}
