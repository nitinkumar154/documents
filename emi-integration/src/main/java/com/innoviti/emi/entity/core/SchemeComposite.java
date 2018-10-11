package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class SchemeComposite implements Serializable {

  private static final long serialVersionUID = 3622947135932503746L;

  @Column(name = "issuer_scheme_code", length = 20, nullable = false)
  private String innoIssuerSchemeCode;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getInnoIssuerSchemeCode() {
    return innoIssuerSchemeCode;
  }

  public void setInnoIssuerSchemeCode(String innoIssuerSchemeCode) {
    this.innoIssuerSchemeCode = innoIssuerSchemeCode;
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
    SchemeComposite that = (SchemeComposite) obj;
    if(innoIssuerSchemeCode != null ? !innoIssuerSchemeCode.equals(that.innoIssuerSchemeCode) : that.innoIssuerSchemeCode != null){
      return false;
    }
    if (crtupdDate !=null?crtupdDate.compareTo(that.crtupdDate) != 0 : that.crtupdDate !=null){
      return false;
    }
    return true;
  }
  @Override
  public int hashCode() {
    return Objects.hash(this.innoIssuerSchemeCode, this.crtupdDate);
  }
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(innoIssuerSchemeCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }
  
}
