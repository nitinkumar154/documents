package com.innoviti.emi.entity.column.mapper.impl;

import java.util.ArrayDeque;
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
import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.repository.core.SchemeModelRepository;
import com.innoviti.emi.repository.core.SchemeRepository;
import com.innoviti.emi.repository.master.SchemeMasterRepository;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;

@Component
public class GeneralSchemeModelColumnMapper implements ColumnMapper<Model, Deque<SchemeModel>> {

  @Autowired
  private SchemeMasterRepository schemeMasterRepository;

  @Autowired
  private SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  private SchemeRepository schemeRepository;

  @Autowired
  private SchemeModelRepository schemeModelRepository;
  
  @Override
  public Deque<SchemeModel> mapColumn(Model model) {
    List<SchemeMaster> generalSchemes = schemeMasterRepository.findGeneralSchemes();
    if (generalSchemes == null || generalSchemes.isEmpty()) {
      return null;
    }
    Deque<SchemeModel> schemeModels = new ArrayDeque<>();
    Pageable topOne = new PageRequest(0, 1);
    for (SchemeMaster schemeMaster : generalSchemes) {
      Integer schemeId = schemeMaster.getSchemeMasterComposite().getSchemeId();
      List<Scheme> schemeList = schemeRepository.findLatestSchemeByBajajSchemeCode(String.valueOf(schemeId), topOne);
      if (schemeList == null || schemeList.isEmpty()) {
        continue;
      }
      Scheme scheme = schemeList.get(0);
      String innoSchemeModelCode = null;
      SchemeModel foundSchemeModel = schemeModelRepository.findTop1BySchemeAndModelOrderBySchemeModelCompositeCrtupdDateDesc(scheme, model);
      if(foundSchemeModel != null){
        innoSchemeModelCode = foundSchemeModel.getSchemeModelComposite().getInnoSchemeModelCode();
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
}
