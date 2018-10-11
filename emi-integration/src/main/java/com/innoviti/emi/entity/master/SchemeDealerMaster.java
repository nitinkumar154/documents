package com.innoviti.emi.entity.master;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "scheme_dealer_bfl")
public class SchemeDealerMaster {

  @EmbeddedId
  private SchemeDealerMasterComposite schemeDealerMasterComposite;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  public SchemeDealerMasterComposite getSchemeDealerMasterComposite() {
    return schemeDealerMasterComposite;
  }

  public void setSchemeDealerMasterComposite(
      SchemeDealerMasterComposite schemeDealerMasterComposite) {
    this.schemeDealerMasterComposite = schemeDealerMasterComposite;
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
    builder.append(schemeDealerMasterComposite.getSchemeId());
    builder.append("|");
    builder.append(schemeDealerMasterComposite.getSupplierId());
    return builder.toString();
  }


}
