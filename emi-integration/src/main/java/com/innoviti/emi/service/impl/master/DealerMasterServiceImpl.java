package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.DealerMaster;
import com.innoviti.emi.entity.master.DealerMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.DealerMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.DealerMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class DealerMasterServiceImpl extends CrudServiceHelper<DealerMaster, DealerMasterComposite>
    implements DealerMasterService {


  @Value("${store.upload.url}")
  private String bajajEmiFile;


  @Autowired
  public DealerMasterServiceImpl(DealerMasterRepository dealerRespository) {
    super(dealerRespository);
  }

  @Override
  public DealerMaster create(DealerMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(DealerMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public DealerMaster findById(DealerMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public DealerMaster update(DealerMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide branch info to update", 400);
    }
    return null;
  }

  @Override
  public List<DealerMaster> findAll() {
    return helperFindAll();
  }

}
