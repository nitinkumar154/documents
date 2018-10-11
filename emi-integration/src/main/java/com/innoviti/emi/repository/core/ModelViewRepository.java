package com.innoviti.emi.repository.core;

import java.util.List;

import com.innoviti.emi.entity.core.ModelView;
import com.innoviti.emi.repository.BaseRepository;

public interface ModelViewRepository
    extends BaseRepository<ModelView, String>, ModelViewRepositoryCustom {

  public List<ModelView> findTop20ByInnoModelDisplayNameIgnoreCaseContainingOrderByInnoModelDisplayNameAsc(
      String term);
}
