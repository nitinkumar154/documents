package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SchemeDealerMasterComposite implements Serializable {

  private static final long serialVersionUID = 1844931968982566669L;

  @Column(name = "scheme_id", length = 8, nullable = false)
  private Integer schemeId;

  @Column(name = "supplier_id", length = 8, nullable = false)
  private Integer supplierId;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public Integer getSchemeId() {
    return schemeId;
  }

  public void setSchemeId(Integer schemeId) {
    this.schemeId = schemeId;
  }

  public Integer getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Integer supplierId) {
    this.supplierId = supplierId;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
