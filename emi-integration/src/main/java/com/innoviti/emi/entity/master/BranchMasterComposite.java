package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BranchMasterComposite implements Serializable {

  private static final long serialVersionUID = -1982153015089293095L;

  @Column(name = "branch_code", length = 20, nullable = false)
  private String branchCode;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public String getBranchCode() {
    return branchCode;
  }

  public void setBranchCode(String branchCode) {
    this.branchCode = branchCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
