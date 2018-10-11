package com.innoviti.emi.entity.core;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.constant.CashbackType;
import com.innoviti.emi.entity.AuditColumns;

@Entity
@Table(name = "issuer_banks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssuerBank extends AuditColumns {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private IssuerBankComposite issuerBankComposite;

  @Transient
  private String innoIssuerBankCode;

  @Column(name = "issuer_bank_display_name", nullable = false, length = 50)
  private String issuerBankDisplayName;

  @Column(name = "issuer_bank_description", nullable = false, length = 50)
  private String issuerBankDesc;

  @Column(name = "issuer_bank_disclaimer", nullable = true, length = 100)
  private String issuerBankDisclaimer;

  @Column(name = "issuer_terms_conditions", columnDefinition = "LONGTEXT", nullable = true)
  private String issuerTermsConditions;

  @Enumerated(EnumType.STRING)
  @Column(name = "issuer_default_cashback_flag", nullable = true, length = 5)
  private CashbackType issuerDefaultCashbackFlag;

  @Column(name = "issuer_min_emi_amount", precision = 10, scale = 2)
  private BigDecimal issuerMinEmiAmount;

  @Column(name = "emi_bank_code", columnDefinition = "INT(5)", nullable = false)
  private Integer emiBankCode;

  @Transient
  private Date crtupdDate;

  public String getIssuerBankDisplayName() {
    return issuerBankDisplayName;
  }

  public void setIssuerBankDisplayName(String issuerBankDisplayName) {
    this.issuerBankDisplayName = issuerBankDisplayName;
  }

  public IssuerBankComposite getIssuerBankComposite() {
    return issuerBankComposite;
  }

  public void setIssuerBankComposite(IssuerBankComposite issuerBankComposite) {
    this.issuerBankComposite = issuerBankComposite;
  }

  public Integer getEmiBankCode() {
    return emiBankCode;
  }

  public void setEmiBankCode(Integer emiBankCode) {
    this.emiBankCode = emiBankCode;
  }

  public void setIssuerBankDesc(String issuerBankDesc) {
    this.issuerBankDesc = issuerBankDesc;
  }

  public String getIssuerBankDisclaimer() {
    return issuerBankDisclaimer;
  }

  public void setIssuerBankDisclaimer(String issuerBankDisclaimer) {
    this.issuerBankDisclaimer = issuerBankDisclaimer;
  }

  public String getIssuerTermsConditions() {
    return issuerTermsConditions;
  }

  public void setIssuerTermsConditions(String issuerTermsConditions) {
    this.issuerTermsConditions = issuerTermsConditions;
  }

  public CashbackType getIssuerDefaultCashbackFlag() {
    return issuerDefaultCashbackFlag;
  }

  public void setIssuerDefaultCashbackFlag(CashbackType issuerDefaultCashbackFlag) {
    this.issuerDefaultCashbackFlag = issuerDefaultCashbackFlag;
  }

  public BigDecimal getIssuerMinEmiAmount() {
    return issuerMinEmiAmount;
  }

  public void setIssuerMinEmiAmount(BigDecimal issuerMinEmiAmount) {
    this.issuerMinEmiAmount = issuerMinEmiAmount;
  }

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
    builder.append(issuerBankComposite.getCrtupdDate());
    builder.append("|");
    builder.append(issuerBankComposite.getInnoIssuerBankCode());
    builder.append("|");
    builder.append(innoIssuerBankCode);
    builder.append("|");
    builder.append(issuerBankDisplayName);
    builder.append("|");
    builder.append(issuerBankDesc);
    builder.append("|");
    builder.append(issuerBankDisclaimer);
    builder.append("|");
    builder.append(issuerTermsConditions);
    builder.append("|");
    builder.append(issuerDefaultCashbackFlag);
    builder.append("|");
    builder.append(issuerMinEmiAmount);
    builder.append("|");
    builder.append(isRecordActive());
    builder.append("|");
    builder.append(getCrtupdReason());
    builder.append("|");
    builder.append(getCrtupdStatus());
    builder.append("|");
    builder.append(getCrtupdUser());
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }

  public String getIssuerBankDesc() {
    return issuerBankDesc;
  }

}
