package com.innoviti.emi.entity.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "state_bfl")
public class StateMaster implements Serializable {

  private static final long serialVersionUID = 7834913676930393154L;

  @EmbeddedId
  private StateMasterComposite stateMasterComposite;

  @NotBlank
  @NotEmpty
  @Column(name = "statedesc", length = 105, nullable = false)
  private String stateDescription;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  public String getStateDescription() {
    return stateDescription;
  }

  public void setStateDescription(String stateDescription) {
    this.stateDescription = stateDescription;
  }

  public StateMasterComposite getStateMasterComposite() {
    return stateMasterComposite;
  }

  public void setStateMasterComposite(StateMasterComposite stateMasterComposite) {
    this.stateMasterComposite = stateMasterComposite;
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
    builder.append(stateMasterComposite.getStateId());
    builder.append("|");
    builder.append(stateDescription);
    return builder.toString();
  }

}
