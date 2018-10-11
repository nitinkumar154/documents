package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.SchemeModelMaster;
import com.innoviti.emi.entity.master.SchemeModelMasterComposite;
import com.innoviti.emi.repository.master.SchemeModelMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.SchemeModelMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class SchemeModelMasterServiceImpl extends CrudServiceHelper<SchemeModelMaster, SchemeModelMasterComposite>
    implements SchemeModelMasterService {

  @Autowired
  public SchemeModelMasterServiceImpl(SchemeModelMasterRepository baseRespository) {
    super(baseRespository);
  }

  @Value("${store.upload.url}")
  private String bajajEmiFile;

  @Override
  public SchemeModelMaster create(SchemeModelMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(SchemeModelMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public SchemeModelMaster findById(SchemeModelMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public SchemeModelMaster update(SchemeModelMaster u) {
    return null;
  }

  @Override
  public List<SchemeModelMaster> findAll() {
    return helperFindAll();
  }

}
