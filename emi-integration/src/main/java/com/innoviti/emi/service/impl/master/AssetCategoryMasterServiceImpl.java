package com.innoviti.emi.service.impl.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.master.AssetCategoryMaster;
import com.innoviti.emi.entity.master.AssetCategoryMasterComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.repository.master.AssetCategoryMasterRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.master.AssetCategoryMasterService;

@Service
@Transactional(transactionManager = "transactionManager")
public class AssetCategoryMasterServiceImpl extends CrudServiceHelper<AssetCategoryMaster, AssetCategoryMasterComposite>
    implements AssetCategoryMasterService {

  @Autowired
  public AssetCategoryMasterServiceImpl(AssetCategoryMasterRepository assetCategoryRespository) {
    super(assetCategoryRespository);
  }

  @Value("${store.upload.url}")
  private String storeUrl;


  @Override
  public AssetCategoryMaster create(AssetCategoryMaster u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(AssetCategoryMasterComposite id) {
    helperDeleteById(id);

  }

  @Override
  public AssetCategoryMaster findById(AssetCategoryMasterComposite id) {
    return helperFindById(id);
  }

  @Override
  public AssetCategoryMaster update(AssetCategoryMaster u) {
    if (u == null) {
      throw new BadRequestException("Please provide branch info to update", 400);
    }
    return null;
  }

  @Override
  public List<AssetCategoryMaster> findAll() {
    return helperFindAll();
  }

}
