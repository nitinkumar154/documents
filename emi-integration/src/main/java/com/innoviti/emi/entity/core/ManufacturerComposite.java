package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class ManufacturerComposite implements Serializable {

  private static final long serialVersionUID = -4250962378614406929L;

  @Column(name = "manufacturer_code", length = 20, nullable = false)
  private String innoManufacturerCode;

  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getInnoManufacturerCode() {
    return innoManufacturerCode;
  }

  public void setInnoManufacturerCode(String innoManufacturerCode) {
    this.innoManufacturerCode = innoManufacturerCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtuptDate) {
    this.crtupdDate = crtuptDate;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(innoManufacturerCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }

}
