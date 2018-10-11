package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Date;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innoviti.emi.core.controller.ProductController;
import com.innoviti.emi.entity.core.Product;

@Relation(value="product", collectionRelation="products")
public class ProductResource extends ResourceSupport {

  private String productCode;
  
  private String innoProductTypeCode;
  
  private String issuerProductTypeName;
  
  private Date crtupdDate;
  
  private String bajajProductTypeCode;
  
  private boolean isRecordActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  public ProductResource(Product product) {
    this.productCode = product.getProductComposite().getProductCode();
    this.innoProductTypeCode =product.getInnoProductTypeCode();
    this.issuerProductTypeName = product.getIssuerProductTypeName();
    this.crtupdDate = product.getProductComposite().getCrtupdDate();
    this.bajajProductTypeCode = product.getBajajProductTypeCode();
    this.setRecordActive(product.isRecordActive());
    this.crtupdReason = product.getCrtupdReason();
    this.crtupdStatus = product.getCrtupdStatus();
    this.crtupdUser = product.getCrtupdUser();
  }
  
  @JsonIgnore
  public String getCreateProductLink(){
    return linkTo(ProductController.class).slash(getInnoProductTypeCode()).slash(getCrtupdDate())
        .withRel("product").getHref();
  }
  public void addSelfLink(){
    this.add(linkTo(ProductController.class).slash(getInnoProductTypeCode()).withSelfRel());
  }
  public Link getSelfLink(){
    return linkTo(ProductController.class).slash(getInnoProductTypeCode()).withSelfRel();
  }
  
  public String getProductCode() {
    return productCode;
  }

  public String getInnoProductTypeCode() {
    return innoProductTypeCode;
  }

  public void setInnoProductTypeCode(String innoProductTypeCode) {
    this.innoProductTypeCode = innoProductTypeCode;
  }

  public String getIssuerProductTypeName() {
    return issuerProductTypeName;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

  public String getBajajProductTypeCode() {
    return bajajProductTypeCode;
  }

  public void setBajajProductTypeCode(String bajajProductTypeCode) {
    this.bajajProductTypeCode = bajajProductTypeCode;
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
