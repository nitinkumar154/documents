package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innoviti.emi.core.controller.CategoryController;
import com.innoviti.emi.entity.core.Category;

@Relation(value = "category", collectionRelation = "categories")
public class CategoryResource extends ResourceSupport {

  private String innoCategoryCode;

  private Date crtupdDate;

  private String bajajCategoryCode;

  private String categoryDesc;

  private String categoryDisplayName;

  private boolean isRecordActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  public CategoryResource(Category category) {

    this.innoCategoryCode = category.getCategoryComposite().getInnoCategoryCode();
    this.crtupdDate = category.getCategoryComposite().getCrtupdDate();
    this.bajajCategoryCode = category.getBajajCategoryCode();
    this.categoryDesc = category.getCategoryDesc();
    this.categoryDisplayName = category.getCategoryDisplayName();
    this.setRecordActive(category.isRecordActive());
    this.crtupdReason = category.getCrtupdReason();
    this.crtupdStatus = category.getCrtupdStatus();
    this.crtupdUser = category.getCrtupdUser();
  }

  @JsonIgnore
  public String getCreateCategoryLink() {
    return linkTo(CategoryController.class).slash(getInnoCategoryCode()).withRel("category")
        .getHref();
  }

  public void addSelfLink() {
    this.add(linkTo(CategoryController.class).slash(getInnoCategoryCode()).withSelfRel());
  }

  public String getInnoCategoryCode() {
    return innoCategoryCode;
  }

  public String getBajajCategoryCode() {
    return bajajCategoryCode;
  }

  public String getCategoryDesc() {
    return categoryDesc;
  }

  public String getCategoryDisplayName() {
    return categoryDisplayName;
  }

  public String getCrtupdReason() {
    return crtupdReason;
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

  public void setInnoCategoryCode(String innoCategoryCode) {
    this.innoCategoryCode = innoCategoryCode;
  }

  public void setBajajCategoryCode(String bajajCategoryCode) {
    this.bajajCategoryCode = bajajCategoryCode;
  }

  public void setCategoryDesc(String categoryDesc) {
    this.categoryDesc = categoryDesc;
  }

  public void setCategoryDisplayName(String categoryDisplayName) {
    this.categoryDisplayName = categoryDisplayName;
  }

  public void setCrtupdReason(String crtupdReason) {
    this.crtupdReason = crtupdReason;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  public boolean isRecordActive() {
    return isRecordActive;
  }

  public void setRecordActive(boolean isRecordActive) {
    this.isRecordActive = isRecordActive;
  }

}
