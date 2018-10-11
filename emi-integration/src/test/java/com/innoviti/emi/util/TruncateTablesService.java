package com.innoviti.emi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

public class TruncateTablesService {
private EntityManager entityManager;
  
  public TruncateTablesService(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void truncateAll(){
    List<String> tableNames = new ArrayList<>();
    entityManager = entityManager.getEntityManagerFactory().createEntityManager();
    entityManager.getTransaction().begin();
    Session session = entityManager.unwrap(Session.class);
    Map<String, ClassMetadata> hibernateMetadata = session.getSessionFactory().getAllClassMetadata();
    for (ClassMetadata classMetadata : hibernateMetadata.values()) {
      AbstractEntityPersister aep = (AbstractEntityPersister) classMetadata;
      if(aep.getTableName().contains("model_name"))
        continue;
      tableNames.add(aep.getTableName());
      System.out.println("table names : "+aep.getTableName());
    }
    // disable foreign keys
    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
    tableNames.forEach(tableName -> entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate());
 // reenable foreign keys 
    entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate(); 
    entityManager.getTransaction().commit();
    session.close();
    
  }
  
}
