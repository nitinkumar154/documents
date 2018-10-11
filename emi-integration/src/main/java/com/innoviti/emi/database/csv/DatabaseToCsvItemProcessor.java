package com.innoviti.emi.database.csv;

import java.text.SimpleDateFormat;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.core.Tenure;

@Component
@StepScope
public class DatabaseToCsvItemProcessor implements ItemProcessor<SchemeModelTerminal, CSVSchemeModelTerminalModel>{
  
  @Override
  public CSVSchemeModelTerminalModel process(SchemeModelTerminal item) throws Exception {
   
    SchemeModel schemeModel = item.getSchemeModelTerminalComposite().getSchemeModel();
   
    Scheme scheme = schemeModel.getScheme();
    Model model = schemeModel.getModel();
    if("XXXXXXXXXXGEN".equals(model.getModelComposite().getInnoModelCode())){
      return null;
    }
   
    IssuerBank issuerBank = scheme.getIssuerBank();
    Tenure tenure = scheme.getTenure();
   return createCSVData(scheme, model, issuerBank, tenure, schemeModel, item);
  }
  public CSVSchemeModelTerminalModel createCSVData(Scheme scheme, Model model, IssuerBank issuerBank, 
      Tenure tenure, SchemeModel schemeModel, SchemeModelTerminal item){
    String bajajProductTypeCode = item.getBajajProductTypeCode();
    String dealerId = item.getDealerId();
    String issuerCustomField = item.getIssuerCustomField();
    String issuerSchemeTerminalSyncStatus = item.getIssuerSchemeTerminalSyncStatus().toString();
    String onUsOffUs = item.getOnUsOffUs().toString();
    String utid = item.getSchemeModelTerminalComposite().getUtid();
   
    Category category = model.getCategory();
    Manufacturer manufacturer = model.getManufacturer();
    
    CSVSchemeModelTerminalModel csvModel = new CSVSchemeModelTerminalModel();
    csvModel.setAdvanceEmi(scheme.getAdvanceEmi());
    csvModel.setBajajIssuerSchemeCode(scheme.getBajajIssuerSchemeCode());
    csvModel.setBajajManufacturerCode(manufacturer.getBajajManufacturerCode());
    csvModel.setBajajProductTypeCode(bajajProductTypeCode);
    csvModel.setCategoryDisplayName(category.getCategoryDisplayName());
    csvModel.setDealerId(dealerId);
    csvModel.setEmiBankCode(issuerBank.getEmiBankCode());
    csvModel.setGenScheme(scheme.getGenScheme());
    csvModel.setInnoCategoryCode(category.getCategoryComposite().getInnoCategoryCode());
    csvModel.setInnoIssuerBankCode(issuerBank.getIssuerBankComposite().getInnoIssuerBankCode());
    csvModel.setInnoIssuerSchemeCode(scheme.getSchemeComposite().getInnoIssuerSchemeCode());
    csvModel.setInnoManufacturerCode(manufacturer.getManufacturerComposite().getInnoManufacturerCode());
    csvModel.setInnoModelCode(model.getModelComposite().getInnoModelCode());
    //csvModel.setInnoModelSerialNumber(model.);
    csvModel.setInnoSchemeModelCode(schemeModel.getSchemeModelComposite().getInnoSchemeModelCode());
    csvModel.setInnovitiSubvention(scheme.getInnovitiSubvention());
    csvModel.setIssuerBankDisplayName(issuerBank.getIssuerBankDisplayName());
    csvModel.setIssuerCustomField(issuerCustomField);
    csvModel.setIssuerSchemeTerminalSyncStatus(issuerSchemeTerminalSyncStatus);
    csvModel.setManufacturerDisplayName(manufacturer.getManufacturerDisplayName());
    csvModel.setMaxAmount(scheme.getMaxAmount().doubleValue());
    csvModel.setMinAmount(scheme.getMinAmount().doubleValue());
    csvModel.setModelDisplayNo(model.getModelDisplayNo());
    csvModel.setOnUsOffUs(onUsOffUs);
    csvModel.setRoi(scheme.getRoi());
    csvModel.setSchemeDisplayName(scheme.getSchemeDisplayName());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    csvModel.setSchemeEndDate(sdf.format(scheme.getSchemeEndDate()));
    csvModel.setSchemeStartDate(sdf.format(scheme.getSchemeStartDate()));
    csvModel.setTenureCode(tenure.getTenureComposite().getInnoTenureCode());
    csvModel.setTenureDisplayName(tenure.getTenureDisplayName());
    csvModel.setTenureMonth(tenure.getTenureMonth());
    csvModel.setUtid(utid);
    return csvModel;
  }
}
