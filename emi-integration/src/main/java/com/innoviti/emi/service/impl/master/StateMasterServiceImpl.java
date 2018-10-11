package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.StateMaster;
import com.innoviti.emi.entity.master.StateMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.StateMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.StateMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class StateMasterServiceImpl extends CrudServiceHelper<StateMaster, StateMasterComposite>
    implements StateMasterService {

  @Value("${store.upload.url}")
  private String bajajEmiFile;

  @Autowired
  public StateMasterServiceImpl(StateMasterRepository stateRespository) {
    super(stateRespository);
  }
  
  @Override
  public StateMaster create(StateMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(StateMasterComposite id) {
    helperDeleteById(id);

  }

  @Override
  public StateMaster findById(StateMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public StateMaster update(StateMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide branch info to update", 400);
    }
    return null;
  }

  @Override
  public List<StateMaster> findAll() {
    return helperFindAll();
  }

}
