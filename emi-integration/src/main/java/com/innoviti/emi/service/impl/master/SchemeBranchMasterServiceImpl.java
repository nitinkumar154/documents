package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.SchemeBranchMaster;
import com.innoviti.emi.entity.master.SchemeBranchMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.BaseRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.SchemeBranchMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class SchemeBranchMasterServiceImpl
    extends CrudServiceHelper<SchemeBranchMaster, SchemeBranchMasterComposite>
    implements SchemeBranchMasterService {

  @Value("${store.upload.url}")
  private String storeUrl;

  public SchemeBranchMasterServiceImpl(
      BaseRepository<SchemeBranchMaster, SchemeBranchMasterComposite> baseRespository) {
    super(baseRespository);
  }

  @Override
  public SchemeBranchMaster create(SchemeBranchMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(SchemeBranchMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public SchemeBranchMaster findById(SchemeBranchMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public SchemeBranchMaster update(SchemeBranchMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide schemeBranchMaster info to update", 400);
    }

    return null;
  }

  @Override
  public List<SchemeBranchMaster> findAll() {
    return helperFindAll();
  }

}
