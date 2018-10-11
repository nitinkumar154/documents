package com.innoviti.emi.service.core;

import java.util.List;

import com.innoviti.emi.entity.core.ModelView;

public interface ModelViewService {
  
  List<ModelView> findAllModelNames(String term);
  
}
