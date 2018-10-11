package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.DealerManufacturerMaster;
import com.innoviti.emi.entity.master.DealerManufacturerMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.DealerManufacturerMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.DealerManufacturerMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class DealerManufacturerMasterServiceImpl extends CrudServiceHelper<DealerManufacturerMaster, DealerManufacturerMasterComposite>
    implements DealerManufacturerMasterService {

  public DealerManufacturerMasterServiceImpl(DealerManufacturerMasterRepository dealerManufacturerRepository) {
    super(dealerManufacturerRepository);
  }

  @Value("${store.upload.url}")
  private String bajajEmiFile;

  @Override
  public DealerManufacturerMaster create(DealerManufacturerMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(DealerManufacturerMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public DealerManufacturerMaster findById(DealerManufacturerMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public DealerManufacturerMaster update(DealerManufacturerMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide Dealer manufacturer info to update", 400);
    }
    return null;
  }

  @Override
  public List<DealerManufacturerMaster> findAll() {
    return helperFindAll();
  }

}
