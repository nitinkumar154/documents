package com.innoviti.emi.starter;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.repository.core.ModelViewRepository;
import com.innoviti.emi.util.Util;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent>{
  private static Logger logger = LoggerFactory.getLogger(StartupListener.class);
  
  @Autowired
  private ModelViewRepository modelViewRepository;
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @Transactional(transactionManager = "transactionManager")
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    logger.debug("Application context initialized or refreshed");
    modelViewRepository.createModelView();
    try {
      FullTextEntityManager fullTextEntityManager =
        Search.getFullTextEntityManager(entityManager);
      fullTextEntityManager.createIndexer().startAndWait();
    }
    catch (InterruptedException e) {
      System.out.println(
        "An error occurred trying to build the serach index: " +
         e.toString());
    }
    return;
  }

  @PostConstruct
  public void setupDefaultData() {
    entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();
    Session session = entityManager.unwrap(Session.class);
    session.doWork(new Work() {
      @Override
      public void execute(Connection connection) throws SQLException {
        Statement s = null;
        try {
          s = connection.createStatement();
          ScriptUtils.executeSqlScript(connection, new ClassPathResource("data-mysql.sql"));
        } finally {
          entityManager.getTransaction().commit();
          if (s != null) {
            s.close();
          }
        }
      }

    });
  }
  @PostConstruct
  public void createDirectories(){
    Path tempFolder = Util.createDirectories(Util.TEMP_FOLDER);
    logger.info("Folder {} created", tempFolder.getFileName());
    Path emiMappingFolder = Util.createDirectories(Util.EMI_FOLDER);
    logger.info("Folder {} created", emiMappingFolder.getFileName());
  }
}
