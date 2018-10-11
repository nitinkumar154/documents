package com.innoviti.emi.job.item.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.column.mapper.impl.ModelProductMasterColumnMapper;
import com.innoviti.emi.entity.core.Product;

@Component
public class ProductItemProcessor implements ItemProcessor<String, Product> {

  @Autowired
  private ModelProductMasterColumnMapper modelProductMasterColumnMapper;

  @Override
  public Product process(String item) throws Exception {
    return modelProductMasterColumnMapper.mapColumn(item);
  }

}
