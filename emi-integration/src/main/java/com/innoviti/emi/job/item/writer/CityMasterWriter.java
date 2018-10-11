package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.CityMaster;

@Component
public class CityMasterWriter extends JpaItemWriter<CityMaster> {

  @Autowired
  public CityMasterWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends CityMaster> items) {
    super.write(items);
  }
}
