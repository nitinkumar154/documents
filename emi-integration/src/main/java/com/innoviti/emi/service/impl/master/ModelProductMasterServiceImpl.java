package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.ModelProductMaster;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.BaseRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.ModelProductMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class ModelProductMasterServiceImpl extends CrudServiceHelper<ModelProductMaster, String>
    implements ModelProductMasterService {

  public ModelProductMasterServiceImpl(BaseRepository<ModelProductMaster, String> baseRespository) {
    super(baseRespository);
  }

  @Override
  public ModelProductMaster create(ModelProductMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(String id) {
    helperDeleteById(id);

  }

  @Override
  public ModelProductMaster findById(String id) {
    return helperFindById(id);
  }

  @Override
  public ModelProductMaster update(ModelProductMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide ModelProductMaster info to update", 400);
    }
    return null;
  }

  @Override
  public List<ModelProductMaster> findAll() {
    return helperFindAll();
  }

}
