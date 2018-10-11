package com.innoviti.emi.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.innoviti.emi.configuration", "com.innoviti.emi.core",
    "com.innoviti.emi.entity", "com.innoviti.emi.exception", "com.innoviti.emi.job", "com.innoviti.emi.master",
    "com.innoviti.emi.service", "com.innoviti.emi.integration", "com.innoviti.emi.model", "com.innoviti.emi.database"})
public class EmiServiceStarterTest {
  
  public static void main(String[] args) {
    SpringApplication.run(EmiServiceStarterTest.class, args);
  }
}
