package com.innoviti.emi.entity.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.entity.AuditColumns;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "categories")
public class Category extends AuditColumns {

  private static final long serialVersionUID = -663694974568664004L;

  @EmbeddedId
  private CategoryComposite categoryComposite;

  @Column(name = "bajaj_category_code", length = 10)
  private String bajajCategoryCode;

  @Column(name = "category_description", length = 50, nullable = false)
  private String categoryDesc;

  @Column(name = "category_display_name", length = 50, nullable = false)
  private String categoryDisplayName;

  @Transient
  private String innoCategoryCode;

  @Transient
  private Date crtupdDate;


  public String getInnoCategoryCode() {
    return innoCategoryCode;
  }

  public void setInnoCategoryCode(String innoCategoryCode) {
    this.innoCategoryCode = innoCategoryCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  public CategoryComposite getCategoryComposite() {
    return categoryComposite;
  }

  public void setCategoryComposite(CategoryComposite categoryComposite) {
    this.categoryComposite = categoryComposite;
  }

  public String getBajajCategoryCode() {
    return bajajCategoryCode;
  }

  public void setBajajCategoryCode(String bajajCategoryCode) {
    this.bajajCategoryCode = bajajCategoryCode;
  }

  public String getCategoryDesc() {
    return categoryDesc;
  }

  public void setCategoryDesc(String categoryDesc) {
    this.categoryDesc = categoryDesc;
  }

  public String getCategoryDisplayName() {
    return categoryDisplayName;
  }

  public void setCategoryDisplayName(String categoryDisplayName) {
    this.categoryDisplayName = categoryDisplayName;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(categoryComposite.getCrtupdDate());
    builder.append("|");
    builder.append(categoryComposite.getInnoCategoryCode());
    builder.append("|");
    builder.append(bajajCategoryCode);
    builder.append("|");
    builder.append(categoryDesc);
    builder.append("|");
    builder.append(categoryDisplayName);
    builder.append("|");
    builder.append(innoCategoryCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }
}
