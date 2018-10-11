package com.innoviti.emi.entity.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "asset_category_bfl")
public class AssetCategoryMaster implements Serializable {

  private static final long serialVersionUID = -2248844643197213696L;

  @EmbeddedId
  private AssetCategoryMasterComposite assetCategoryMasterComposite;

  @NotBlank
  @NotEmpty
  @Column(name = "catgdesc", length = 35, nullable = false)
  private String description;

  @NotBlank
  @NotEmpty
  @Column(name = "cibil_check", length = 1, nullable = false)
  private String cibilCheck;

  @NotBlank
  @NotEmpty
  @Column(name = "risk_classification", length = 1, nullable = false)
  private String riskClassification;

  @NotBlank
  @NotEmpty
  @Column(name = "digital_flag", length = 1, nullable = false)
  private String digitalFlag;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCibilCheck() {
    return cibilCheck;
  }

  public void setCibilCheck(String cibilCheck) {
    this.cibilCheck = cibilCheck;
  }

  public String getRiskClassification() {
    return riskClassification;
  }

  public void setRiskClassification(String riskClassification) {
    this.riskClassification = riskClassification;
  }

  public String getDigitalFlag() {
    return digitalFlag;
  }

  public void setDigitalFlag(String digitalFlag) {
    this.digitalFlag = digitalFlag;
  }

  public AssetCategoryMasterComposite getAssetCategoryMasterComposite() {
    return assetCategoryMasterComposite;
  }

  public void setAssetCategoryMasterComposite(
      AssetCategoryMasterComposite assetCategoryMasterComposite) {
    this.assetCategoryMasterComposite = assetCategoryMasterComposite;
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
    StringBuilder builder = new StringBuilder();
    builder.append(assetCategoryMasterComposite.getId());
    builder.append("|");
    builder.append(description);
    builder.append("|");
    builder.append(cibilCheck);
    builder.append("|");
    builder.append(riskClassification);
    builder.append("|");
    builder.append(digitalFlag);
    return builder.toString();
  }

}
