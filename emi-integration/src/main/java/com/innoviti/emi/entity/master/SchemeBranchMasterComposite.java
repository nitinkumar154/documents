package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SchemeBranchMasterComposite implements Serializable {

  private static final long serialVersionUID = -6350740206830339028L;

  @Column(name = "schemeid", length = 8, nullable = false)
  private Integer schemeId;

  @Column(columnDefinition = "INT(8) NOT NULL COMMENT ' Refers to DimBranch'", name = "branchid",nullable = false)
  private Integer branchId;

  @Column(name = "record_update_date", columnDefinition = "datetime", nullable = false)
  private Date crtupdDate;

  public Integer getSchemeId() {
    return schemeId;
  }

  public void setSchemeId(Integer schemeId) {
    this.schemeId = schemeId;
  }

  public Integer getBranchId() {
    return branchId;
  }

  public void setBranchId(Integer branchId) {
    this.branchId = branchId;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
