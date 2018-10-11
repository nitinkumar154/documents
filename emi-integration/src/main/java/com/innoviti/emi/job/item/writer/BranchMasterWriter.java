package com.innoviti.emi.job.item.writer;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.master.BranchMaster;

@Component
public class BranchMasterWriter extends JpaItemWriter<BranchMaster> {

  @Autowired
  public BranchMasterWriter(EntityManagerFactory entityManagerFactory) {
    setEntityManagerFactory(entityManagerFactory);
  }

  @Override
  public void write(List<? extends BranchMaster> items) {
    super.write(items);
  }
}
