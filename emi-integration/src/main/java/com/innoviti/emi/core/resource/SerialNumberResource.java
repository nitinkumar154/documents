package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innoviti.emi.core.controller.SerialNumberController;
import com.innoviti.emi.entity.core.SerialNo;

@Relation(value = "serialNo", collectionRelation = "serialNos")
public class SerialNumberResource extends ResourceSupport {

  private String innoModelSerialNumber;
  private String modelDisplayName;

  private String innoModelCode;

  private String manufacturerSerialNumber;

  private Boolean emiStatus;

  private boolean isRecordActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  private Date crtupdDate;

  public SerialNumberResource(SerialNo serialNo) {

    this.innoModelSerialNumber = serialNo.getSerialNoComposite().getInnoModelSerialNumber();
    this.modelDisplayName = serialNo.getModel().getModelDisplayNo() + " - "
        + serialNo.getModel().getManufacturer().getManufacturerDisplayName();
    this.crtupdDate = serialNo.getSerialNoComposite().getCrtupdDate();
    this.innoModelCode = serialNo.getModel().getModelComposite().getInnoModelCode();
    this.manufacturerSerialNumber = serialNo.getManufacturerSerialNumber();
    this.emiStatus = serialNo.getEmiStatus();
    this.setRecordActive(serialNo.isRecordActive());
    this.crtupdReason = serialNo.getCrtupdReason();
    this.crtupdStatus = serialNo.getCrtupdStatus();
    this.crtupdUser = serialNo.getCrtupdUser();
  }

  @JsonIgnore
  public String getCreateCategoryLink() {
    return linkTo(methodOn(SerialNumberController.class)
        .getByInnoModelSerialNumber(getInnoModelSerialNumber())).withRel("serialNo").getHref();
  }

  public void addSelfLink() {
    this.add(linkTo(SerialNumberController.class).slash(getInnoModelSerialNumber()).withSelfRel());
  }

  public String getInnoModelSerialNumber() {
    return innoModelSerialNumber;
  }

  public String getModelDisplayName() {
    return modelDisplayName;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public String getInnoModelCode() {
    return innoModelCode;
  }

  public String getManufacturerSerialNumber() {
    return manufacturerSerialNumber;
  }

  public Boolean getEmiStatus() {
    return emiStatus;
  }

  public String getCrtupdReason() {
    return crtupdReason;
  }

  public String getCrtupdStatus() {
    return crtupdStatus;
  }

  public String getCrtupdUser() {
    return crtupdUser;
  }

  public boolean isRecordActive() {
    return isRecordActive;
  }

  public void setRecordActive(boolean isRecordActive) {
    this.isRecordActive = isRecordActive;
  }

}
