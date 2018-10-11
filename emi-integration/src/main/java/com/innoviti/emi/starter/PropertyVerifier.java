package com.innoviti.emi.starter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropertyVerifier implements BeanFactoryPostProcessor, PriorityOrdered{

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    Environment enviroment = beanFactory.getBean(Environment.class);
    String dbDriver = enviroment.getProperty("db.driver");
    if(dbDriver == null || dbDriver.isEmpty()){
      throw new ApplicationContextException("Missing property = 'db.driver'");
    }
  
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

}
