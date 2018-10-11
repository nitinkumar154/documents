package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CityMasterComposite implements Serializable {

  private static final long serialVersionUID = -4859469277937900189L;

  @Column(name = "cityid", length = 20, nullable = false)
  private String cityId;

  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }
}
