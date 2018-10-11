package com.innoviti.emi.entity.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.entity.AuditColumns;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "manufacturers")
public class Manufacturer extends AuditColumns {

  private static final long serialVersionUID = 9215534492376635255L;

  @EmbeddedId
  private ManufacturerComposite manufacturerComposite;

  @Transient
  private String innoManufacturerCode;

  @Column(name = "bajaj_manufacturer_code", length = 10)
  private String bajajManufacturerCode;

  @Column(name = "manufacturer_description", length = 150, nullable = false)
  private String manufacturerDesc;

  @Column(name = "manufacturer_display_name", length = 50, nullable = false)
  private String manufacturerDisplayName;

  @Transient
  private Date crtupdDate;

  public ManufacturerComposite getManufacturerComposite() {
    return manufacturerComposite;
  }

  public void setManufacturerComposite(ManufacturerComposite manufacturerComposite) {
    this.manufacturerComposite = manufacturerComposite;
  }

  public String getBajajManufacturerCode() {
    return bajajManufacturerCode;
  }

  public void setBajajManufacturerCode(String bajajManufacturerCode) {
    this.bajajManufacturerCode = bajajManufacturerCode;
  }

  public String getManufacturerDesc() {
    return manufacturerDesc;
  }

  public void setManufacturerDesc(String manufacturerDesc) {
    this.manufacturerDesc = manufacturerDesc;
  }

  public String getManufacturerDisplayName() {
    return manufacturerDisplayName;
  }

  public void setManufacturerDisplayName(String manufacturerDisplayName) {
    this.manufacturerDisplayName = manufacturerDisplayName;
  }

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
    builder.append(manufacturerComposite.getCrtupdDate());
    builder.append("|");
    builder.append(manufacturerComposite.getInnoManufacturerCode());
    builder.append("|");
    builder.append(innoManufacturerCode);
    builder.append("|");
    builder.append(bajajManufacturerCode);
    builder.append("|");
    builder.append(manufacturerDesc);
    builder.append("|");
    builder.append(manufacturerDisplayName);
    builder.append("|");
    builder.append(isRecordActive());
    builder.append("|");
    builder.append(getCrtupdReason());
    builder.append("|");
    builder.append(getCrtupdStatus());
    builder.append("|");
    builder.append(getCrtupdUser());
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }

}
