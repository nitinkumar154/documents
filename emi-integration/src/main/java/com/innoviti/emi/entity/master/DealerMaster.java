package com.innoviti.emi.entity.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "dealer_bfl")
public class DealerMaster implements Serializable {

  private static final long serialVersionUID = -5733955380212182352L;

  @EmbeddedId
  private DealerMasterComposite dealerMasterComposite;

  @NotBlank
  @NotEmpty
  @Column(name = "supplierdesc", length = 100, nullable = false)
  private String supplierDesc;

  @Pattern(regexp = "[D,S]")
  @Column(name = "supplier_dealer_flag", length = 1, nullable = true)
  private String supplierDealerFlag;

  @Column(name = "dealer_grp_id", length = 8, nullable = true)
  private Integer dealerGroupId;

  @Column(name = "dealer_grp_desc", length = 105, nullable = true)
  private String dealerGroupDesc;

  @Column(name = "pan", length = 30, nullable = true)
  private String pan;

  @Column(name = "contact_person", length = 100, nullable = true)
  private String contactPerson;

  @Column(name = "address1", length = 120, nullable = true)
  private String address1;

  @Column(name = "address2", length = 120, nullable = true)
  private String address2;

  @Column(name = "address3", length = 120, nullable = true)
  private String address3;

  @Column(name = "address4", length = 120, nullable = true)
  private String address4;

  @Column(name = "stdisd", length = 48, nullable = true)
  private String stdIsd;

  @Column(name = "phone1", length = 48, nullable = true)
  private String phone1;

  @Column(name = "mobile", length = 105, nullable = true)
  private String mobile;

  @NotBlank
  @NotEmpty
  @Column(name = "city", length = 105, nullable = true)
  private String city;

  @Column(name = "dealer_email", length = 105, nullable = true)
  private String dealerEmail;

  @Column(name = "classification", length = 30, nullable = true)
  private String classification;

  @Column(name = "loyality_prog_applicable", length = 3, nullable = true)
  private String loyalityProgApplicable;

  @Column(name = "supplier_type", length = 5, nullable = true)
  private String supplierType;

  @Column(name = "supplier_branch", length = 10)
  private String supplierBranch;

  @Column(name = "asset_catg_id", length = 8, nullable = false)
  private Integer assetCatgId;

  @Column(name = "process_type", length = 20, nullable = false)
  private String processType;

  @Pattern(regexp = "(Y|N)")
  @Column(name = "preferred_limit_flag", length = 1, nullable = false)
  private String preferredLimitFlag;

  @Column(name = "store_id", length = 10, nullable = false)
  private String storeId;

  @Column(name = "co_brand_limit_flag", length = 1, nullable = false)
  private String coBrandLimitFlag;

  @Column(name = "co_brand_card_code", length = 2, nullable = false)
  private String coBrandCardCode;

  @Column(name = "serving_cities", columnDefinition = "text not null")
  private String servingCities;

  @NotBlank
  @NotEmpty
  @Column(name = "state", length = 20, nullable = false)
  private String state;

  @Column(name = "zip_code", length = 6, nullable = true)
  private Integer zipCode;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  public String getSupplierDesc() {
    return supplierDesc;
  }

  public void setSupplierDesc(String supplierDesc) {
    this.supplierDesc = supplierDesc;
  }

  public String getSupplierDealerFlag() {
    return supplierDealerFlag;
  }

  public void setSupplierDealerFlag(String supplierDealerFlag) {
    this.supplierDealerFlag = supplierDealerFlag;
  }

  public Integer getDealerGroupId() {
    return dealerGroupId;
  }

  public void setDealerGroupId(Integer dealerGroupId) {
    this.dealerGroupId = dealerGroupId;
  }

  public String getDealerGroupDesc() {
    return dealerGroupDesc;
  }

  public void setDealerGroupDesc(String dealerGroupDesc) {
    this.dealerGroupDesc = dealerGroupDesc;
  }

  public String getPan() {
    return pan;
  }

  public void setPan(String pan) {
    this.pan = pan;
  }

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getAddress3() {
    return address3;
  }

  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  public String getAddress4() {
    return address4;
  }

  public void setAddress4(String address4) {
    this.address4 = address4;
  }

