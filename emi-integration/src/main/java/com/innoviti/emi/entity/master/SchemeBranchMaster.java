package com.innoviti.emi.entity.master;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "scheme_branch_bfl")
public class SchemeBranchMaster {

  @EmbeddedId
  private SchemeBranchMasterComposite schemeBranchMasterComposite;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", columnDefinition = "varchar(60)", nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", columnDefinition = "char(1)", nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", columnDefinition = "varchar(32)", nullable = false)
  private String crtupdUser;
  
  public SchemeBranchMasterComposite getSchemeBranchMasterComposite() {
    return schemeBranchMasterComposite;
  }

  public void setSchemeBranchMasterComposite(
      SchemeBranchMasterComposite schemeBranchMasterComposite) {
    this.schemeBranchMasterComposite = schemeBranchMasterComposite;
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
    builder.append(schemeBranchMasterComposite.getSchemeId());
    builder.append("|");
    builder.append(schemeBranchMasterComposite.getBranchId());
    return builder.toString();
  }



}
