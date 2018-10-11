package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.entity.master.SchemeMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.SchemeMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.SchemeMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class SchemeMasterServiceImpl extends CrudServiceHelper<SchemeMaster, SchemeMasterComposite>
    implements SchemeMasterService {

  public SchemeMasterServiceImpl(SchemeMasterRepository baseRespository) {
    super(baseRespository);
  }

  @Override
  public SchemeMaster create(SchemeMaster u) {
    return helperCreate(u);
  }

  @Override
  public SchemeMaster update(SchemeMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide scheme info to update", 400);
    }
    return null;
  }

  @Override
  public List<SchemeMaster> findAll() {
    return helperFindAll();
  }

  @Override
  public void deleteById(SchemeMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public SchemeMaster findById(SchemeMasterComposite id) {
    return helperFindById(id);
  }


}
