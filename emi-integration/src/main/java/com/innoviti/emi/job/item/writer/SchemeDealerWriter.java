package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.SchemeDealerMaster;

@Component
public class SchemeDealerWriter extends JpaItemWriter<SchemeDealerMaster> {

  @Autowired
  public SchemeDealerWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends SchemeDealerMaster> items) {
    super.write(items);
  }
}
