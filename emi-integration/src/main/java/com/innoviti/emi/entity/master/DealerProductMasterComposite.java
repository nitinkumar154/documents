package com.innoviti.emi.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class DealerProductMasterComposite implements Serializable {

  private static final long serialVersionUID = 5408013541598569048L;

  @Column(name = "supplierid", length = 8, nullable = false)
  private Integer supplierId;

  @NotBlank
  @NotEmpty
  @Column(name = "code", length = 8, nullable = false)
  private String code;

  @Column(name = "record_update_date", nullable = false)
  private Date crtupdDate;

  public Integer getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Integer supplierId) {
    this.supplierId = supplierId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDate) {
    this.crtupdDate = crtupdDate;
  }

}