  public String getStdIsd() {
    return stdIsd;
  }

  public void setStdIsd(String stdIsd) {
    this.stdIsd = stdIsd;
  }

  public String getPhone1() {
    return phone1;
  }

  public void setPhone1(String phone1) {
    this.phone1 = phone1;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getDealerEmail() {
    return dealerEmail;
  }

  public void setDealerEmail(String dealerEmail) {
    this.dealerEmail = dealerEmail;
  }

  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }

  public String getLoyalityProgApplicable() {
    return loyalityProgApplicable;
  }

  public void setLoyalityProgApplicable(String loyalityProgApplicable) {
    this.loyalityProgApplicable = loyalityProgApplicable;
  }

  public String getSupplierType() {
    return supplierType;
  }

  public void setSupplierType(String supplierType) {
    this.supplierType = supplierType;
  }

  public String getSupplierBranch() {
    return supplierBranch;
  }

  public void setSupplierBranch(String supplierBranch) {
    this.supplierBranch = supplierBranch;
  }

  public Integer getAssetCatgId() {
    return assetCatgId;
  }

  public void setAssetCatgId(Integer assetCatgId) {
    this.assetCatgId = assetCatgId;
  }

  public String getProcessType() {
    return processType;
  }

  public void setProcessType(String processType) {
    this.processType = processType;
  }

  public String getPreferredLimitFlag() {
    return preferredLimitFlag;
  }

  public void setPreferredLimitFlag(String preferredLimitFlag) {
    this.preferredLimitFlag = preferredLimitFlag;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public String getCoBrandLimitFlag() {
    return coBrandLimitFlag;
  }

  public void setCoBrandLimitFlag(String coBrandLimitFlag) {
    this.coBrandLimitFlag = coBrandLimitFlag;
  }

  public String getCoBrandCardCode() {
    return coBrandCardCode;
  }

  public void setCoBrandCardCode(String coBrandCardCode) {
    this.coBrandCardCode = coBrandCardCode;
  }

  public String getServingCities() {
    return servingCities;
  }

  public void setServingCities(String servingCities) {
    this.servingCities = servingCities;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Integer getZipCode() {
    return zipCode;
  }

  public void setZipCode(Integer zipCode) {
    this.zipCode = zipCode;
  }

  public DealerMasterComposite getDealerMasterComposite() {
    return dealerMasterComposite;
  }

  public void setDealerMasterComposite(DealerMasterComposite dealerMasterComposite) {
    this.dealerMasterComposite = dealerMasterComposite;
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
    builder.append(dealerMasterComposite.getSupplierId());
    builder.append("|");
    builder.append(supplierDesc);
    builder.append("|");
    builder.append(supplierDealerFlag);
    builder.append("|");
    builder.append(dealerGroupId);
    builder.append("|");
    builder.append(dealerGroupDesc);
    builder.append("|");
    builder.append(pan);
    builder.append("|");
    builder.append(contactPerson);
    builder.append("|");
    builder.append(address1);
    builder.append("|");
    builder.append(address2);
    builder.append("|");
    builder.append(address3);
    builder.append("|");
    builder.append(address4);
    builder.append("|");
    builder.append(stdIsd);
    builder.append("|");
    builder.append(phone1);
    builder.append("|");
    builder.append(mobile);
    builder.append("|");
    builder.append(city);
    builder.append("|");
    builder.append(dealerEmail);
    builder.append("|");
    builder.append(classification);
    builder.append("|");
    builder.append(loyalityProgApplicable);
    builder.append("|");
    builder.append(supplierType);
    builder.append("|");
    builder.append(supplierBranch);
    builder.append("|");
    builder.append(assetCatgId);
    builder.append("|");
    builder.append(processType);
    builder.append("|");
    builder.append(preferredLimitFlag);
    builder.append("|");
    builder.append(storeId);
    builder.append("|");
    builder.append(coBrandLimitFlag);
    builder.append("|");
    builder.append(coBrandCardCode);
    builder.append("|");
    builder.append(servingCities);
    builder.append("|");
    builder.append(state);
    builder.append("|");
    builder.append(zipCode);
    return builder.toString();
  }

}
