package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.ManufacturerMaster;
import com.innoviti.emi.entity.master.ManufacturerMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.ManufacturerMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.ManufacturerMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class ManufacturerMasterServiceImpl extends CrudServiceHelper<ManufacturerMaster, ManufacturerMasterComposite>
    implements ManufacturerMasterService {

  public ManufacturerMasterServiceImpl(ManufacturerMasterRepository manufacturerRepository) {
    super(manufacturerRepository);
  }

  @Value("${store.upload.url}")
  private String bajajEmiFile;

  @Override
  public ManufacturerMaster create(ManufacturerMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(ManufacturerMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public ManufacturerMaster findById(ManufacturerMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public ManufacturerMaster update(ManufacturerMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide manufacturer info to update", 400);
    }
    return null;
  }

  @Override
  public List<ManufacturerMaster> findAll() {
    return helperFindAll();
  }

}
