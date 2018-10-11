package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.ModelMaster;

@Component
public class ModelMasterWriter extends JpaItemWriter<ModelMaster> {

  @Autowired
  public ModelMasterWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);  
  }

  @Override
  public void write(List<? extends ModelMaster> items) {
    super.write(items);
  }
}
