package com.innoviti.emi.job.item.processor;

import java.util.Date;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelComposite;
import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.repository.core.SchemeModelRepository;
import com.innoviti.emi.repository.core.SchemeRepository;
import com.innoviti.emi.service.core.DefaultDataService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;

@Component
@StepScope
public class GeneralSchemeModelItemProcessor implements ItemProcessor<SchemeMaster, SchemeModel>{

  @Autowired
  private SequenceGeneratorService sequenceGeneratorService;
  
  @Autowired
  private SchemeRepository schemeRepository;
  
  @Autowired
  private DefaultDataService defaultDataService;
  
  @Autowired
  private SchemeModelRepository schemeModelRepository;
  
  @Override
  public SchemeModel process(SchemeMaster schemeMaster) throws Exception {
    
    Integer schemeId = schemeMaster.getSchemeMasterComposite().getSchemeId();
    Pageable topOne = new PageRequest(0, 1);
    List<Scheme> schemeList = schemeRepository.findLatestSchemeByBajajSchemeCode(String.valueOf(schemeId), topOne);
    if(schemeList == null || schemeList.isEmpty()){
      return null;
    }
    Scheme scheme = schemeList.get(0);
    String innoSchemeCode = scheme.getSchemeComposite().getInnoIssuerSchemeCode();
    Model generalModelCode = defaultDataService.getDefaultGeneralSchemeModel();
    String defaultGeneralSchemeModelCode = generalModelCode.getModelComposite().getInnoModelCode();
    
    List<SchemeModel> foundSchemeModels = schemeModelRepository.getSchemeModelBySchemeAndModelCode(innoSchemeCode, defaultGeneralSchemeModelCode);
    String innoSchemeModelCode = null;
    if(foundSchemeModels != null && !foundSchemeModels.isEmpty()){
      innoSchemeModelCode = foundSchemeModels.get(0).getSchemeModelComposite().getInnoSchemeModelCode();
    }
    else{
      SequenceType sequenceType = SequenceType.SCHEME_MODEL;
      int seqNum = (int)sequenceGeneratorService.getSeqNumber(sequenceType.getSequenceName());
      String newInsertId = String.valueOf(seqNum);
      innoSchemeModelCode = sequenceType.getSequenceIdentifier() + Util.getLeftPaddedValueZeroFilled(newInsertId, 15);
    }
    SchemeModel schemeModel = new SchemeModel();
    SchemeModelComposite schemeModelComposite = new SchemeModelComposite();
    schemeModelComposite.setCrtupdDate(new Date());
    schemeModelComposite.setInnoSchemeModelCode(innoSchemeModelCode);
    schemeModel.setSchemeModelComposite(schemeModelComposite);
    schemeModel.setScheme(scheme);
    schemeModel.setModel(generalModelCode);
    schemeModel.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    schemeModel.setCrtupdStatus("N");
    schemeModel.setCrtupdUser("Batch");
    schemeModel.setRecordActive(true);
    return schemeModel;
  }
  
  

}
