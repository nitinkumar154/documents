package com.innoviti.emi.entity.column.mapper.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelComposite;
import com.innoviti.emi.entity.master.SchemeDealerMaster;
import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.entity.master.SchemeModelMaster;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.ModelRepository;
import com.innoviti.emi.repository.core.SchemeModelRepository;
import com.innoviti.emi.repository.core.SchemeRepository;
import com.innoviti.emi.repository.master.SchemeDealerMasterRepository;
import com.innoviti.emi.repository.master.SchemeModelMasterRepository;
import com.innoviti.emi.service.core.DefaultDataService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;

@Component
public class SchemeModelColumnMapper implements ColumnMapper<SchemeMaster, Deque<SchemeModel>> {

  @Autowired
  private SchemeModelMasterRepository schemeModelMasterRepository;

  @Autowired
  private SchemeRepository schemeRepository;

  @Autowired
  private SchemeDealerMasterRepository schemeDealerMasterRespository;

  @Autowired
  private DefaultDataService defaultDataService;

  @Autowired
  private ModelRepository modelRepository;

  @Autowired
  private SequenceGeneratorService sequenceGeneratorService;
  
  @Autowired
  private SchemeModelRepository schemeModelRepository;

  @Override
  public Deque<SchemeModel> mapColumn(SchemeMaster schemeMaster) {
    String generalSchemeFlag = schemeMaster.getGeneralScheme();
    String dealerMappingFlag = schemeMaster.getDealerMapping();
    String modelMappingFlag = schemeMaster.getModelMapping();

    Integer schemeId = schemeMaster.getSchemeMasterComposite().getSchemeId();
    boolean isGenralScheme = isGenralScheme(generalSchemeFlag, dealerMappingFlag, modelMappingFlag);
    if (isGenralScheme) {
      throw new AlreadyMappedException(schemeId + "|General scheme");
    }
    Pageable topOne = new PageRequest(0, 1);
    List<Scheme> schemeList = schemeRepository.findLatestSchemeByBajajSchemeCode(String.valueOf(schemeId), topOne);
    if (schemeList == null || schemeList.isEmpty()) {
      throw new NotFoundException(schemeId + "|no scheme");
    }
    Scheme scheme = schemeList.get(0);
    String innoSchemeCode = scheme.getSchemeComposite().getInnoIssuerSchemeCode();
    
    List<Model> models = new ArrayList<>();
    if (isSchemeModelDealerMapping(generalSchemeFlag, dealerMappingFlag, modelMappingFlag)) {
      List<SchemeModelMaster> schemeModelList = checkSchemeDealerModelMapped(schemeId);
      models = getModels(schemeModelList);
    } else if (isSchemeModelMapping(generalSchemeFlag, dealerMappingFlag, modelMappingFlag)) {
      List<SchemeModelMaster> schemeModelList = checkSchemeModelMapped(schemeId);
      models = getModels(schemeModelList);
    } else if (isDealerSchemeMapping(generalSchemeFlag, dealerMappingFlag, modelMappingFlag)) {
      checkSchemeDealerMapped(schemeId);
      Model model = defaultDataService.getDefaultModel();
      models.add(model);
    } else {
      throw new NotFoundException(schemeId + "|Ignore scheme");
    }
    Deque<SchemeModel> schemeModels = new ArrayDeque<>();
    for (Model model : models) {
      String innoModelCode = model.getModelComposite().getInnoModelCode();
      List<SchemeModel> foundSchemeModels = schemeModelRepository.getSchemeModelBySchemeAndModelCode(innoSchemeCode, innoModelCode);
      String innoSchemeModelCode = null;
      if(foundSchemeModels != null && !foundSchemeModels.isEmpty()){
        innoSchemeModelCode = foundSchemeModels.get(0).getSchemeModelComposite().getInnoSchemeModelCode();
      }
      else{
        SequenceType sequenceType = SequenceType.SCHEME_MODEL;
        int seqNum = (int) sequenceGeneratorService.getSeqNumber(sequenceType.getSequenceName());
        String newInsertId = String.valueOf(seqNum);
        innoSchemeModelCode =
            sequenceType.getSequenceIdentifier() + Util.getLeftPaddedValueZeroFilled(newInsertId, 15);
      }
      SchemeModel schemeModel = new SchemeModel();
      SchemeModelComposite schemeModelComposite = new SchemeModelComposite();
      schemeModelComposite.setCrtupdDate(new Date());
      schemeModelComposite.setInnoSchemeModelCode(innoSchemeModelCode);
      schemeModel.setSchemeModelComposite(schemeModelComposite);
      schemeModel.setScheme(scheme);
      schemeModel.setModel(model);
      schemeModel.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
      schemeModel.setCrtupdStatus("N");
      schemeModel.setCrtupdUser("Batch");
      schemeModel.setRecordActive(true);
      schemeModels.add(schemeModel);
    }
    return schemeModels;
  }

