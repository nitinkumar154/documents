package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class SerialNoComposite implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "model_serial_number", length = 20, nullable = false)
  private String innoModelSerialNumber;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getInnoModelSerialNumber() {
    return innoModelSerialNumber;
  }

  public void setInnoModelSerialNumber(String innoModelSerialNumber) {
    this.innoModelSerialNumber = innoModelSerialNumber;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(innoModelSerialNumber);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }

}
