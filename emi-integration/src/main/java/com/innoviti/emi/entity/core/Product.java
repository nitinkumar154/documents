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
@Table(name = "products")
public class Product extends AuditColumns {

  private static final long serialVersionUID = 988902138112017202L;

  @EmbeddedId
  private ProductComposite productComposite;

  @Transient
  private String productCode;

  @Column(name = "product_type_code", length = 20, nullable = false)
  private String innoProductTypeCode;

  @Column(name = "bajaj_product_type_code", nullable = true, length = 10)
  private String bajajProductTypeCode;

  @Column(name = "issuer_product_display_name", nullable = true, length = 50)
  private String issuerProductTypeName;

  @Transient
  private Date crtupdDate;

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getBajajProductTypeCode() {
    return bajajProductTypeCode;
  }

  public void setBajajProductTypeCode(String bajajProductTypeCode) {
    this.bajajProductTypeCode = bajajProductTypeCode;
  }

  public String getIssuerProductTypeName() {
    return issuerProductTypeName;
  }

  public void setIssuerProductTypeName(String innoProductTypeName) {
    this.issuerProductTypeName = innoProductTypeName;
  }

  public ProductComposite getProductComposite() {
    return productComposite;
  }

  public void setProductComposite(ProductComposite productComposite) {
    this.productComposite = productComposite;
  }

  public String getInnoProductTypeCode() {
    return innoProductTypeCode;
  }

  public void setInnoProductTypeCode(String innoProductTypeCode) {
    this.innoProductTypeCode = innoProductTypeCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtuptDate) {
    this.crtupdDate = crtuptDate;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(productComposite.getCrtupdDate());
    builder.append("|");
    builder.append(productComposite.getProductCode());
    builder.append("|");
    builder.append(innoProductTypeCode);
    builder.append("|");
    builder.append(bajajProductTypeCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }



}
