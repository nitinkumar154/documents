package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SchemeModelMasterComposite implements Serializable {

  private static final long serialVersionUID = -103300480641400711L;

  @Column(name = "schemeid", length = 8, nullable = false)
  private Integer schemeId;

  @Column(name = "modelid", length = 8, nullable = false)
  private String modelId;

  @Column(name = "manufacturerid", length = 8, nullable = false)
  private String manufacturer;

  @Column(name = "categoryid", length = 20, nullable = false)
  private String categoryId;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public Integer getSchemeId() {
    return schemeId;
  }

  public void setSchemeId(Integer schemeId) {
    this.schemeId = schemeId;
  }

  public String getModelId() {
    return modelId;
  }

  public void setModelId(String modelId) {
    this.modelId = modelId;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
