package com.innoviti.emi.core.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ResourceEmbeddedSupport extends ResourceSupport{
  
  @JsonUnwrapped
  private Resources<EmbeddedWrapper> embeddedResources;

  public Resources<EmbeddedWrapper> getEmbeddedResources() {
    return embeddedResources;
  }

  public void setEmbeddedResources(Resources<EmbeddedWrapper> embeddedResources) {
    this.embeddedResources = embeddedResources;
  }
  
  
}
