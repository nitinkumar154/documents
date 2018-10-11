package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.DealerMaster;

@Component
public class DealerMasterWriter extends JpaItemWriter<DealerMaster> {

  @Autowired
  public DealerMasterWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends DealerMaster> items) {
    super.write(items);
  }
}
