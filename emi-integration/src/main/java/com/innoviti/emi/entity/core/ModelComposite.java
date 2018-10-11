package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class ModelComposite implements Serializable{
  
  
  private static final long serialVersionUID = 6843011712158533271L;

  @Column(name = "model_code", length = 20, nullable = false)
  private String innoModelCode;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getInnoModelCode() {
    return innoModelCode;
  }

  public void setInnoModelCode(String innoModelCode) {
    this.innoModelCode = innoModelCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtuptDate) {
    this.crtupdDate = crtuptDate;
  }
  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if(obj == null || getClass() != obj.getClass()){
      return false;
    }
    ModelComposite that = (ModelComposite) obj;
    if(innoModelCode != null ? !innoModelCode.equals(that.innoModelCode) : that.innoModelCode != null){
      return false;
    }
    if (crtupdDate !=null?crtupdDate.compareTo(that.crtupdDate) != 0 : that.crtupdDate !=null){
      return false;
    }
    return true;
  }
  @Override
  public int hashCode() {
    return Objects.hash(this.innoModelCode, this.crtupdDate);
  }
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(innoModelCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }  
  

}
