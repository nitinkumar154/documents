package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innoviti.emi.core.controller.IssuerBankController;
import com.innoviti.emi.entity.core.IssuerBank;

@Relation(value = "issuerBank", collectionRelation = "issuerBanks")
public class IssuerBankResource extends ResourceSupport {

  private String innoIssuerBankCode;

  private Date crtupdDate;

  private String issuerBankDisplayName;

  private String issuerBankDesc;

  private String issuerBankDisclaimer;

  private Integer emiBankCode;
  
  private String issuerTermsConditions;

  private String issuerDefaultCashbackFlag;
  
  private BigDecimal issuerMinEmiAmount;
  
  private boolean isRecordActive;
  
  private String crtupdReason;
  
  private String crtupdStatus;
  
  private String crtupdUser;
  
  public IssuerBankResource(IssuerBank issuerBank) {
    super();
    this.innoIssuerBankCode =issuerBank.getIssuerBankComposite().getInnoIssuerBankCode();
    this.crtupdDate = issuerBank.getIssuerBankComposite().getCrtupdDate();
    this.issuerBankDisplayName = issuerBank.getIssuerBankDisplayName();
    this.issuerBankDesc = issuerBank.getIssuerBankDesc();
    this.issuerBankDisclaimer = issuerBank.getIssuerBankDisclaimer();
    this.emiBankCode = issuerBank.getEmiBankCode();
    this.issuerTermsConditions = issuerBank.getIssuerTermsConditions();
    this.issuerDefaultCashbackFlag = issuerBank.getIssuerDefaultCashbackFlag().toString();
    this.issuerMinEmiAmount = issuerBank.getIssuerMinEmiAmount();
    this.setRecordActive(issuerBank.isRecordActive());
    this.crtupdReason = issuerBank.getCrtupdReason();
    this.crtupdStatus = issuerBank.getCrtupdStatus();
    this.crtupdUser = issuerBank.getCrtupdUser();
  }
  
  @JsonIgnore
  public String getCreateIssuerBankLink(){
    return linkTo(methodOn(IssuerBankController.class).getIssuerBankById(innoIssuerBankCode))
    .withRel("issuerBank").getHref();
  }
  public void addSelfLink(){
    this.add(linkTo(IssuerBankController.class).slash(getInnoIssuerBankCode()).withSelfRel());
  }

  public String getInnoIssuerBankCode() {
    return innoIssuerBankCode;
  }

  public void setInnoIssuerBankCode(String innoIssuerBankCode) {
    this.innoIssuerBankCode = innoIssuerBankCode;
  }

  public Integer getEmiBankCode() {
    return emiBankCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtuptDate) {
    this.crtupdDate = crtuptDate;
  }

  public String getIssuerBankDisplayName() {
    return issuerBankDisplayName;
  }

  public void setIssuerBankDisplayName(String issuerBankDisplayName) {
    this.issuerBankDisplayName = issuerBankDisplayName;
  }

  public String getIssuerBankDesc() {
    return issuerBankDesc;
  }

  public void setIssuerBankDesc(String issuerBankDesc) {
    this.issuerBankDesc = issuerBankDesc;
  }

  public String getIssuerBankDisclaimer() {
    return issuerBankDisclaimer;
  }

  public void setIssuerBankDisclaimer(String issuerBankDisclaimer) {
    this.issuerBankDisclaimer = issuerBankDisclaimer;
  }

  public String getIssuerTermsConditions() {
    return issuerTermsConditions;
  }

  public void setIssuerTermsConditions(String issuerTermsConditions) {
    this.issuerTermsConditions = issuerTermsConditions;
  }

  public String getIssuerDefaultCashbackFlag() {
    return issuerDefaultCashbackFlag;
  }

  public void setIssuerDefaultCashbackFlag(String issuerDefaultCashbackFlag) {
    this.issuerDefaultCashbackFlag = issuerDefaultCashbackFlag;
  }

  public BigDecimal getIssuerMinEmiAmount() {
    return issuerMinEmiAmount;
  }

  public void setIssuerMinEmiAmount(BigDecimal issuerMinEmiAmount) {
    this.issuerMinEmiAmount = issuerMinEmiAmount;
  }

  public String getCrtupdReason() {
    return crtupdReason;
  }

  public void setCrtupdReason(String crtupdReason) {
    this.crtupdReason = crtupdReason;
  }

  public String getCrtupdStatus() {
    return crtupdStatus;
  }

  public void setCrtupdStatus(String crtupdStatus) {
    this.crtupdStatus = crtupdStatus;
  }

  public String getCrtupdUser() {
    return crtupdUser;
  }

  public void setCrtupdUser(String crtupdUser) {
    this.crtupdUser = crtupdUser;
  }
  public boolean isRecordActive() {
    return isRecordActive;
  }

  public void setRecordActive(boolean isRecordActive) {
    this.isRecordActive = isRecordActive;
  }

}
