package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

public class MultipleEntityJpaWriter<T> extends JpaItemWriter<List<T>> {
  
  private static final Logger log = LoggerFactory.getLogger(MultipleEntityJpaWriter.class);
  private EntityManagerFactory entityManagerFactory;

  public MultipleEntityJpaWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
    this.entityManagerFactory = entityManagerFactory;
  }
  
  @Override
  public void write(List<? extends List<T>> items) {
    EntityManager entityManager =
        EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
    if (entityManager == null) {
      throw new DataAccessResourceFailureException(
          "Unable to obtain a transactional EntityManager");
    }
    for (List<T> item : items) {
      persist(entityManager, item);
    }
    entityManager.flush();
  }
  protected void persist(EntityManager entityManager, List<T> objects) {
    if (logger.isDebugEnabled()) {
      logger.debug("Writing to JPA with " + objects.size() + " items.");
    }

    if (!objects.isEmpty()) {
      long mergeCount = 0;
      for (T item : objects) {
        if (!entityManager.contains(item)) {
          entityManager.merge(item);
          mergeCount++;
        }
      }
      log.debug("{} entities merged. ", mergeCount);
      if (logger.isDebugEnabled()) {
        logger.debug(mergeCount + " entities merged.");
        logger.debug((objects.size() - mergeCount) + " entities found in persistence context.");
      }
    }
  }
}
