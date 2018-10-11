package com.innoviti.emi.setup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.innoviti.emi.starter.EmiServiceStarterTest;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmiServiceStarterTest.class)
@TestPropertySource(locations="file:config/test-config.properties")
public class SetupWithJPA {
  @Test
  public void test(){
    
  }
}
