package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.DealerProductMaster;
import com.innoviti.emi.entity.master.DealerProductMasterComposite;
import com.innoviti.emi.repository.master.DealerProductMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.DealerProductMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class DealerProductMasterServiceImpl
    extends CrudServiceHelper<DealerProductMaster, DealerProductMasterComposite>
    implements DealerProductMasterService {


  @Autowired
  public DealerProductMasterServiceImpl(DealerProductMasterRepository baseRespository) {
    super(baseRespository);
  }

  @Value("${store.upload.url}")
  private String bajajEmiFile;

  @Override
  public DealerProductMaster create(DealerProductMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(DealerProductMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public DealerProductMaster findById(DealerProductMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public DealerProductMaster update(DealerProductMaster u) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<DealerProductMaster> findAll() {
    return helperFindAll();
  }

}
