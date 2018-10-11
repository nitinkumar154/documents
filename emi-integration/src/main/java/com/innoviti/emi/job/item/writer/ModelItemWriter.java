package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.core.Model;

@Component
@StepScope
public class ModelItemWriter extends JpaItemWriter<Model>{
 
 @Autowired
 public ModelItemWriter(EntityManagerFactory entityManagerFactory) {
  setEntityManagerFactory(entityManagerFactory);
 }
 @Override
 public  void write(List<? extends Model> items) {
   super.write(items);
 }
}
