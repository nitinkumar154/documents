package com.innoviti.emi.job.item.reader;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.InitializingBean;

public abstract class BaseJpaItemReader<T> implements ItemStream, InitializingBean{
  private Logger logger = LoggerFactory.getLogger(BaseJpaItemReader.class);
  private EntityManagerFactory entityManagerFactory;
  private String queryString;
  private int gridSize;
  private int chunkStartIndex;
  private int pageSize;
  private JpaPagingPartitionerItemReader<T> jpaPagingPartitionerItemReader;
 
  public BaseJpaItemReader(JpaPagingPartitionerItemReader<T> jpaPagingPartitionerItemReader) {
    this.jpaPagingPartitionerItemReader = jpaPagingPartitionerItemReader;
  }
  public EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

  public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
    this.jpaPagingPartitionerItemReader.setEntityManagerFactory(entityManagerFactory);
  }

  public String getQueryString() {
    return queryString;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
    this.jpaPagingPartitionerItemReader.setQueryString(queryString);
  }

  public int getGridSize() {
    return gridSize;
  }

  public void setGridSize(int gridSize) {
    this.gridSize = gridSize;
    this.jpaPagingPartitionerItemReader.setGridSize(gridSize);
  }

  public int getChunkStartIndex() {
    return chunkStartIndex;
  }

  public void setChunkStartIndex(int chunkStartIndex) {
    this.chunkStartIndex = chunkStartIndex;
    this.jpaPagingPartitionerItemReader.setChunkStartIndex(chunkStartIndex);
  }
  public int getPageSize() {
    return pageSize;
  }
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
    this.jpaPagingPartitionerItemReader.setPageSize(pageSize);
  }
  public JpaPagingPartitionerItemReader<T> getJpaPagingPartitionerItemReader() {
    return this.jpaPagingPartitionerItemReader;
  }
  
  @Override
  public void afterPropertiesSet() throws Exception {
    this.jpaPagingPartitionerItemReader.afterPropertiesSet();
    
  }
  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException{
   try {
    this.jpaPagingPartitionerItemReader.open(executionContext);
   } catch (Exception e) {
     logger.error("", e);
     throw new RuntimeException(e.getMessage());
   }
  }
  @Override
  public void close() {
    try {
      this.jpaPagingPartitionerItemReader.close();
    } catch (Exception e) {
      logger.error("", e);
    }
    
  }
  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    this.jpaPagingPartitionerItemReader.update(executionContext);
  } 
}
