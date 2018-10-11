package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.core.SchemeModel;

@Component
@StepScope
public class SchemeModelItemWriter extends JpaItemWriter<SchemeModel> {

  public SchemeModelItemWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends SchemeModel> items) {
    super.write(items);
  }

}
