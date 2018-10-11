package com.innoviti.emi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RespositoryRestConfiguration extends RepositoryRestConfigurerAdapter {

  @Value("${spring.data.rest.page-default-size}")
  private String pageSize;

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {

    int defaultPageSize = 0;
    config.setMaxPageSize(Integer.MAX_VALUE);
    if (pageSize != null)
      defaultPageSize = Integer.valueOf(pageSize).intValue();
    if (defaultPageSize > 0) {
      config.setDefaultPageSize(defaultPageSize);
    }
  }
}
