package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.innoviti.emi.constant.OnUsOffUsStatus;
import com.innoviti.emi.constant.UTIDUpdateStatus;
import com.innoviti.emi.core.controller.SchemeModelTerminalController;
import com.innoviti.emi.entity.core.SchemeModelTerminal;

@Relation(value = "schemeModelTerminal", collectionRelation = "schemeModelTerminals")
public class SchemeModelTerminalResource extends ResourceSupport {

  private String utid;

  private String innoSchemeModelCode;

  private String dealerId;
  
  private String bajajProductTypeCode;
  
  private Date crtupdDate;

  private boolean isRecordActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  private String issuerCustomField;

  @Enumerated(EnumType.STRING)
  private UTIDUpdateStatus issuerSchemeTerminalSyncStatus;
  
  @Enumerated(EnumType.STRING)
  private OnUsOffUsStatus onUsOffUs;

  public SchemeModelTerminalResource(SchemeModelTerminal schemeModelTerminal) {
    this.utid = schemeModelTerminal.getSchemeModelTerminalComposite().getUtid();
    this.innoSchemeModelCode =
        schemeModelTerminal.getSchemeModelTerminalComposite().getSchemeModel().getSchemeModelComposite().getInnoSchemeModelCode();
    this.bajajProductTypeCode = schemeModelTerminal.getBajajProductTypeCode();
    this.crtupdDate = schemeModelTerminal.getSchemeModelTerminalComposite().getCrtupdDate();
    this.dealerId = schemeModelTerminal.getDealerId();
    this.issuerSchemeTerminalSyncStatus = schemeModelTerminal.getIssuerSchemeTerminalSyncStatus();
    this.onUsOffUs = schemeModelTerminal.getOnUsOffUs();
    this.setRecordActive(schemeModelTerminal.isRecordActive());
    this.issuerCustomField = schemeModelTerminal.getIssuerCustomField();
    this.crtupdReason = schemeModelTerminal.getCrtupdReason();
    this.crtupdStatus = schemeModelTerminal.getCrtupdStatus();
    this.crtupdUser = schemeModelTerminal.getCrtupdUser();
  }

  public String createSchemeModelTerminalLink() {
    return linkTo(methodOn(SchemeModelTerminalController.class)
        .getSchemeModelTerminalResourceList(getUtid(), getInnoSchemeModelCode()))
            .withRel("schemeModelTerminal").getHref();
  }

  public void createSelfLink() {
    this.add(linkTo(SchemeModelTerminalController.class).slash(getUtid())
        .slash(getInnoSchemeModelCode()).withSelfRel());
  }

  public String getUtid() {
    return utid;
  }

  public void setUtid(String utId) {
    this.utid = utId;
  }

  public String getInnoSchemeModelCode() {
    return innoSchemeModelCode;
  }

  public void setInnoSchemeModelCode(String innoSchemeModelCode) {
    this.innoSchemeModelCode = innoSchemeModelCode;
  }

  public String getDealerId() {
    return dealerId;
  }

  public void setDealerId(String dealerId) {
    this.dealerId = dealerId;
  }

  public OnUsOffUsStatus getOnUsOffUs() {
    return onUsOffUs;
  }

  public String getBajajProductTypeCode() {
    return bajajProductTypeCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
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

  public String getIssuerCustomField() {
    return issuerCustomField;
  }

  public void setIssuerCustomField(String issuerCustomField) {
    this.issuerCustomField = issuerCustomField;
  }

  public UTIDUpdateStatus getIssuerSchemeTerminalSyncStatus() {
    return issuerSchemeTerminalSyncStatus;
  }

  public void setIssuerSchemeTerminalSyncStatus(UTIDUpdateStatus issuerSchemeTerminalSyncStatus) {
    this.issuerSchemeTerminalSyncStatus = issuerSchemeTerminalSyncStatus;
  }

}
