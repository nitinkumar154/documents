package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class ModelProductMasterComposite implements Serializable {

  private static final long serialVersionUID = 1582177792485239726L;

  @Column(name = "modelid", length = 8, nullable = false)
  private Integer modelId;

  @Column(name = "code", length = 8, nullable = false)
  private String code;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public Integer getModelId() {
    return modelId;
  }

  public void setModelId(Integer modelId) {
    this.modelId = modelId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
