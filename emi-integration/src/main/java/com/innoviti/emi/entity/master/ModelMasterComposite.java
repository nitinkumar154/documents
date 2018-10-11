package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ModelMasterComposite implements Serializable {

  private static final long serialVersionUID = -8953297448292380606L;

  @Column(name = "modelid", length = 8, nullable = false)
  private Integer modelId;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public Integer getModelId() {
    return modelId;
  }

  public void setModelId(Integer modelId) {
    this.modelId = modelId;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj){
      return true;
    }
    if(obj == null || getClass() != obj.getClass()){
      return false;
    }
    ModelMasterComposite that = (ModelMasterComposite) obj;
    if(modelId != null ? !modelId.equals(that.modelId) : that.modelId != null){
      return false;
    }
    if (crtupdDate !=null?!crtupdDate.equals(that.crtupdDate) : that.crtupdDate !=null){
      return false;
    }
    return true;
  }
  @Override
  public int hashCode() {
    return Objects.hash(this.crtupdDate, this.modelId);
  }
}
