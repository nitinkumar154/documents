package com.innoviti.emi.service.core;

import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;

public interface DefaultDataService {
  Category geDefaultCategory();
  Manufacturer getDefaultManufacturer();
  Product getDefaultProduct();
  Model getDefaultModel();
  Scheme getDefaultScheme();
  SchemeModel getDefaultSchemeModel();
  Model getDefaultGeneralSchemeModel();
}