  private boolean isGenralScheme(String generalSchemeFlag, String dealerMappingFlag,
      String modelMappingFlag) {
    if ("Y".equalsIgnoreCase(generalSchemeFlag)) {
      return true;
    }
    return ("N".equalsIgnoreCase(generalSchemeFlag) && "N".equalsIgnoreCase(dealerMappingFlag)
        && "N".equalsIgnoreCase(modelMappingFlag));
  }

  private boolean isSchemeModelDealerMapping(String generalSchemeFlag, String dealerMappingFlag,
      String modelMappingFlag) {
    return ("N".equalsIgnoreCase(generalSchemeFlag) && "Y".equalsIgnoreCase(dealerMappingFlag)
        && "Y".equalsIgnoreCase(modelMappingFlag));
  }

  private boolean isSchemeModelMapping(String generalSchemeFlag, String dealerMappingFlag,
      String modelMappingFlag) {
    return ("N".equalsIgnoreCase(generalSchemeFlag) && "N".equalsIgnoreCase(dealerMappingFlag)
        && "Y".equalsIgnoreCase(modelMappingFlag));
  }

  private boolean isDealerSchemeMapping(String generalSchemeFlag, String dealerMappingFlag,
      String modelMappingFlag) {
    return ("N".equalsIgnoreCase(generalSchemeFlag) && "Y".equalsIgnoreCase(dealerMappingFlag)
        && "N".equalsIgnoreCase(modelMappingFlag));
  }

  private List<SchemeModelMaster> checkSchemeDealerModelMapped(Integer schemeId) {
    List<SchemeModelMaster> schemeModelMasters = getMappedSchemeModel(schemeId);
    SchemeDealerMaster schemeDealerMaster = getMappedDealerScheme(schemeId);
    if ((schemeModelMasters == null || schemeModelMasters.isEmpty()) && schemeDealerMaster == null) {
      throw new NotFoundException(schemeId + "|No scheme model dealer mapping");
    }
    return schemeModelMasters;
  }

  private List<SchemeModelMaster> checkSchemeModelMapped(Integer schemeId) {
    List<SchemeModelMaster> schemeModelMasters = getMappedSchemeModel(schemeId);
    if (schemeModelMasters == null || schemeModelMasters.isEmpty()) {
      throw new NotFoundException(schemeId + "|No scheme model mapping");
    }
    return schemeModelMasters;
  }

  private SchemeDealerMaster checkSchemeDealerMapped(Integer schemeId) {
    SchemeDealerMaster schemeDealerMaster = getMappedDealerScheme(schemeId);
    if (schemeDealerMaster == null) {
      throw new NotFoundException(schemeId + "|No scheme dealer mapping");
    }
    return schemeDealerMaster;
  }

  private SchemeDealerMaster getMappedDealerScheme(Integer schemeId) {
    return schemeDealerMasterRespository.findLastestSchemeDealerBySchemeId(schemeId);
  }

  private List<SchemeModelMaster> getMappedSchemeModel(Integer schemeId) {
    return schemeModelMasterRepository.findLatestSchemeModelMasterByScheme(schemeId);
  }

  private Model getModel(SchemeModelMaster schemeModelMaster) {
    if (schemeModelMaster == null) {
      return defaultDataService.getDefaultModel();
    }
    String modelId = schemeModelMaster.getSchemeModelMasterComposite().getModelId();
    Model model =
        modelRepository.findLatestModelByBajajModelCode(modelId);
    if (model == null) {
      return defaultDataService.getDefaultModel();
    }
    return model;
  }

  private List<Model> getModels(List<SchemeModelMaster> schemeModelList) {
    List<Model> models = new ArrayList<>();
    if (schemeModelList == null || schemeModelList.isEmpty()) {
      Model model = defaultDataService.getDefaultModel();
      models.add(model);
    }
    for (SchemeModelMaster schemeModelMaster : schemeModelList) {
      Model model = getModel(schemeModelMaster);
      models.add(model);
    }
    return models;
  }
}
