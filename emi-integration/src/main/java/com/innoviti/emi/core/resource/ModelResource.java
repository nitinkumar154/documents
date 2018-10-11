package com.innoviti.emi.core.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innoviti.emi.core.controller.ModelController;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Product;

@Relation(value = "model", collectionRelation = "models")
public class ModelResource extends ResourceEmbeddedSupport {

  private String innoModelCode;

  private Date crtupdDate;

  private String bajajModelCode;

  private String bajajModelNo;

  private String productCode;
  private String issuerProductTypeName;

  private String modelDisplayNo;

  private String innoCategoryCode;
  private String innoCategoryName;

  private String innoManufacturerCode;
  private String innoManufacturerName;

  private BigDecimal bajajModelSellingPrice;

  private Date bajajModelExpiryDate;

  private BigDecimal innoMinSellingPrice;

  private BigDecimal innoMaxSellingPrice;

  private String fieldA;

  private boolean isRecordActive;

  private String crtupdReason;

  private String crtupdStatus;

  private String crtupdUser;

  @JsonInclude(value = Include.NON_NULL)
  private Product product;


  public ModelResource(Model model) {
    super();
    this.innoModelCode = model.getModelComposite().getInnoModelCode();
    this.crtupdDate = model.getModelComposite().getCrtupdDate();
    this.bajajModelCode = model.getBajajModelCode();
    this.bajajModelNo = model.getBajajModelNo();
    this.productCode = model.getProduct().getProductComposite().getProductCode();
    this.issuerProductTypeName = model.getProduct().getIssuerProductTypeName();
    this.modelDisplayNo = model.getModelDisplayNo();
    this.innoCategoryCode = model.getCategory().getCategoryComposite().getInnoCategoryCode();
    this.innoCategoryName = model.getCategory().getCategoryDisplayName();
    this.innoManufacturerCode =
        model.getManufacturer().getManufacturerComposite().getInnoManufacturerCode();
    this.innoManufacturerName = model.getManufacturer().getManufacturerDisplayName();
    this.bajajModelSellingPrice = model.getBajajModelSellingPrice();
    this.bajajModelExpiryDate = model.getBajajModelExpiryDate();
    this.innoMinSellingPrice = model.getInnoMinSellingPrice();
    this.innoMaxSellingPrice = model.getInnoMaxSellingPrice();
    this.isRecordActive = model.isRecordActive();
    this.crtupdReason = model.getCrtupdReason();
    this.crtupdStatus = model.getCrtupdStatus();
    this.crtupdUser = model.getCrtupdUser();

    EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
    ProductResource productResource = new ProductResource(model.getProduct());
    List<EmbeddedWrapper> wrraper = Arrays.asList(wrappers.wrap(productResource));
    setEmbeddedResources(new Resources<>(wrraper, productResource.getSelfLink()));
  }

  @JsonIgnore
  public String getCreateModelLink() {
    return linkTo(ModelController.class).slash(getInnoModelCode()).slash(getCrtupdDate())
        .withRel("product").getHref();
  }



  public void addSelfLink() {
    this.add(linkTo(ModelController.class).slash(getInnoModelCode()).withSelfRel());
  }

  public String getInnoModelCode() {
    return innoModelCode;
  }

  public void setInnoModelCode(String innoModelCode) {
    this.innoModelCode = innoModelCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtuptDate) {
    this.crtupdDate = crtuptDate;
  }

  public String getBajajModelCode() {
    return bajajModelCode;
  }

  public void setBajajModelCode(String bajajModelCode) {
    this.bajajModelCode = bajajModelCode;
  }

  public String getBajajModelNo() {
    return bajajModelNo;
  }

  public void setBajajModelNo(String bajajModelNo) {
    this.bajajModelNo = bajajModelNo;
  }

  public String getProductCode() {
    return productCode;
  }

  public String getModelDisplayNo() {
    return modelDisplayNo;
  }

  public String getIssuerProductTypeName() {
    return issuerProductTypeName;
  }

  public String getInnoCategoryName() {
    return innoCategoryName;
  }

  public String getInnoManufacturerName() {
    return innoManufacturerName;
  }

  public void setModelDisplayNo(String modelDisplayNo) {
    this.modelDisplayNo = modelDisplayNo;
  }

  public String getInnoCategoryCode() {
    return innoCategoryCode;
  }

  public String getInnoManufacturerCode() {
    return innoManufacturerCode;
  }

  // public Category getCategory() {
  // return category;
  // }

  public BigDecimal getBajajModelSellingPrice() {
    return bajajModelSellingPrice;
  }

  // public Product getProduct() {
  // return product;
  // }
  //
  // public Manufacturer getManufacturer() {
  // return manufacturer;
  // }

  public void setBajajModelSellingPrice(BigDecimal bajajModelSellingPrice) {
    this.bajajModelSellingPrice = bajajModelSellingPrice;
  }

  public Date getBajajModelExpiryDate() {
    return bajajModelExpiryDate;
  }

  public void setBajajModelExpiryDate(Date bajajModelExpiryDate) {
    this.bajajModelExpiryDate = bajajModelExpiryDate;
  }

  public BigDecimal getInnoMinSellingPrice() {
    return innoMinSellingPrice;
  }

  public void setInnoMinSellingPrice(BigDecimal innoMinSellingPrice) {
    this.innoMinSellingPrice = innoMinSellingPrice;
  }

  public BigDecimal getInnoMaxSellingPrice() {
    return innoMaxSellingPrice;
  }

  public void setInnoMaxSellingPrice(BigDecimal innoMaxSellingPrice) {
    this.innoMaxSellingPrice = innoMaxSellingPrice;
  }

  public String getFieldA() {
    return fieldA;
  }

  public void setFieldA(String fieldA) {
    this.fieldA = fieldA;
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

  public Product getProduct() {
    return product;
  }

}
