package com.innoviti.emi.entity.core;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.entity.AuditColumns;
import com.innoviti.emi.entity.ModelCompositeBridge;

@Indexed(index="auto")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "models")
public class Model extends AuditColumns {


  private static final long serialVersionUID = 18578505163227944L;

  @FieldBridge(impl = ModelCompositeBridge.class)
  @EmbeddedId
  private ModelComposite modelComposite;

  @Column(name = "bajaj_model_code", nullable = true, length = 10)
  private String bajajModelCode;

  @Column(name = "bajaj_model_number", nullable = true, length = 150)
  private String bajajModelNo;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({
      @JoinColumn(name = "product_code", referencedColumnName = "product_code"),
      @JoinColumn(referencedColumnName = "record_update_timestamp")})
  private Product product;

  @Transient
  private String productCode;
  
  @Field(store = Store.NO)
  @Column(name = "model_display_number", nullable = false, length = 50)
  private String modelDisplayNo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({
    @JoinColumn(name = "category_code", referencedColumnName = "category_code"),
    @JoinColumn(referencedColumnName = "record_update_timestamp")
  })
  private Category category;

  @Transient
  private String innoCategoryCode;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumns({
      @JoinColumn(name = "manufacturer_code", referencedColumnName = "manufacturer_code"),
      @JoinColumn(referencedColumnName = "record_update_timestamp")})
  private Manufacturer manufacturer;

  @Transient
  private String innoManufacturerCode;

  @Column(name = "bajaj_model_selling_price", nullable = true, precision = 10, scale = 2)
  private BigDecimal bajajModelSellingPrice;

  @Temporal(TemporalType.DATE)
  @Column(name = "bajaj_model_expiry_date", nullable = true)
  private Date bajajModelExpiryDate;

  @Column(name = "model_min_selling_price", nullable = true, precision = 10, scale = 2)
  private BigDecimal innoMinSellingPrice;

  @Column(name = "model_max_selling_price", nullable = true, precision = 10, scale = 2)
  private BigDecimal innoMaxSellingPrice;

  @Transient
  private String innoModelCode;

  @Transient
  private Date crtupdDate;

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
  
   public void setProductCode(String innoProductTypeCode) {
   this.productCode = innoProductTypeCode;
   }

  public String getModelDisplayNo() {
    return modelDisplayNo;
  }

  public void setModelDisplayNo(String modelDisplayNo) {
    this.modelDisplayNo = modelDisplayNo;
  }

   public String getInnoCategoryCode() {
   return innoCategoryCode;
   }
  
   public void setInnoCategoryCode(String innoCategoryCode) {
   this.innoCategoryCode = innoCategoryCode;
   }

   public String getInnoManufacturerCode() {
   return innoManufacturerCode;
   }
  
   public void setInnoManufacturerCode(String innoManufactureCode) {
   this.innoManufacturerCode = innoManufactureCode;
   }

  public BigDecimal getBajajModelSellingPrice() {
    return bajajModelSellingPrice;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

   public Category getCategory() {
   return category;
   }
  
   public void setCategory(Category category) {
   this.category = category;
   }

  public Manufacturer getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(Manufacturer manufacturer) {
    this.manufacturer = manufacturer;
  }

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

  public ModelComposite getModelComposite() {
    return modelComposite;
  }

  public void setModelComposite(ModelComposite modelComposite) {
    this.modelComposite = modelComposite;
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(modelComposite.toString());
    builder.append("|");
    builder.append(bajajModelCode);
    builder.append("|");
    builder.append(bajajModelNo);
    builder.append("|");
    String productCode = product == null ? null : product.getProductComposite().getProductCode();
    builder.append(productCode);
    builder.append("|");
    builder.append(bajajModelSellingPrice);
    builder.append("|");
    builder.append(bajajModelExpiryDate);
    builder.append("|");
    builder.append(innoMinSellingPrice);
    builder.append("|");
    builder.append(innoMaxSellingPrice);
    builder.append("|");
    builder.append(isRecordActive());
    return builder.toString();
  }
  
}
