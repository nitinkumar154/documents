package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.core.Product;

@Component
public class ProductItemWriter extends JpaItemWriter<Product> {

  public ProductItemWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends Product> items) {
    super.write(items);
  }

}
