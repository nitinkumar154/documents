package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssetCategoryMasterComposite implements Serializable {

  private static final long serialVersionUID = -1234577222726805993L;

  @Column(name = "catgid", length = 8, nullable = false)
  private Integer id;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
