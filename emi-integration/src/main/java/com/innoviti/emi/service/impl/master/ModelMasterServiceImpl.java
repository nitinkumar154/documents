package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.ModelMaster;
import com.innoviti.emi.entity.master.ModelMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.ModelMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.ModelMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class ModelMasterServiceImpl extends CrudServiceHelper<ModelMaster, ModelMasterComposite>
    implements ModelMasterService {

  @Autowired
  public ModelMasterServiceImpl(ModelMasterRepository baseRespository) {
    super(baseRespository);
  }

  @Value("${store.upload.url}")
  private String storeUrl;

  @Override
  public ModelMaster create(ModelMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(ModelMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public ModelMaster findById(ModelMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public ModelMaster update(ModelMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide branch info to update", 400);
    }
    return null;
  }

  @Override
  public List<ModelMaster> findAll() {
    return helperFindAll();
  }

}
