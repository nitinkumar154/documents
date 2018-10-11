package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.SchemeDealerMaster;
import com.innoviti.emi.entity.master.SchemeDealerMasterComposite;
import com.innoviti.emi.repository.master.SchemeDealerMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.SchemeDealerMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class SchemeDealerMasterServiceImpl
    extends CrudServiceHelper<SchemeDealerMaster, SchemeDealerMasterComposite>
    implements SchemeDealerMasterService {

  @Autowired
  public SchemeDealerMasterServiceImpl(SchemeDealerMasterRepository baseRespository) {
    super(baseRespository);
  }

  @Value("${store.upload.url}")
  private String bajajEmiFile;


  @Override
  public SchemeDealerMaster create(SchemeDealerMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(SchemeDealerMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public SchemeDealerMaster findById(SchemeDealerMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public SchemeDealerMaster update(SchemeDealerMaster u) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<SchemeDealerMaster> findAll() {
    return helperFindAll();
  }

}
