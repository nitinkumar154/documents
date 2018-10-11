package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.DealerManufacturerMaster;

@Component
public class DealerManufacturerWriter extends JpaItemWriter<DealerManufacturerMaster> {
  
  @Autowired
  public DealerManufacturerWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
    
  }
  @Override
  public void write(List<? extends DealerManufacturerMaster> items) {
    super.write(items);
  }
}
