package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DealerMasterComposite implements Serializable {

  private static final long serialVersionUID = -3960736343190164985L;

  @Column(name = "supplierid", length = 8, nullable = false)
  private Integer supplierId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

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
