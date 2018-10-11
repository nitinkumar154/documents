package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.StateMaster;

@Component
public class StateMasterWriter extends JpaItemWriter<StateMaster> {

  @Autowired
  public StateMasterWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends StateMaster> items) {
    /*
     * for (SchemeMaster schemeMaster : items) { System.out.println("writer : " +
     * schemeMaster.getSchemeId()); }
     */
    super.write(items);
  }
}
