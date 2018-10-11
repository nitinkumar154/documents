package com.innoviti.emi.service;

import java.io.Serializable;
import java.util.List;

public interface CrudService<T, ID extends Serializable> {
  T create(T u);

  void deleteById(ID id);

  T findById(ID id);

  T update(T u);

  List<T> findAll();
}
