package com.innoviti.emi.core.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innoviti.emi.entity.core.ModelView;

@JsonIgnoreProperties(ignoreUnknown = true)
@Relation(value = "modelView", collectionRelation = "modelViews")
public class ModelViewResource extends ResourceSupport {

  private String innoModelDisplayName;

  private String innoModelCode;

  public ModelViewResource(ModelView modelView) {
    super();
    this.innoModelDisplayName = modelView.getInnoModelDisplayName();
    this.innoModelCode = modelView.getInnoModelCode();
  }

  public String getInnoModelCode() {
    return innoModelCode;
  }

  public String getInnoModelDisplayName() {
    return innoModelDisplayName;
  }

}
