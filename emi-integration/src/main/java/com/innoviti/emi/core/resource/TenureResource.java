package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innoviti.emi.core.controller.TenureController;
import com.innoviti.emi.entity.core.Tenure;

@Relation(value = "tenure", collectionRelation = "tenures")
public class TenureResource extends ResourceSupport {

  private String innoTenureCode;

  private Date crtupdDate;

  private String tenureMonth;

  private String tenureDisplayName;

  private String fieldA;

  private boolean isActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  public TenureResource(Tenure tenure) {
    this.innoTenureCode = tenure.getTenureComposite().getInnoTenureCode();
    this.crtupdDate = tenure.getTenureComposite().getCrtupdDate();
    this.tenureMonth = tenure.getTenureMonth();
    this.tenureDisplayName = tenure.getTenureDisplayName();
    this.isActive = tenure.isRecordActive();
    this.crtupdReason = tenure.getCrtupdReason();
    this.crtupdStatus = tenure.getCrtupdStatus();
    this.crtupdUser = tenure.getCrtupdUser();
  }

  @JsonIgnore
  public String getCreateCategoryLink() {
    return linkTo(methodOn(TenureController.class).getTenureByInnoTenureCode(getInnoTenureCode()))
        .withRel("tenure").getHref();
  }

  public void addSelfLink() {
    this.add(linkTo(TenureController.class).slash(getInnoTenureCode()).withSelfRel());
  }

  public String getInnoTenureCode() {
    return innoTenureCode;
  }

  public void setInnoTenureCode(String innoTenureCode) {
    this.innoTenureCode = innoTenureCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  public String getTenureMonth() {
    return tenureMonth;
  }

  public void setTenureMonth(String tenureMonth) {
    this.tenureMonth = tenureMonth;
  }

  public String getTenureDisplayName() {
    return tenureDisplayName;
  }

  public void setTenureDisplayName(String tenureDisplayName) {
    this.tenureDisplayName = tenureDisplayName;
  }

  public String getFieldA() {
    return fieldA;
  }

  public void setFieldA(String fieldA) {
    this.fieldA = fieldA;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
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

}
