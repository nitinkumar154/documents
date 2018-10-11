package com.innoviti.emi.setup;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = EmiServiceStarter.class)
//@TestPropertySource(locations="file:config/config.properties")
public class ExportSchema{

  @Autowired
  private Environment env;
  
  @Autowired
  private EntityManager entityManager;
  
 // @Test
  public void export() throws ClassNotFoundException{

    
    Map<String, String> settings = new HashMap<String, String>();
    
    settings.put("hibernate.connection.driver_class", env.getRequiredProperty("db.driver"));
    settings.put("hibernate.connection.url", env.getRequiredProperty("db.url"));
    settings.put("hibernate.connection.username", env.getRequiredProperty("db.username"));
    settings.put("hibernate.connection.password", env.getRequiredProperty("db.password"));
    settings.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
    settings.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
    settings.put("hibernate.ejb.naming_strategy",
        env.getRequiredProperty("hibernate.ejb.naming_strategy"));
    settings.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
    settings.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
    MetadataSources metadata = new MetadataSources(
            new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build());  
    
    entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();
    Session session = entityManager.unwrap(Session.class);
    Map<String, ClassMetadata> hibernateMetadata = session.getSessionFactory().getAllClassMetadata();
    for (ClassMetadata classMetadata : hibernateMetadata.values()) {
      AbstractEntityPersister aep = (AbstractEntityPersister) classMetadata;
      metadata.addAnnotatedClass(Class.forName(aep.getRootEntityName()));
    }
    SchemaExport export = new SchemaExport((MetadataImplementor) metadata.buildMetadata());
    export.setDelimiter(";");
    export.setOutputFile("create_schema.sql");
    export.setFormat(true);
    export.execute(true, false, false, false);
  }
}
