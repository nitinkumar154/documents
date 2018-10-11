package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.DealerProductMaster;

@Component
public class DealerProductMasterWriter extends JpaItemWriter<DealerProductMaster> {

  @Autowired
  public DealerProductMasterWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends DealerProductMaster> items) {
    super.write(items);
  }
}
