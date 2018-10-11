package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.core.SchemeModelTerminal;

@Component
@StepScope
public class SchemeModelTerminalItemWriter extends JpaItemWriter<SchemeModelTerminal>{

  @Autowired
  public SchemeModelTerminalItemWriter(EntityManagerFactory entityManagerFactory) {
   setEntityManagerFactory(entityManagerFactory);
  }
  @Override
  public void write(List<? extends SchemeModelTerminal> items) {
    super.write(items);
  }
}
