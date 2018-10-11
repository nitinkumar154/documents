package com.innoviti.emi.service.impl.core;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.entity.core.ModelView;
import com.innoviti.emi.repository.core.ModelViewRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.ModelViewService;

@Service
@Transactional(transactionManager = "transactionManager")
public class ModelViewServiceImpl extends CrudServiceHelper<ModelView, String>
    implements ModelViewService {

  private ModelViewRepository modelViewRepository;

  public ModelViewServiceImpl(ModelViewRepository modelViewRepository) {
    super(modelViewRepository);
    this.modelViewRepository = modelViewRepository;
  }

  @Override
  public List<ModelView> findAllModelNames(String term) {
    List<ModelView> modelViewNameList = modelViewRepository
        .findTop20ByInnoModelDisplayNameIgnoreCaseContainingOrderByInnoModelDisplayNameAsc(term);

    if (!modelViewNameList.iterator().hasNext())
      return modelViewNameList;

    return modelViewNameList;
  }

}
