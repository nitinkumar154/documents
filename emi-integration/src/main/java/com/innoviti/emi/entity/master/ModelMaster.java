package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "model_master_bfl")
public class ModelMaster implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private ModelMasterComposite modelMasterComposite;

  @Column(name = "modelno", length = 150, nullable = false)
  private String modelNumber;

  @Column(name = "categoryid", length = 8, nullable = false)
  private Integer categoryId;

  @Column(name = "manufacturerid", length = 8, nullable = false)
  private int manufacturerId;

  @Column(name = "selling_price", precision = 18, scale = 4, nullable = false)
  private BigDecimal sellingPrice;

  @Temporal(TemporalType.DATE)
  @Column(name = "model_expiry_date", length = 20, nullable = false)
  private Date modelExpiryDate;

  @Column(name = "size_id", length = 20, nullable = false)
  private String sizeId;

  @Column(name = "make", length = 35, nullable = false)
  private String make;

  @Column(name = "is_record_active", nullable = false)
  private boolean isRecordActive;

  @Column(name = "record_update_reason", length = 60, nullable = false)
  private String crtupdReason;

  @Column(name = "record_update_status", length = 1, nullable = false)
  private char crtupdStatus;

  @Column(name = "record_update_user", length = 32, nullable = false)
  private String crtupdUser;

  @Column(name = "product_code", length = 3, nullable = true)
  private String productCode;

  public String getModelNumber() {
    return modelNumber;
  }

  public void setModelNumber(String modelNumber) {
    this.modelNumber = modelNumber;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public int getManufacturerId() {
    return manufacturerId;
  }

  public void setManufacturerId(int manufacturerId) {
    this.manufacturerId = manufacturerId;
  }

  public BigDecimal getSellingPrice() {
    return sellingPrice;
  }

  public void setSellingPrice(BigDecimal sellingPrice) {
    this.sellingPrice = sellingPrice;
  }

  public Date getModelExpiryDate() {
    return modelExpiryDate;
  }

  public void setModelExpiryDate(Date modelExpiryDate) {
    this.modelExpiryDate = modelExpiryDate;
  }

  public String getSizeId() {
    return sizeId;
  }

  public void setSizeId(String sizeId) {
    this.sizeId = sizeId;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public ModelMasterComposite getModelMasterComposite() {
    return modelMasterComposite;
  }

  public void setModelMasterComposite(ModelMasterComposite modelMasterComposite) {
    this.modelMasterComposite = modelMasterComposite;
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
    builder.append(modelMasterComposite.getModelId());
    builder.append("|");
    builder.append(modelNumber);
    builder.append("|");
    builder.append(categoryId);
    builder.append("|");
    builder.append(manufacturerId);
    builder.append("|");
    builder.append(sellingPrice);
    builder.append("|");
    builder.append(modelExpiryDate);
    builder.append("|");    
    builder.append(sizeId);
    builder.append("|");
    builder.append(make);
    builder.append("|");
    builder.append(productCode);
    return builder.toString();
  }

}
