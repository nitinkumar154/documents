package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.CityMaster;
import com.innoviti.emi.entity.master.CityMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.CityMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.CityMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class CityMasterServiceImpl extends CrudServiceHelper<CityMaster, CityMasterComposite>
    implements CityMasterService {

  @Value("${store.upload.url}")
  private String bajajEmiFile;

  @Autowired
  public CityMasterServiceImpl(CityMasterRepository cityRespository) {
    super(cityRespository);
  }

  @Override
  public CityMaster create(CityMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(CityMasterComposite id) {
    helperDeleteById(id);
  }

  @Override
  public CityMaster findById(CityMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public CityMaster update(CityMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide branch info to update", 400);
    }
    return null;
  }

  @Override
  public List<CityMaster> findAll() {
    return helperFindAll();
  }

}
