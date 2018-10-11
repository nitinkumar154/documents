package com.innoviti.emi.entity.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "city_bfl")
public class CityMaster implements Serializable {

  private static final long serialVersionUID = 6224967823076583960L;

  @EmbeddedId
  private CityMasterComposite cityMasterComposite;

  @NotEmpty
  @NotBlank
  @Column(name = "cityname", length = 50, nullable = false)
  private String cityName;

  @Column(name = "stateid", length = 8, nullable = false)
  private Integer stateId;

  @Column(name = "citytype", length = 105, nullable = true)
  private String cityType;

  @Column(name = "risk_plloc", length = 105, nullable = true)
  private String riskPlloc;

  @Column(name = "citytypeid", length = 4, nullable = true)
  private Integer cityTypeId;

  @Column(name = "city_category", length = 3, nullable = true)
  private String cityCategory;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public Integer getStateId() {
    return stateId;
  }

  public void setStateId(Integer stateId) {
    this.stateId = stateId;
  }

  public String getCityType() {
    return cityType;
  }

  public void setCityType(String cityType) {
    this.cityType = cityType;
  }

  public String getRiskPlloc() {
    return riskPlloc;
  }

  public void setRiskPlloc(String riskPlloc) {
    this.riskPlloc = riskPlloc;
  }

  public Integer getCityTypeId() {
    return cityTypeId;
  }

  public void setCityTypeId(Integer cityTypeId) {
    this.cityTypeId = cityTypeId;
  }

  public String getCityCategory() {
    return cityCategory;
  }

  public void setCityCategory(String cityCategory) {
    this.cityCategory = cityCategory;
  }

  public CityMasterComposite getCityMasterComposite() {
    return cityMasterComposite;
  }

  public void setCityMasterComposite(CityMasterComposite cityMasterComposite) {
    this.cityMasterComposite = cityMasterComposite;
  }

  public boolean isRecordActive() {
    return isRecordActive;
  }

  public void setRecordActive(boolean isRecordActive) {
    this.isRecordActive = isRecordActive;
  }

  public String getCrtupdReason() {
    return crtupdReason;
  }

  public void setCrtupdReason(String crtupdReason) {
    this.crtupdReason = crtupdReason;
  }

  public char getCrtupdStatus() {
    return crtupdStatus;
  }

  public void setCrtupdStatus(char crtupdStatus) {
    this.crtupdStatus = crtupdStatus;
  }

  public String getCrtupdUser() {
    return crtupdUser;
  }

  public void setCrtupdUser(String crtupdUser) {
    this.crtupdUser = crtupdUser;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(cityMasterComposite.getCityId());
    builder.append("|");
    builder.append(cityName);
    builder.append("|");
    builder.append(stateId);
    builder.append("|");
    builder.append(cityType);
    builder.append("|");
    builder.append(riskPlloc);
    builder.append("|");
    builder.append(cityTypeId);
    builder.append("|");
    builder.append(cityCategory);
    return builder.toString();
  }
}
