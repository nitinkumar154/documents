package com.innoviti.emi.entity.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Subselect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "model_name")
@Subselect("select * from model_name")
public class ModelView {

  @Id
  @Column(name = "innoModelCode", nullable = false)
  private String innoModelCode;

  @Column(name = "innoModelDisplayName")
  private String innoModelDisplayName;

  public String getInnoModelCode() {
    return innoModelCode;
  }

  public void setInnoModelCode(String innoModelCode) {
    this.innoModelCode = innoModelCode;
  }

  public String getInnoModelDisplayName() {
    return innoModelDisplayName;
  }

  public void setInnoModelDisplayName(String modelDisplayName) {
    this.innoModelDisplayName = modelDisplayName;
  }


}
