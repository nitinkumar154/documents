package com.innoviti.emi.setup;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.persistence.EntityManager;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.innoviti.emi.util.TruncateTablesService;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EmiServiceStarterTest.class)
public class SetupWithRestDoc {

  @Autowired
  private WebApplicationContext context;
  
  private MockMvc mockMvc;
  
  @Rule
  public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");
  
  @Autowired
  private EntityManager entityManager;
  
  //@Before
  public void setup() throws Exception{
    this.mockMvc = webAppContextSetup(this.context)
        .apply(documentationConfiguration(restDocumentation)).build();
    
    TruncateTablesService truncateTable = new TruncateTablesService(entityManager);
    truncateTable.truncateAll();;
  }
  @Test
  public void test(){
    
  }
  protected MockMvc getMockMvc(){
    return mockMvc;
  }
}
