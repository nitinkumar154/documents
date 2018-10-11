package com.innoviti.emi.entity.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.constant.OnUsOffUsStatus;
import com.innoviti.emi.constant.UTIDUpdateStatus;
import com.innoviti.emi.entity.AuditColumns;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "issuer_scheme_model_terminal")
public class SchemeModelTerminal extends AuditColumns{

  private static final long serialVersionUID = 5182864283420498830L;

  @EmbeddedId
  private SchemeModelTerminalComposite schemeModelTerminalComposite;

  @Column(name = "dealer_id", length = 10)
  private String dealerId;

  @Column(name = "issuer_custom_field", columnDefinition = "varchar(10) NOT NULL ")
  private String issuerCustomField;

  @Enumerated(EnumType.STRING)
  @Column(name = "issuer_scheme_terminal_sync_status", nullable = false)
  private UTIDUpdateStatus issuerSchemeTerminalSyncStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "issuer_scheme_onus_offus")
  private OnUsOffUsStatus onUsOffUs;

  @Column(name = "bajaj_product_type_code", nullable = true, length = 10)
  private String bajajProductTypeCode;

  @Transient
  private String utid;

  @Transient
  // @Column(name = "issuer_scheme_model_code", insertable = false, updatable = false)
  private String innoSchemeModelCode;

  @Transient
  private Date crtupdDate;

  public SchemeModelTerminalComposite getSchemeModelTerminalComposite() {
    return schemeModelTerminalComposite;
  }

  public void setSchemeModelTerminalComposite(
      SchemeModelTerminalComposite schemeModelTerminalComposite) {
    this.schemeModelTerminalComposite = schemeModelTerminalComposite;
  }

  public String getDealerId() {
    return dealerId;
  }

  public void setDealerId(String dealerId) {
    this.dealerId = dealerId;
  }

  public OnUsOffUsStatus getOnUsOffUs() {
    return onUsOffUs;
  }

  public void setOnUsOffUs(OnUsOffUsStatus onUsOffUs) {
    this.onUsOffUs = onUsOffUs;
  }

  public String getUtid() {
    return utid;
  }

  public void setUtid(String utId) {
    this.utid = utId;
  }

  public String getInnoSchemeModelCode() {
    return innoSchemeModelCode;
  }

  public void setInnoSchemeModelCode(String innoSchemeModelCode) {
    this.innoSchemeModelCode = innoSchemeModelCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public String getBajajProductTypeCode() {
    return bajajProductTypeCode;
  }

  public void setBajajProductTypeCode(String bajajProductTypeCode) {
    this.bajajProductTypeCode = bajajProductTypeCode;
  }

  public void setCrtupdDate(Date crtuptDate) {
    this.crtupdDate = crtuptDate;
  }

  public String getIssuerCustomField() {
    return issuerCustomField;
  }

  public void setIssuerCustomField(String issuerCustomField) {
    this.issuerCustomField = issuerCustomField;
  }

  public UTIDUpdateStatus getIssuerSchemeTerminalSyncStatus() {
    return issuerSchemeTerminalSyncStatus;
  }

  public void setIssuerSchemeTerminalSyncStatus(UTIDUpdateStatus issuerSchemeTerminalSyncStatus) {
    this.issuerSchemeTerminalSyncStatus = issuerSchemeTerminalSyncStatus;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(schemeModelTerminalComposite.toString());
    builder.append("|");
    builder.append(dealerId);
    builder.append("|");
    builder.append(issuerCustomField);
    builder.append("|");
    builder.append(issuerSchemeTerminalSyncStatus);
    builder.append("|");
    builder.append(onUsOffUs);
    builder.append("|");
    builder.append(bajajProductTypeCode);
    return builder.toString();
  }
  
  public void copyAuditDetailsInto(SchemeModelTerminal copy) {
	  copy.setRecordActive(isRecordActive());
	  copy.setCrtupdReason(getCrtupdReason());
	  copy.setCrtupdStatus(getCrtupdStatus());
	  copy.setCrtupdUser(getCrtupdUser());
  }
  
  public void copyNonAuditDetailsInto(SchemeModelTerminal copy) {
	  copy.bajajProductTypeCode = bajajProductTypeCode;
	  copy.onUsOffUs = onUsOffUs;
	  copy.issuerSchemeTerminalSyncStatus = issuerSchemeTerminalSyncStatus;
	  copy.issuerCustomField = issuerCustomField;
	  copy.dealerId = dealerId;
  }

}
