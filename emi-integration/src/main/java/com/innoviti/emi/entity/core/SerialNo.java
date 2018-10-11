package com.innoviti.emi.entity.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.entity.AuditColumns;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "model_serial_numbers")
public class SerialNo extends AuditColumns {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private SerialNoComposite serialNoComposite;

  @Column(name = "manufacturer_model_serial_number", nullable = false, length = 50)
  private String manufacturerSerialNumber;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({
      @JoinColumn(name = "model_code", referencedColumnName = "model_code"),
      @JoinColumn(referencedColumnName = "record_update_timestamp")})
  private Model model;
  
  @Transient
  private String innoModelCode;

  @Column(name = "is_emi_used", nullable = false)
  private Boolean emiStatus;

  @Transient
  private String innoModelSerialNumber;

  @Transient
  private Date crtupdDate;

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public String getInnoModelCode() {
    return innoModelCode;
  }

  public void setInnoModelCode(String innoModelCode) {
    this.innoModelCode = innoModelCode;
  }

  public String getManufacturerSerialNumber() {
    return manufacturerSerialNumber;
  }

  public void setManufacturerSerialNumber(String manufacturerSerialNumber) {
    this.manufacturerSerialNumber = manufacturerSerialNumber;
  }

  public Boolean getEmiStatus() {
    return emiStatus;
  }

  public void setEmiStatus(Boolean emiStatus) {
    this.emiStatus = emiStatus;
  }

  public SerialNoComposite getSerialNoComposite() {
    return serialNoComposite;
  }

  public void setSerialNoComposite(SerialNoComposite serialNoComposite) {
    this.serialNoComposite = serialNoComposite;
  }

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

}
