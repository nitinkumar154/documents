package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.innoviti.emi.core.controller.ManufacturerController;
import com.innoviti.emi.entity.core.Manufacturer;

@Relation(value = "manufacturer", collectionRelation = "manufacturers")
public class ManufacturerResource extends ResourceSupport {

  private String innoManufacturerCode;

  private Date crtupdDate;

  private String bajajManufacturerCode;

  private String manufacturerDesc;

  private String manufacturerDisplayName;

  private boolean isRecordActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  public ManufacturerResource(Manufacturer manufacturer) {

    this.innoManufacturerCode = manufacturer.getManufacturerComposite().getInnoManufacturerCode();
    this.crtupdDate = manufacturer.getManufacturerComposite().getCrtupdDate();
    this.bajajManufacturerCode = manufacturer.getBajajManufacturerCode();
    this.manufacturerDesc = manufacturer.getManufacturerDesc();
    this.manufacturerDisplayName = manufacturer.getManufacturerDisplayName();
    this.isRecordActive = manufacturer.isRecordActive();
    this.crtupdReason = manufacturer.getCrtupdReason();
    this.crtupdStatus = manufacturer.getCrtupdStatus();
    this.crtupdUser = manufacturer.getCrtupdUser();
  }

  public String createManufacturerLink() {
    return linkTo(
        methodOn(ManufacturerController.class).getListOfManufacturer(getInnoManufacturerCode()))
            .withRel("manufacturer").getHref();
  }

  public void createSelfLink() {
    this.add(linkTo(ManufacturerController.class).slash(getInnoManufacturerCode()).withSelfRel());
  }

  public String getInnoManufacturerCode() {
    return innoManufacturerCode;
  }

  public void setInnoManufacturerCode(String innoManufacturerCode) {
    this.innoManufacturerCode = innoManufacturerCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtuptDate) {
    this.crtupdDate = crtuptDate;
  }

  public String getBajajManufacturerCode() {
    return bajajManufacturerCode;
  }

  public void setBajajManufacturerCode(String bajajManufacturerCode) {
    this.bajajManufacturerCode = bajajManufacturerCode;
  }

  public String getManufacturerDesc() {
    return manufacturerDesc;
  }

  public void setManufacturerDesc(String manufacturerDesc) {
    this.manufacturerDesc = manufacturerDesc;
  }

  public String getManufacturerDisplayName() {
    return manufacturerDisplayName;
  }

  public void setManufacturerDisplayName(String manufacturerDisplayName) {
    this.manufacturerDisplayName = manufacturerDisplayName;
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
