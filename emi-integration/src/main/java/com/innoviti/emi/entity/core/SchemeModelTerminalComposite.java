package com.innoviti.emi.entity.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Embeddable
public class SchemeModelTerminalComposite implements Serializable {

  private static final long serialVersionUID = 7996630922068912569L;

  @Column(name = "utid", columnDefinition = "varchar(12) NOT NULL ")
  private String utid;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumns({
    @JoinColumn(name = "issuer_scheme_model_code", referencedColumnName = "issuer_scheme_model_code"),
    @JoinColumn(name = "fk_scheme_model_date", referencedColumnName = "record_update_timestamp")
  })
  private SchemeModel schemeModel;
  
  @Transient
  private String innoSchemeModelCode;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "record_update_timestamp", nullable = false)
  private Date crtupdDate;

  public String getUtid() {
    return utid;
  }

  public void setUtid(String utId) {
    this.utid = utId;
  }

  public SchemeModel getSchemeModel() {
    return schemeModel;
  }

  public void setSchemeModel(SchemeModel schemeModel) {
    this.schemeModel = schemeModel;
  }

  public String getInnoSchemeModelCode() {
    return innoSchemeModelCode;
  }

  public void setInnoSchemeModelCode(String innoIssuerSchemeCode) {
    this.innoSchemeModelCode = innoIssuerSchemeCode;
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
    builder.append(utid);
    builder.append("|");
    builder.append(schemeModel);
    builder.append("|");
    builder.append(innoSchemeModelCode);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }
  
}
