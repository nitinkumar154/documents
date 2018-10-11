package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StateMasterComposite implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "stateid", length = 8, nullable = false)
  private Integer stateId;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public Integer getStateId() {
    return stateId;
  }

  public void setStateId(Integer stateId) {
    this.stateId = stateId;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
