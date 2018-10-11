package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class CategoryComposite implements Serializable {

  private static final long serialVersionUID = -7293646657213369534L;

  @Column(name = "category_code", length = 20, nullable = false)
  private String innoCategoryCode;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getInnoCategoryCode() {
    return innoCategoryCode;
  }

  public void setInnoCategoryCode(String innoCategoryCode) {
    this.innoCategoryCode = innoCategoryCode;
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
    builder.append(innoCategoryCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }
}
