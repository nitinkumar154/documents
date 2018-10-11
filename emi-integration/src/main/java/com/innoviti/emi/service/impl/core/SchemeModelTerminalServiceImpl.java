package com.innoviti.emi.service.impl.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.constant.UTIDUpdateStatus;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.core.SchemeModelTerminalComposite;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.SchemeModelTerminalRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.SchemeModelService;
import com.innoviti.emi.service.core.SchemeModelTerminalService;
import com.innoviti.emi.service.core.SequenceGeneratorService;

@Service
@Transactional(transactionManager = "transactionManager")
public class SchemeModelTerminalServiceImpl
    extends CrudServiceHelper<SchemeModelTerminal, SchemeModelTerminalComposite>
    implements SchemeModelTerminalService {

  private static Logger logger = LoggerFactory.getLogger(SchemeModelTerminalServiceImpl.class);

  private SchemeModelTerminalRepository schemeModelTerminalRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  SchemeModelService schemeModelService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  @Autowired
  public SchemeModelTerminalServiceImpl(
      SchemeModelTerminalRepository schemeModelTerminalRepository) {
    super(schemeModelTerminalRepository);
    this.schemeModelTerminalRepository = schemeModelTerminalRepository;
  }

  @Override
  public SchemeModelTerminal create(SchemeModelTerminal u) {
    
    SchemeModelTerminalComposite schemeModelTerminalComposite = new SchemeModelTerminalComposite();
    String innoSchemeModelCode = u.getInnoSchemeModelCode();
    SchemeModel schemeModel =
        schemeModelService.findSchemeModelByInnoSchemeModelCode(innoSchemeModelCode);
    schemeModelTerminalComposite.setSchemeModel(schemeModel);
    
    schemeModelTerminalComposite.setUtid(u.getUtid());
    schemeModelTerminalComposite.setCrtupdDate(u.getCrtupdDate());
    u.setIssuerSchemeTerminalSyncStatus(UTIDUpdateStatus.NOT_SENT);
    u.setSchemeModelTerminalComposite(schemeModelTerminalComposite);
    return helperCreate(u);
  }

  @Override
  public void deleteById(SchemeModelTerminalComposite id) {
    helperDeleteById(id);

  }

  @Override
  public SchemeModelTerminal findById(SchemeModelTerminalComposite id) {
    return helperFindById(id);
  }

  @Override
  public SchemeModelTerminal update(SchemeModelTerminal u) {
    return helperUpdate(u);
  }

  @Override
  public List<SchemeModelTerminal> findAll() {
    return helperFindAll();
  }

  @Override
  public List<SchemeModelTerminal> getSchemeModelTerminalListById(String utid,
      String innoSchemeModelCode) {
    if (utid == null || innoSchemeModelCode == null)
      throw new BadRequestException("Requested id cannot be Null !");

    List<SchemeModelTerminal> schemeModelTerminals = schemeModelTerminalRepository
        .findTop1BySchemeModelTerminalCompositeUtidAndSchemeModelTerminalCompositeSchemeModelOrderBySchemeModelTerminalCompositeCrtupdDateDesc(
            utid, innoSchemeModelCode);

    if (schemeModelTerminals != null && !schemeModelTerminals.isEmpty())
      return schemeModelTerminals;
    return schemeModelTerminals;
  }
  
  @Override
  public List<SchemeModelTerminal> getSchemeModelTerminalListById(String innoSchemeModelCode) {
	  if (innoSchemeModelCode == null)
	      throw new BadRequestException("Requested id cannot be Null !");
	  List<SchemeModelTerminal> result = schemeModelTerminalRepository
		        .findBySchemeCode(innoSchemeModelCode);

		    if (result != null && !result.isEmpty())
		      return result;
  	return null;
  }

  @Override
  public List<SchemeModelTerminal> createIssuerSchemeModel(List<SchemeModelTerminal> schemeModel) {
    try {
      schemeModel.stream().forEach(s -> {
        SchemeModelTerminalComposite schemeComposite = new SchemeModelTerminalComposite();
        schemeComposite.setCrtupdDate(new Date());
        // schemeComposite.setInnoSchemeModelCode(s.getInnoSchemeModelCode());
        schemeComposite.setUtid(s.getUtid());
        s.setCrtupdReason("Batch job insert");
        s.setCrtupdStatus("N");
        s.setCrtupdUser("Batch job");
        s.setRecordActive(false);
        s.setSchemeModelTerminalComposite(schemeComposite);
        helperCreate(s);
      });

    } catch (Exception e) {
      logger.error("Error ...", e);
    }

    return schemeModel;
  }

  @Override

  public Page<SchemeModelTerminal> findAllSchemeModelTerminal(Pageable pageable) {
    logger.info("before getting data");
    Page<SchemeModelTerminal> schemeModelTerminalList =
        schemeModelTerminalRepository.findAll(pageable);
    if (schemeModelTerminalList == null || !schemeModelTerminalList.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);

    return schemeModelTerminalList;
  }

  @Override
  public Page<SchemeModelTerminal> findAllSchemeModelTerminalForDataMovement(String recordStatus,
      Pageable pageable) {
    Page<SchemeModelTerminal> schemeModelTerminalList = null;
    Date latestDateForEntity = dataMoveKeeperService
        .findById(SequenceType.SCHEME_MODEL_TERMINAL.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      schemeModelTerminalList = schemeModelTerminalRepository.findByCrtupdStatus(recordStatus,
          latestDateForEntity, pageable);
    } else {
      schemeModelTerminalList =
          schemeModelTerminalRepository.findByCrtupdStatus(recordStatus, pageable);
    }
    if (schemeModelTerminalList == null || !schemeModelTerminalList.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);

    return schemeModelTerminalList;
  }

  @Override
  public boolean findByCodeIfExists(String innoSchemeModelCode, String utid) {

    Iterable<SchemeModelTerminal> schemeModelTerminalList =
        schemeModelTerminalRepository.findBySchemeCodeAndUtid(innoSchemeModelCode, utid);
    if (schemeModelTerminalList == null || !schemeModelTerminalList.iterator().hasNext())
      return false;

    return true;
  }

  @Override
  public List<SchemeModelTerminal> updateSchemeModelTerminalSyncStatus(String innoSchemeModelCode,
      String utid, String status) {

    List<SchemeModelTerminal> savedSchemeModelTerminalList = new ArrayList<>();

    Iterable<SchemeModelTerminal> updatedSchemeModelTerminalList =
        schemeModelTerminalRepository.findBySchemeCodeAndUtid(innoSchemeModelCode, utid);

    if (updatedSchemeModelTerminalList == null
        || !updatedSchemeModelTerminalList.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);

    for (SchemeModelTerminal schemeModelTerminal : updatedSchemeModelTerminalList) {
      schemeModelTerminal.setIssuerSchemeTerminalSyncStatus(UTIDUpdateStatus.valueOf(status));
      SchemeModelTerminal savedEntity =
          schemeModelTerminalRepository.saveAndFlush(schemeModelTerminal);
      if (savedEntity != null)
        savedSchemeModelTerminalList.add(savedEntity);
    }
    if (savedSchemeModelTerminalList == null || savedSchemeModelTerminalList.isEmpty())
      throw new NotFoundException("No records found for the given query", 404);

    return savedSchemeModelTerminalList;
  }

}
