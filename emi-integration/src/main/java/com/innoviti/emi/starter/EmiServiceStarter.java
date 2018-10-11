package com.innoviti.emi.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableResourceServer
//@EnableAutoConfiguration(exclude = {BatchAutoConfiguration.class})
@ComponentScan("com.innoviti")
public class EmiServiceStarter {
  public static void main(String[] args) {
    System.setProperty("spring.config.name", "config");
    SpringApplication.run(EmiServiceStarter.class, args);
  }
  
}
