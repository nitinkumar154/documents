package com.innoviti.emi.entity.master;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "manufacturer_bfl")
public class ManufacturerMaster {

  @EmbeddedId
  private ManufacturerMasterComposite manufacturerMasterComposite;

  @Column(name = "manufacture_desc", length = 150, nullable = false)
  private String manufacturerDesc;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  public String getManufacturerDesc() {
    return manufacturerDesc;
  }

  public void setManufacturerDesc(String manufacturerDesc) {
    this.manufacturerDesc = manufacturerDesc;
  }

  public ManufacturerMasterComposite getManufacturerMasterComposite() {
    return manufacturerMasterComposite;
  }

  public void setManufacturerMasterComposite(
      ManufacturerMasterComposite manufacturerMasterComposite) {
    this.manufacturerMasterComposite = manufacturerMasterComposite;
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
    builder.append(manufacturerMasterComposite.getManufacturerId());
    builder.append("|");
    builder.append(manufacturerDesc);
    return builder.toString();
  }
}
