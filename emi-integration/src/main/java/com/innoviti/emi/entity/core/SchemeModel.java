package com.innoviti.emi.entity.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.entity.AuditColumns;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "issuer_scheme_model")
public class SchemeModel extends AuditColumns {

  private static final long serialVersionUID = -5572583116258771763L;

  @EmbeddedId
  private SchemeModelComposite schemeModelComposite;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumns({
      @JoinColumn(name = "issuer_scheme_code", referencedColumnName = "issuer_scheme_code"),
      @JoinColumn(referencedColumnName = "record_update_timestamp")})
  private Scheme scheme;

  @Transient
  private String innoIssuerSchemeCode;
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumns({@JoinColumn(name = "model_code", referencedColumnName = "model_code"),
      @JoinColumn(referencedColumnName = "record_update_timestamp")})
  private Model model;

  @Transient
  private String innoModelCode;

  @Transient
  private String innoSchemeModelCode;

  @Transient
  private Date crtupdDate;

  public SchemeModelComposite getSchemeModelComposite() {
    return schemeModelComposite;
  }

  public void setSchemeModelComposite(SchemeModelComposite schemeModelComposite) {
    this.schemeModelComposite = schemeModelComposite;
  }

  public String getInnoIssuerSchemeCode() {
    return innoIssuerSchemeCode;
  }

  public void setInnoIssuerSchemeCode(String innoIssuerSchemeCode) {
    this.innoIssuerSchemeCode = innoIssuerSchemeCode;
  }

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public String getInnoModelCode() {
    return innoModelCode;
  }

  public void setInnoModelCode(String innoModelCode) {
    this.innoModelCode = innoModelCode;
  }

  public Scheme getScheme() {
    return scheme;
  }

  public void setScheme(Scheme scheme) {
    this.scheme = scheme;
  }

  public String getInnoSchemeModelCode() {
    return innoSchemeModelCode;
  }

  public void setInnoSchemeModelCode(String innoSchemeModelCode) {
    this.innoSchemeModelCode = innoSchemeModelCode;
  }

  public Date getCrtupdDate() {
    return crtupdDate;
  }

  public void setCrtupdDate(Date crtupdDt) {
    this.crtupdDate = crtupdDt;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(schemeModelComposite);
    builder.append("|");
    String modelId = model == null ? null : model.getModelComposite().getInnoModelCode();
    builder.append(modelId);
    builder.append("|");
    String schemeId = scheme == null ? null : scheme.getSchemeComposite().getInnoIssuerSchemeCode();
    builder.append(schemeId);
    builder.append("|");
    builder.append(crtupdDate);
    return builder.toString();
  }
  
  public void copyAuditDetailsInto(SchemeModel copy) {
	  copy.setRecordActive(isRecordActive());
	  copy.setCrtupdReason(getCrtupdReason());
	  copy.setCrtupdStatus(getCrtupdStatus());
	  copy.setCrtupdUser(getCrtupdUser());
  }
  
  public void copyNonAuditDetailsInto(SchemeModel copy) {
	  copy.scheme = scheme;
	  copy.model = model;
  }
  
}

