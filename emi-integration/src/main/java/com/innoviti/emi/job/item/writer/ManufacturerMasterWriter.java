package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.ManufacturerMaster;

@Component
public class ManufacturerMasterWriter extends JpaItemWriter<ManufacturerMaster> {

  @Autowired
  public ManufacturerMasterWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends ManufacturerMaster> items) {
    super.write(items);
  }
}
