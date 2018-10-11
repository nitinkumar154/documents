package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.BranchMaster;
import com.innoviti.emi.entity.master.BranchMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.BranchMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.BranchMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class BranchMasterServiceImpl extends CrudServiceHelper<BranchMaster, BranchMasterComposite>
    implements BranchMasterService {

  @Value("${store.upload.url}")
  private String bajajEmiFile;

  @Autowired
  public BranchMasterServiceImpl(BranchMasterRepository branchRespository) {
    super(branchRespository);
  }

  public BranchMaster create(BranchMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(BranchMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public BranchMaster findById(BranchMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public BranchMaster update(BranchMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide branch info to update", 400);
    }
    return null;
  }

  @Override
  public List<BranchMaster> findAll() {
    return helperFindAll();
  }


}
