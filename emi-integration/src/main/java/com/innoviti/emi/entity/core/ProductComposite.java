package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class ProductComposite implements Serializable{
  
  private static final long serialVersionUID = 5106729063630413643L;

  @Column(name = "product_code", length = 20, nullable = false)
  private String productCode;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String innoProductTypeCode) {
    this.productCode = innoProductTypeCode;
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
    builder.append(productCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }
  
  

}
