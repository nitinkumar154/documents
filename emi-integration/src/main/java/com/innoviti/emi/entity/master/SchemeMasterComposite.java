package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SchemeMasterComposite implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "schemeid", length = 8, nullable = false)
  private Integer schemeId;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public Integer getSchemeId() {
    return schemeId;
  }

  public void setSchemeId(Integer schemeId) {
    this.schemeId = schemeId;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
