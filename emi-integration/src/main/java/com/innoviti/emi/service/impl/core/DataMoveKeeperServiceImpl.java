package com.innoviti.emi.service.impl.core;

import java.util.List;

import org.springframework.stereotype.Service;

import com.innoviti.emi.entity.core.DataMoveKeeper;
import com.innoviti.emi.repository.BaseRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.DataMoveKeeperService;

@Service
public class DataMoveKeeperServiceImpl extends CrudServiceHelper<DataMoveKeeper, String>
    implements DataMoveKeeperService {

  public DataMoveKeeperServiceImpl(BaseRepository<DataMoveKeeper, String> baseRespository) {
    super(baseRespository);
  }

  @Override
  public DataMoveKeeper create(DataMoveKeeper u) {
    return helperCreate(u);
  }

  @Override
  public void deleteById(String id) {
    helperDeleteById(id);
  }

  @Override
  public DataMoveKeeper findById(String id) {
    return helperFindById(id);
  }

  @Override
  public DataMoveKeeper update(DataMoveKeeper u) {
    return helperUpdate(u);
  }

  @Override
  public List<DataMoveKeeper> findAll() {
    return helperFindAll();
  }

}
