package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innoviti.emi.core.controller.SchemeModelController;
import com.innoviti.emi.entity.core.SchemeModel;

@Relation(value = "schemeModel", collectionRelation = "schemeModels")
public class SchemeModelResource extends ResourceSupport {


  private String innoSchemeModelCode;

  private String schemeModelDisplayName;

  private Date crtupdDate;

  private String innoIssuerSchemeCode;

  private String innoModelCode;

  private boolean isRecordActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  public SchemeModelResource(SchemeModel schemeModel) {
    super();
    this.innoSchemeModelCode = schemeModel.getSchemeModelComposite().getInnoSchemeModelCode();
    this.schemeModelDisplayName = schemeModel.getScheme().getSchemeDisplayName() + " :: "
        + schemeModel.getModel().getModelDisplayNo() + " - "
        + schemeModel.getModel().getManufacturer().getManufacturerDisplayName();
    this.crtupdDate = schemeModel.getSchemeModelComposite().getCrtupdDate();
    this.innoIssuerSchemeCode =
        schemeModel.getScheme().getSchemeComposite().getInnoIssuerSchemeCode();
    this.innoModelCode = schemeModel.getModel().getModelComposite().getInnoModelCode();
    this.isRecordActive = schemeModel.isRecordActive();
    this.crtupdReason = schemeModel.getCrtupdReason();
    this.crtupdStatus = schemeModel.getCrtupdStatus();
    this.crtupdUser = schemeModel.getCrtupdUser();
  }

  @JsonIgnore
  public String getCreateSchemeModelLink() {
    return linkTo(SchemeModelController.class).slash(getInnoSchemeModelCode())
        .withRel("schemeModel").getHref();
  }

  public void addSelfLink() {
    this.add(linkTo(SchemeModelController.class).slash(getInnoSchemeModelCode()).withSelfRel());
  }

  public String getInnoSchemeModelCode() {
    return innoSchemeModelCode;
  }

  public void setInnoSchemeModelCode(String innoSchemeModelCode) {
    this.innoSchemeModelCode = innoSchemeModelCode;
  }

  public String getSchemeModelDisplayName() {
    return schemeModelDisplayName;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  public String getInnoIssuerSchemeCode() {
    return innoIssuerSchemeCode;
  }

  public void setInnoIssuerSchemeCode(String innoIssuerSchemeCode) {
    this.innoIssuerSchemeCode = innoIssuerSchemeCode;
  }

  public String getInnoModelCode() {
    return innoModelCode;
  }

  public void setInnoModelCode(String innoModelCode) {
    this.innoModelCode = innoModelCode;
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

  public boolean isRecordActive() {
    return isRecordActive;
  }

  public void setCrtupdStatus(String crtupdStatus) {
    this.crtupdStatus = crtupdStatus;
  }

  public String getCrtupdUser() {
    return crtupdUser;
  }

  public void setCrtupd_user(String crtupdUser) {
    this.crtupdUser = crtupdUser;
  }

}
