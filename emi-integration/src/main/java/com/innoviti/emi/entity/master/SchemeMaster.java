package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "scheme_bfl")
public class SchemeMaster implements Serializable {

  private static final long serialVersionUID = 8637510592094876474L;

  @EmbeddedId
  private SchemeMasterComposite schemeMasterComposite;

  @Column(name = "schemedesc", length = 200, nullable = false)
  private String schemeDescription;

  @Column(name = "tenure", length = 5, nullable = false)
  private String tenure;

  @Column(name = "processing_fees", length = 43, nullable = true)
  private String processingFee;

  @Column(name = "product", length = 50, nullable = false)
  private String product;

  @Column(name = "advance_emi", length = 6, nullable = false)
  private String advanceEmi;

  @Column(name = "dbd", length = 43, nullable = false)
  private String dealerSubvention;

  @Column(name = "mbd", length = 43, nullable = false)
  private String manfacturerSubvention;

  @Column(name = "intrate", precision = 5, scale = 2, nullable = true)
  private Double interestRate;

  @Temporal(TemporalType.DATE)
  @Column(name = "scheme_start_date", nullable = false)
  private Date schemeStartDate;

  @Temporal(TemporalType.DATE)
  @Column(name = "scheme_expiry_date", nullable = false)
  private Date schemeExpiryDate;

  @Column(name = "portal_description", length = 200, nullable = false)
  private String portalDescription;

  @Column(name = "maxamount", precision = 18, scale = 0, nullable = false)
  private BigDecimal maxAmount;

  @Column(name = "minamount", precision = 18, scale = 0, nullable = false)
  private BigDecimal minAmount;

  @Column(name = "gen_sch", length = 3, nullable = false)
  private String generalScheme;

  @Column(name = "spl_sch", length = 1, nullable = false)
  private String specialScheme;

  @Column(name = "dealer_mapping", length = 1, nullable = false)
  private String dealerMapping;

  @Column(name = "model_mapping", length = 1, nullable = false)
  private String modelMapping;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  public String getSchemeDescription() {
    return schemeDescription;
  }

  public void setSchemeDescription(String schemeDescription) {
    this.schemeDescription = schemeDescription;
  }

  public String getTenure() {
    return tenure;
  }

  public void setTenure(String tenure) {
    this.tenure = tenure;
  }

  public String getProcessingFee() {
    return processingFee;
  }

  public void setProcessingFee(String processingFee) {
    this.processingFee = processingFee;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public String getAdvanceEmi() {
    return advanceEmi;
  }

  public void setAdvanceEmi(String advanceEmi) {
    this.advanceEmi = advanceEmi;
  }

  public String getDealerSubvention() {
    return dealerSubvention;
  }

  public void setDealerSubvention(String dealerSubvention) {
    this.dealerSubvention = dealerSubvention;
  }

  public String getManfacturerSubvention() {
    return manfacturerSubvention;
  }

  public void setManfacturerSubvention(String manfacturerSubvention) {
    this.manfacturerSubvention = manfacturerSubvention;
  }

  public Double getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(Double interestRate) {
    this.interestRate = interestRate;
  }

  public Date getSchemeStartDate() {
    return schemeStartDate;
  }

  public void setSchemeStartDate(Date schemeStartDate) {
    this.schemeStartDate = schemeStartDate;
  }

  public Date getSchemeExpiryDate() {
    return schemeExpiryDate;
  }

  public void setSchemeExpiryDate(Date schemeExpiryDate) {
    this.schemeExpiryDate = schemeExpiryDate;
  }

  public String getPortalDescription() {
    return portalDescription;
  }

  public void setPortalDescription(String portalDescription) {
    this.portalDescription = portalDescription;
  }

  public BigDecimal getMaxAmount() {
    return maxAmount;
  }

  public void setMaxAmount(BigDecimal maxAmount) {
    this.maxAmount = maxAmount;
  }

  public BigDecimal getMinAmount() {
    return minAmount;
  }

  public void setMinAmount(BigDecimal minAmount) {
    this.minAmount = minAmount;
  }

  public String getGeneralScheme() {
    return generalScheme;
  }

  public void setGeneralScheme(String generalScheme) {
    this.generalScheme = generalScheme;
  }

  public String getSpecialScheme() {
    return specialScheme;
  }

  public void setSpecialScheme(String specialScheme) {
    this.specialScheme = specialScheme;
  }

  public String getDealerMapping() {
    return dealerMapping;
  }

  public void setDealerMapping(String dealerMapping) {
    this.dealerMapping = dealerMapping;
  }

  public String getModelMapping() {
    return modelMapping;
  }

  public void setModelMapping(String modelMapping) {
    this.modelMapping = modelMapping;
  }

  public SchemeMasterComposite getSchemeMasterComposite() {
    return schemeMasterComposite;
  }

  public void setSchemeMasterComposite(SchemeMasterComposite schemeMasterComposite) {
    this.schemeMasterComposite = schemeMasterComposite;
  }

  public boolean isRecordActive() {
    return isRecordActive;
  }

  public void setRecordActive(boolean isRecordActive) {
    this.isRecordActive = isRecordActive;
  }

  public String getCrtupdReason() {
    return crtupdReason;
  }

  public void setCrtupdReason(String crtupdReason) {
    this.crtupdReason = crtupdReason;
  }

  public char getCrtupdStatus() {
    return crtupdStatus;
  }

  public void setCrtupdStatus(char crtupdStatus) {
    this.crtupdStatus = crtupdStatus;
  }

  public String getCrtupdUser() {
    return crtupdUser;
  }

  public void setCrtupdUser(String crtupdUser) {
    this.crtupdUser = crtupdUser;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append(getSchemeMasterComposite().getSchemeId()).append("|").append(getSchemeDescription())
        .append("|").append(getTenure()).append("|").append(getProcessingFee()).append("|")
        .append(getProduct()).append("|").append(getAdvanceEmi()).append("|")
        .append(getDealerSubvention()).append("|").append(getManfacturerSubvention()).append("|")
        .append(getInterestRate()).append("|").append(getSchemeStartDate()).append("|")
        .append(getSchemeExpiryDate()).append("|").append(getPortalDescription()).append("|")
        .append(getMaxAmount()).append("|").append(getMinAmount()).append("|")
        .append(getGeneralScheme()).append("|").append(getSpecialScheme()).append("|")
        .append(getDealerMapping()).append("|").append(getModelMapping()).append("|").toString();


  }
}
