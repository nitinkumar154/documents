package com.innoviti.emi.entity.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "branch_bfl")
public class BranchMaster implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private BranchMasterComposite branchMasterComposite;

  @Column(name = "main_branch", length = 6, nullable = false)
  private String mainBranch;

  @Column(name = "branch_name", length = 30, nullable = false)
  private String branchName;

  @Column(name = "branch_addr_1", length = 32, nullable = false)
  private String branchAddr1;

  @Column(name = "branch_addr_2", length = 32, nullable = false)
  private String branchAddr2;

  @Column(name = "branch_addr_3", length = 32, nullable = false)
  private String branchAddr3;

  @Column(name = "branch_addr_4", length = 32, nullable = false)
  private String branchAddr4;

  @Column(name = "branch_region_code", length = 3, nullable = false)
  private String branchRegionCode;

  @Column(name = "branch_zone_code", length = 2, nullable = false)
  private String branchZoneCode;

  @Column(name = "branch_state_code", length = 5, nullable = false)
  private String branchStateCode;

  @Column(name = "branch_type", length = 4, nullable = false)
  private String branchType;

  @Column(name = "branch_area_code", length = 2, nullable = false)
  private String branchAreaCode;

  @Column(name = "bank_code", length = 5, nullable = false)
  private String bankCode;

  @Column(name = "branch_city", length = 24, nullable = false)
  private String branchCity;

  @Column(name = "branch_pin", length = 6, nullable = false)
  private String branchPin;

  @Column(name = "branch_state", length = 15, nullable = false)
  private String branchState;

  @Column(name = "branch_email_id", length = 50, nullable = false)
  private String branchEmailId;

  @Column(name = "corporate_id", length = 15, nullable = false)
  private String corporateId;

  @Column(name = "main_branch_flag", length = 1, nullable = false)
  private String mainBranchFlag;

  @Column(name = "branch_phone_number", length = 12, nullable = false)
  private String branchPhoneNumber;

  @Column(name = "branch_contact_person_name", length = 32, nullable = false)
  private String branchContactPersonName;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  public String getMainBranch() {
    return mainBranch;
  }

  public void setMainBranch(String mainBranch) {
    this.mainBranch = mainBranch;
  }

  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public String getBranchAddr1() {
    return branchAddr1;
  }

  public void setBranchAddr1(String branchAddr1) {
    this.branchAddr1 = branchAddr1;
  }

  public String getBranchAddr2() {
    return branchAddr2;
  }

  public void setBranchAddr2(String branchAddr2) {
    this.branchAddr2 = branchAddr2;
  }

  public String getBranchAddr3() {
    return branchAddr3;
  }

  public void setBranchAddr3(String branchAddr3) {
    this.branchAddr3 = branchAddr3;
  }

  public String getBranchAddr4() {
    return branchAddr4;
  }

  public void setBranchAddr4(String branchAddr4) {
    this.branchAddr4 = branchAddr4;
  }

  public String getBranchRegionCode() {
    return branchRegionCode;
  }

  public void setBranchRegionCode(String branchRegionCode) {
    this.branchRegionCode = branchRegionCode;
  }

  public String getBranchZoneCode() {
    return branchZoneCode;
  }

  public void setBranchZoneCode(String branchZoneCode) {
    this.branchZoneCode = branchZoneCode;
  }

  public String getBranchStateCode() {
    return branchStateCode;
  }

  public void setBranchStateCode(String branchStateCode) {
    this.branchStateCode = branchStateCode;
  }

  public String getBranchType() {
    return branchType;
  }

  public void setBranchType(String branchType) {
    this.branchType = branchType;
  }

  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public String getBranchCity() {
    return branchCity;
  }

  public void setBranchCity(String branchCity) {
    this.branchCity = branchCity;
  }

  public String getBranchPin() {
    return branchPin;
  }

  public void setBranchPin(String branchPin) {
    this.branchPin = branchPin;
  }

  public String getBranchState() {
    return branchState;
  }

  public void setBranchState(String branchState) {
    this.branchState = branchState;
  }

  public String getBranchEmailId() {
    return branchEmailId;
  }

  public void setBranchEmailId(String branchEmailId) {
    this.branchEmailId = branchEmailId;
  }

  public String getCorporateId() {
    return corporateId;
  }

  public void setCorporateId(String corporateId) {
    this.corporateId = corporateId;
  }

  public String getMainBranchFlag() {
    return mainBranchFlag;
  }

  public void setMainBranchFlag(String mainBranchFlag) {
    this.mainBranchFlag = mainBranchFlag;
  }

  public String getBranchPhoneNumber() {
    return branchPhoneNumber;
  }

  public void setBranchPhoneNumber(String branchPhoneNumber) {
    this.branchPhoneNumber = branchPhoneNumber;
  }

  public String getBranchContactPersonName() {
    return branchContactPersonName;
  }

  public void setBranchContactPersonName(String branchContactPersonName) {
    this.branchContactPersonName = branchContactPersonName;
  }

  public String getBranchAreaCode() {
    return branchAreaCode;
  }

  public void setBranchAreaCode(String branchAreaCode) {
    this.branchAreaCode = branchAreaCode;
  }

  public BranchMasterComposite getBranchMasterComposite() {
    return branchMasterComposite;
  }

  public void setBranchMasterComposite(BranchMasterComposite branchMasterComposite) {
    this.branchMasterComposite = branchMasterComposite;
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

  public String getCrtupdUser() {
    return crtupdUser;
  }

  public void setCrtupdUser(String crtupdUser) {
    this.crtupdUser = crtupdUser;
  }

  public char getCrtupdStatus() {
    return crtupdStatus;
  }

  public void setCrtupdStatus(char crtupdStatus) {
    this.crtupdStatus = crtupdStatus;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(branchMasterComposite.getBranchCode());
    builder.append("|");
    builder.append(mainBranch);
    builder.append("|");
    builder.append(branchName);
    builder.append("|");
    builder.append(branchAddr1);
    builder.append("|");
    builder.append(branchAddr2);
    builder.append("|");
    builder.append(branchAddr3);
    builder.append("|");
    builder.append(branchRegionCode);
    builder.append("|");
    builder.append(branchZoneCode);
    builder.append("|");
    builder.append(branchStateCode);
    builder.append("|");
    builder.append(branchType);
    builder.append("|");
    builder.append(branchAreaCode);
    builder.append("|");
    builder.append(bankCode);
    builder.append("|");
    builder.append(branchAddr4);
    builder.append("|");
    builder.append(branchCity);
    builder.append("|");
    builder.append(branchPin);
    builder.append("|");
    builder.append(branchState);
    builder.append("|");
    builder.append(branchEmailId);
    builder.append("|");
    builder.append(corporateId);
    builder.append("|");
    builder.append(mainBranchFlag);
    builder.append("|");
    builder.append(branchPhoneNumber);
    builder.append("|");
    builder.append(branchContactPersonName);
    return builder.toString();
  }

}
