package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ManufacturerMasterComposite implements Serializable {

  private static final long serialVersionUID = 7462919440402351696L;

  @Column(name = "manufacture_id", precision = 8, scale = 0, nullable = false)
  private Integer manufacturerId;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

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
