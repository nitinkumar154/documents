package com.innoviti.emi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AuditColumns implements Serializable{

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", columnDefinition = "varchar(60)", nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", columnDefinition = "char(1)", nullable = false)
  private String crtupdStatus;

  @Column(name = "record_update_user", columnDefinition = "varchar(32)", nullable = false)
  private String crtupdUser;

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

  public String getCrtupdStatus() {
    return crtupdStatus;
  }

  public void setCrtupdStatus(String crtupdStatus) {
    this.crtupdStatus = crtupdStatus;
  }

  public String getCrtupdUser() {
    return crtupdUser;
  }

  public void setCrtupdUser(String crtupdUser) {
    this.crtupdUser = crtupdUser;
  }

}
