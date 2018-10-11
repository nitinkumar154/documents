package com.innoviti.emi.service.impl.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.constant.ErrorMessages;
import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelComposite;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.core.SchemeModelTerminalComposite;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.SchemeModelRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.CategoryService;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.ManufacturerService;
import com.innoviti.emi.service.core.ModelService;
import com.innoviti.emi.service.core.SchemeModelService;
import com.innoviti.emi.service.core.SchemeModelTerminalService;
import com.innoviti.emi.service.core.SchemeService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class SchemeModelServiceImpl extends CrudServiceHelper<SchemeModel, SchemeModelComposite>
    implements SchemeModelService {

  private SchemeModelRepository schemeModelRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  SchemeService schemeService;

  @Autowired
  ModelService modelService;

  @Autowired
  ManufacturerService manufacturerService;

  @Autowired
  CategoryService categoryService;
  
  @Autowired
  SchemeModelTerminalService schemeModelTerminalService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  private static final String SEQ_NAME = "SchemeModel";
  private static final String IDENTIFIER = "SHM";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public SchemeModelServiceImpl(SchemeModelRepository schemeModelRepository) {
    super(schemeModelRepository);
    this.schemeModelRepository = schemeModelRepository;
  }

  @Override
  public SchemeModel create(SchemeModel u) {
    SchemeModelComposite schemeModelComposite = new SchemeModelComposite();

    String innoIssuerSchemeCode = u.getInnoIssuerSchemeCode();
    Scheme scheme = schemeService.findSchemeByInnoIssuerSchemeCode(innoIssuerSchemeCode);
    u.setScheme(scheme);
    u.setInnoIssuerSchemeCode(innoIssuerSchemeCode);

    String innoModelCode = u.getInnoModelCode();
    Model model = modelService.findModelByInnoModelCode(innoModelCode);
    u.setModel(model);
    u.setInnoModelCode(innoModelCode);

    if (u.getInnoSchemeModelCode() == null) {
      if (!schemeMappingExists(u.getInnoIssuerSchemeCode(), u.getInnoModelCode()))
        throw new AlreadyMappedException(
            "Requested Scheme & Model: " + scheme.getSchemeDisplayName() + " <--> "
                + model.getModelDisplayNo() + " are already mapped.",
            422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);
      String innoSchemeModelCode = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);

      schemeModelComposite.setInnoSchemeModelCode(innoSchemeModelCode);
    } else {
      schemeModelComposite.setInnoSchemeModelCode(u.getInnoSchemeModelCode());
    }
    schemeModelComposite.setCrtupdDate(u.getCrtupdDate());
    u.setSchemeModelComposite(schemeModelComposite);
    return helperCreate(u);
  }

  private boolean schemeMappingExists(String innoIssuerSchemeCode, String innoModelCode) {
    if (innoIssuerSchemeCode == null || innoModelCode == null)
      throw new BadRequestException("Requested field should not be null !!");
    return schemeModelRepository.findSchemeMapping(innoIssuerSchemeCode, innoModelCode).isEmpty();
  }

  @Override
  public void deleteById(SchemeModelComposite id) {
    helperDeleteById(id);

  }

  @Override
  public SchemeModel findById(SchemeModelComposite id) {
    return helperFindById(id);
  }

  @Override
  public SchemeModel update(SchemeModel u) {
	  SchemeModel result = null;
	  List<SchemeModelTerminal> listOfTerminalMappings = schemeModelTerminalService.getSchemeModelTerminalListById(u.getSchemeModelComposite().getInnoSchemeModelCode());
	  result = helperUpdate(u);
	  for(SchemeModelTerminal schemeModelTerminal:listOfTerminalMappings) {
		  SchemeModelTerminal object = new SchemeModelTerminal();
		  SchemeModelTerminalComposite id = new SchemeModelTerminalComposite();
		  id.setUtid(schemeModelTerminal.getSchemeModelTerminalComposite().getUtid());
		  id.setInnoSchemeModelCode(schemeModelTerminal.getSchemeModelTerminalComposite().getInnoSchemeModelCode());
		  id.setSchemeModel(u);
		  id.setCrtupdDate(u.getSchemeModelComposite().getCrtupdDate());
		  object.setSchemeModelTerminalComposite(id);
		  schemeModelTerminal.copyNonAuditDetailsInto(object);
		  schemeModelTerminal.copyAuditDetailsInto(object);
		  object.setCrtupdStatus("U");
		  object.setCrtupdDate(u.getSchemeModelComposite().getCrtupdDate());
		  schemeModelTerminalService.update(object);
	  }
    return result;
  }

  @Override
  public List<SchemeModel> findAll() {
    return helperFindAll();
  }

  @Override
  public SchemeModel findSchemeModelByInnoSchemeModelCode(String innoSchemeModelCode) {
    if (innoSchemeModelCode == null || innoSchemeModelCode.isEmpty())
      throw new BadRequestException("Requested id cannot be blank", 400);

    SchemeModel lookedUpSchemeModel = schemeModelRepository
        .findTop1BySchemeModelCompositeInnoSchemeModelCodeOrderBySchemeModelCompositeCrtupdDateDesc(
            innoSchemeModelCode);
    if (lookedUpSchemeModel == null) {
      throw new NotFoundException("Scheme Model not found for id : " + innoSchemeModelCode, 404);
    }

    return lookedUpSchemeModel;
  }

  @Override
  public Iterable<SchemeModel> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Query string cannot be blank", 400);
    }
    Iterable<SchemeModel> schemeModels = schemeModelRepository.findAll(predicate);
    if (schemeModels == null || !schemeModels.iterator().hasNext()) {
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    }
    return schemeModels;
  }

  @Override
  public Page<SchemeModel> findAllSchemeModel(String bajajFlag, Pageable pageable) {

    Page<SchemeModel> schemeModelList = null;
    List<Scheme> schemeList = null;
    if (bajajFlag.equals("N")) {
      schemeList = schemeService.findAllNonBajajSchemes();
      List<String> schemeCodes = new ArrayList<>();
      for (Scheme scheme : schemeList)
        schemeCodes.add(scheme.getSchemeComposite().getInnoIssuerSchemeCode());
      schemeModelList = schemeModelRepository.findAllSchemeModelsFromScheme(schemeCodes, pageable);

    } else
      schemeModelList = schemeModelRepository.findAllSchemesModels(pageable);

    if (schemeModelList == null || !schemeModelList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return schemeModelList;
  }

  @Override
  public Page<SchemeModel> findAllSchemeModelsForDataMovement(String recordStatus,
      Pageable pageable) {
    Page<SchemeModel> schemeModelList = null;
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.SCHEME_MODEL.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      schemeModelList = schemeModelRepository.findAllSchemeModelsForDataMovement(recordStatus,
          latestDateForEntity, pageable);
    } else {
      schemeModelList =
          schemeModelRepository.findAllSchemeModelsForDataMovement(recordStatus, pageable);
    }
    if (schemeModelList == null || !schemeModelList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    return schemeModelList;
  }

  @Override
  public List<SchemeModel> getAllSchemeModelForScheme(Scheme scheme) {
	  List<SchemeModel> resultList = new LinkedList<>();
	  resultList = schemeModelRepository.getAllSchemeModelBySchemeCode(scheme.getSchemeComposite().getInnoIssuerSchemeCode());
	  return resultList;
  }
}
