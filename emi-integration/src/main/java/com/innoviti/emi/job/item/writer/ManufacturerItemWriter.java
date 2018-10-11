package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.core.Manufacturer;

@Component
public class ManufacturerItemWriter extends JpaItemWriter<Manufacturer>{
  @Autowired
  public ManufacturerItemWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }
  @Override
  public void write(List<? extends Manufacturer> items) {
    super.write(items);
  }

}
