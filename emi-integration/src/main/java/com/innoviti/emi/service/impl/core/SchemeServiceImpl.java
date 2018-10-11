package com.innoviti.emi.service.impl.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.constant.ErrorMessages;
import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeComposite;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelComposite;
import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.SchemeRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.IssuerBankService;
import com.innoviti.emi.service.core.SchemeModelService;
import com.innoviti.emi.service.core.SchemeService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.service.core.TenureService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class SchemeServiceImpl extends CrudServiceHelper<Scheme, SchemeComposite>
    implements SchemeService {

  private SchemeRepository schemeRespository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  IssuerBankService issuerBankService;

  @Autowired
  TenureService tenureService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;
  
  @Autowired
  SchemeModelService schemeModelService;

  private static final String SEQ_NAME = "Scheme";
  private static final String IDENTIFIER = "SCH";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public SchemeServiceImpl(SchemeRepository schemeRepository) {
    super(schemeRepository);
    this.schemeRespository = schemeRepository;
  }

  @Override
  public Scheme create(Scheme u) {
    SchemeComposite schemeComposite = new SchemeComposite();
    String innoIssuerBankCode = u.getInnoIssuerBankCode();
    IssuerBank issuerBank =
        issuerBankService.findIssuerBankByInnoIssuerBankCode(innoIssuerBankCode);
    u.setIssuerBank(issuerBank);
    u.setInnoIssuerBankCode(innoIssuerBankCode);
    String innoTenureCode = u.getInnoTenureCode();
    Tenure tenure = tenureService.findTenureByInnoTenureCode(innoTenureCode);
    u.setTenure(tenure);
    u.setInnoTenureCode(innoTenureCode);
    if (u.getInnoIssuerSchemeCode() == null) {
      if (!schemeExists(u.getSchemeDisplayName()))
        throw new AlreadyMappedException(
            "Requested Scheme -> " + u.getSchemeDisplayName() + " already exists", 422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);
      String innoIssuerSchemeCode = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);
      schemeComposite.setInnoIssuerSchemeCode(innoIssuerSchemeCode);
    } else {
      schemeComposite.setInnoIssuerSchemeCode(u.getInnoIssuerSchemeCode());
    }
    schemeComposite.setCrtupdDate(u.getCrtupdDate());
    u.setSchemeComposite(schemeComposite);
    return helperCreate(u);
  }

  private boolean schemeExists(String schemeDisplayName) {
    if (schemeDisplayName == null)
      throw new BadRequestException("Requested field should not be null !!");
    return schemeRespository.findSchemeByName(schemeDisplayName.trim()).isEmpty();
  }

  @Override
  public void deleteById(SchemeComposite id) {
    helperDeleteById(id);
  }

  @Override
  public Scheme findById(SchemeComposite id) {
    return helperFindById(id);
  }

  @Override
  public Scheme update(Scheme u) {
	  Scheme result = new Scheme();
	  u.copyNonAuditDetailsInto(result);
	  u.copyAuditDetailsInto(result);
	  List<SchemeModel> listOfSchemeModels = schemeModelService.getAllSchemeModelForScheme(findSchemeByInnoIssuerSchemeCode(u.getInnoIssuerSchemeCode()));
	  SchemeComposite schemeComposite = new SchemeComposite();
	  schemeComposite.setInnoIssuerSchemeCode(u.getInnoIssuerSchemeCode());
	  schemeComposite.setCrtupdDate(u.getCrtupdDate());
	  u.setSchemeComposite(schemeComposite);
	  u.setIssuerBank(issuerBankService.findIssuerBankByInnoIssuerBankCode(u.getInnoIssuerBankCode()));
	  u.setTenure(tenureService.findTenureByInnoTenureCode(u.getInnoTenureCode()));
	  result = helperUpdate(u);
	  for(SchemeModel schemeModel:listOfSchemeModels) {
		  SchemeModel object = new SchemeModel();
		  schemeModel.copyAuditDetailsInto(object);
		  schemeModel.copyNonAuditDetailsInto(object);
		  SchemeModelComposite id = new SchemeModelComposite();
		  id.setInnoSchemeModelCode(schemeModel.getSchemeModelComposite().getInnoSchemeModelCode());
		  id.setCrtupdDate(u.getCrtupdDate());
		  object.setCrtupdStatus("U");
		  object.setSchemeModelComposite(id);
		  object.setScheme(result);
		  schemeModelService.update(object);
	  }
	  return result;
  }

  @Override
  public List<Scheme> findAll() {
    return helperFindAll();
  }

  @Override
  public Scheme findSchemeByInnoIssuerSchemeCode(String innoIssuerSchemeCode) {
    if (innoIssuerSchemeCode == null)
      throw new BadRequestException("Requested id cannot be null");
    Scheme lookedUpScheme = schemeRespository
        .findTop1BySchemeCompositeInnoIssuerSchemeCodeOrderBySchemeCompositeCrtupdDateDesc(
            innoIssuerSchemeCode);
    if (lookedUpScheme == null) {
      throw new NotFoundException("Scheme not found for id : " + innoIssuerSchemeCode, 404);
    }
    return lookedUpScheme;
  }

  @Override
  public Iterable<Scheme> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Query string cannot be blank", 400);
    }
    Iterable<Scheme> schemes = schemeRespository.findAll(predicate);
    if (schemes == null) {
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    }
    return schemes;
  }

  @Override
  public Page<Scheme> findAllSchemes(Pageable pageable) {
    Page<Scheme> schemePages = schemeRespository.findAllSchemes(pageable);
    if (schemePages == null || !schemePages.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return schemePages;
  }

  @Override
  public Page<Scheme> findAllSchemesFromFilter(String bankCode, String tenureCode,
      Pageable pageable) {

    List<Scheme> schemeListUpdated = new ArrayList<>();
    Page<Scheme> schemeList =
        schemeRespository.findAllSchemesByFilter(bankCode, tenureCode, pageable);
    if (schemeList == null || !schemeList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    Iterator<Scheme> i = schemeList.iterator();
    while (i.hasNext()) {
      Scheme s = i.next();
      Scheme latestScheme = schemeRespository
          .findTop1BySchemeCompositeInnoIssuerSchemeCodeOrderBySchemeCompositeCrtupdDateDesc(
              s.getSchemeComposite().getInnoIssuerSchemeCode());
      if (s == latestScheme)
        schemeListUpdated.add(latestScheme);
    }
    if (schemeListUpdated.isEmpty())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    return new PageImpl<>(schemeListUpdated);
  }

  @Override
  public Page<Scheme> findAllSchemesForDataMovement(String recordStatus, Pageable pageable) {
    Page<Scheme> schemeList = null;
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.SCHEME.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      schemeList = schemeRespository.findAllSchemesForDataMovement(recordStatus,
          latestDateForEntity, pageable);
    } else {
      schemeList = schemeRespository.findAllSchemesForDataMovement(recordStatus, pageable);
    }
    if (schemeList == null || !schemeList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return schemeList;
  }

  @Override
  public List<Scheme> findAllNonBajajSchemes() {

    List<Scheme> schemeList = schemeRespository.findAllNonBajajSchemes();
    if (schemeList == null || !schemeList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return schemeList;
  }

}
