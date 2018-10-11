package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class DealerManufacturerMasterComposite implements Serializable {

  private static final long serialVersionUID = -419426851721636800L;

  @Column(name = "supplierid", length = 8, nullable = false)
  private Integer supplierId;

  @Column(name = "manufacturer_id", length = 8, nullable = false)
  private Integer manufacturerId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_date", columnDefinition = "datetime", nullable = false)
  private Date crtupdDate;

  public Integer getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Integer supplierId) {
    this.supplierId = supplierId;
  }

  public Integer getManufacturerId() {
    return manufacturerId;
  }

  public void setManufacturerId(Integer manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
