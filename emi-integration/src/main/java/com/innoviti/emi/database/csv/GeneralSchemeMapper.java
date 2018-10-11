package com.innoviti.emi.database.csv;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.repository.core.ModelRepository;
import com.innoviti.emi.repository.core.ProductRepository;
import com.innoviti.emi.service.core.DefaultDataService;


@Component
public class GeneralSchemeMapper implements ColumnMapper<SchemeModelTerminal, Deque<CSVSchemeModelTerminalModel>>{
  
  @Autowired
  private DatabaseToCsvItemProcessor databaseToCsvItemProcessor;
  
  @Autowired
  private ModelRepository modelRespository;
  
  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private DefaultDataService defaultDataService;

  @Override
  public Deque<CSVSchemeModelTerminalModel> mapColumn(SchemeModelTerminal t) {
     String innoGeneralModelCode = t.getSchemeModelTerminalComposite().getSchemeModel().getModel().getModelComposite().getInnoModelCode();
     Model defaultGeneralModel = defaultDataService.getDefaultGeneralSchemeModel();
     String defaultGeneralModelCode = defaultGeneralModel.getModelComposite().getInnoModelCode();
     
     if(!defaultGeneralModelCode.equals(innoGeneralModelCode)){
       return null;
     }
     Deque<CSVSchemeModelTerminalModel> terminalModels = new ArrayDeque<>();
     Pageable topOne = new PageRequest(0, 1);
     List<Product> products = productRepository.findByProductCodeNotDefault("REMI", "XXXXXXXXXXXXX", topOne);
     
     if(products == null || products.isEmpty()){
       return null;
     }
 
     List<Model> models = modelRespository.findByProductCode(products.get(0).getProductComposite().getProductCode()); 
    if(models == null || models.isEmpty()){
      return null;
    }
    models.add(defaultDataService.getDefaultModel());
    SchemeModel schemeModel  = t.getSchemeModelTerminalComposite().getSchemeModel();
    Scheme scheme = schemeModel.getScheme();
    IssuerBank issuerBank = scheme.getIssuerBank();
    Tenure tenure = scheme.getTenure();
    for(Model model : models){
      CSVSchemeModelTerminalModel csvModel = databaseToCsvItemProcessor.createCSVData(scheme, model, issuerBank, tenure, schemeModel, t);
      terminalModels.add(csvModel);
    }
    return terminalModels;
  }
  
}
