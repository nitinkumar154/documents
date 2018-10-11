package com.innoviti.emi.job.item.reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.orm.JpaQueryProvider;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class JpaPagingPartitionerItemReader<T> extends AbstractPagingItemReader<T> {

  private static final Logger log = LoggerFactory.getLogger(JpaPagingPartitionerItemReader.class);

  private EntityManagerFactory entityManagerFactory;

  private EntityManager entityManager;

  private final Map<String, Object> jpaPropertyMap = new HashMap<String, Object>();

  private String queryString;

  private JpaQueryProvider queryProvider;

  private Map<String, Object> parameterValues;

  private boolean transacted = true;// default value

  private int gridSize;
  
  private int chunkStartIndex;

  private int rangeIndex;

  public JpaPagingPartitionerItemReader() {
    setName(ClassUtils.getShortName(JpaPagingPartitionerItemReader.class));
  }

  /**
   * Create a query using an appropriate query provider (entityManager OR queryProvider).
   */
  private Query createQuery() {
    if (queryProvider == null) {
      return entityManager.createQuery(queryString);
    } else {
      return queryProvider.createQuery();
    }
  }

  public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  /**
   * By default (true) the EntityTransaction will be started and committed around the read. Can be
   * overridden (false) in cases where the JPA implementation doesn't support a particular
   * transaction. (e.g. Hibernate with a JTA transaction). NOTE: may cause problems in guaranteeing
   * the object consistency in the EntityManagerFactory.
   * 
   * @param transacted
   */
  public void setTransacted(boolean transacted) {
    this.transacted = transacted;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void afterPropertiesSet() throws Exception {
    super.afterPropertiesSet();

    if (queryProvider == null) {
      Assert.notNull(entityManagerFactory);
      Assert.hasLength(queryString);
    }
    // making sure that the appropriate (JPA) query provider is set
    else {
      Assert.isTrue(queryProvider != null, "JPA query provider must be set");
    }
  }

  /**
   * @param queryString JPQL query string
   */
  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  /**
   * @param queryProvider JPA query provider
   */
  public void setQueryProvider(JpaQueryProvider queryProvider) {
    this.queryProvider = queryProvider;
  }

  @Override
  protected void doOpen() throws Exception {
    super.doOpen();

    entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
    if (entityManager == null) {
      throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
    }
    // set entityManager to queryProvider, so it participates
    // in JpaPagingItemReader's managed transaction
    if (queryProvider != null) {
      queryProvider.setEntityManager(entityManager);
    }

  }

  public void setParameterValues(Map<String, Object> parameterValues) {
    this.parameterValues = parameterValues;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void doReadPage() {

    EntityTransaction tx = null;


    if (transacted) {
      tx = entityManager.getTransaction();
      tx.begin();

      entityManager.flush();
      entityManager.clear();
    }
    log.info("Item count : {} Current range index : {}", getCurrentItemCount(),
        rangeIndex);
   
    Query query = createQuery().setFirstResult(rangeIndex).setMaxResults(getPageSize());

    if (parameterValues != null) {
      for (Map.Entry<String, Object> me : parameterValues.entrySet()) {
        query.setParameter(me.getKey(), me.getValue());
      }
    }

    if (results == null) {
      results = new CopyOnWriteArrayList<T>();
    } else {
      results.clear();
    }

    if (!transacted) {
      List<T> queryResult = query.getResultList();
      for (T entity : queryResult) {
        entityManager.detach(entity);
        results.add(entity);
      } // end if
    } else {
      results.addAll(query.getResultList());
      tx.commit();
    } // end if
    rangeIndex = (getPageSize() * getGridSize()) + rangeIndex;
  }

  @Override
  protected void doJumpToPage(int itemIndex) {
    // TODO Auto-generated method stub

  }
  
  @Override
  protected void doClose() throws Exception {
    entityManager.close();
    super.doClose();
  }

  public int getChunkStartIndex() {
    return chunkStartIndex;
  }

  public void setChunkStartIndex(int chunkStartIndex) {
    this.chunkStartIndex = chunkStartIndex;
    this.rangeIndex = chunkStartIndex;
  }

  public int getGridSize() {
    return gridSize;
  }

  public void setGridSize(int gridSize) {
    this.gridSize = gridSize;
  }
  
}
