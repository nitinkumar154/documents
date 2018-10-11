package com.innoviti.emi.service.impl.core;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.constant.ErrorMessages;
import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.entity.core.TenureComposite;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.TenureRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.service.core.TenureService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class TenureServiceImpl extends CrudServiceHelper<Tenure, TenureComposite>
    implements TenureService {

  private TenureRepository tenureRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  private static final String SEQ_NAME = "Tenure";
  private static final String IDENTIFIER = "TR";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public TenureServiceImpl(TenureRepository tenureRepository) {
    super(tenureRepository);
    this.tenureRepository = tenureRepository;
  }

  @Override
  public Tenure create(Tenure u) {
    TenureComposite tenureComposite = new TenureComposite();
    if (u.getInnoTenureCode() == null) {
      if (!tenureExists(u.getTenureMonth()))
        throw new AlreadyMappedException(
            "Requested Tenure -> " + u.getTenureMonth() + " month(s) already exists", 422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);

      String innoTenureCode = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 3);
      tenureComposite.setInnoTenureCode(innoTenureCode);
    } else {
      tenureComposite.setInnoTenureCode(u.getInnoTenureCode());
    }
    tenureComposite.setCrtupdDate(u.getCrtupdDate());
    u.setTenureComposite(tenureComposite);
    return helperCreate(u);
  }

  private boolean tenureExists(String tenureMonth) {
    if (tenureMonth == null)
      throw new BadRequestException("Requested field should not be null !!");
    return tenureRepository.findTenureByName(tenureMonth.trim()).isEmpty();
  }

  @Override
  public void deleteById(TenureComposite id) {
    helperDeleteById(id);
  }

  @Override
  public Tenure findById(TenureComposite id) {
    return helperFindById(id);
  }

  @Override
  public Tenure update(Tenure u) {
    return null;
  }

  @Override
  public List<Tenure> findAll() {
    return helperFindAll();
  }

  @Override
  public Tenure findTenureByInnoTenureCode(String innoTenureCode) {
    if (innoTenureCode == null || innoTenureCode.isEmpty()) {
      throw new BadRequestException("Please provide innoviti tenure code", 400);
    }
    Tenure lookedUpTenure = tenureRepository
        .findTop1ByTenureCompositeInnoTenureCodeOrderByTenureCompositeCrtupdDateDesc(
            innoTenureCode);
    if (lookedUpTenure == null) {
      throw new NotFoundException("Tenure not found for id : " + innoTenureCode, 404);
    }
    return lookedUpTenure;
  }

  @Override
  public Iterable<Tenure> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Please provide query string", 400);
    }
    Iterable<Tenure> tenures = tenureRepository.findAll(predicate);
    if (tenures == null) {
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    }
    return tenures;
  }

  @Override
  public Page<Tenure> findAllTenures(Pageable pageable) {
    Page<Tenure> tenureList = tenureRepository.findAllTenures(pageable);
    if (tenureList == null || !tenureList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return tenureList;
  }

  @Override
  public Page<Tenure> findAllTenuresForDataMovement(String recordStatus, Pageable pageable) {
    Page<Tenure> tenureList = null;
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.TENURE.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      tenureList = tenureRepository.findAllTenuresForDataMovement(recordStatus, latestDateForEntity,
          pageable);
    } else {
      tenureList = tenureRepository.findAllTenuresForDataMovement(recordStatus, pageable);
    }
    if (tenureList == null || !tenureList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return tenureList;
  }

  @Override
  @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRES_NEW,
      isolation = Isolation.DEFAULT)
  public synchronized Tenure saveTenureForBatch(String tenureMonth) {
    Tenure tenure = tenureRepository.findByTenureMonth(tenureMonth);
    if(tenure != null){
      return tenure;
    }
    tenure = new Tenure();
    tenure.setCrtupdDate(new Date());
    tenure.setCrtupdReason("Batch insert");
    tenure.setCrtupdStatus("N");
    tenure.setCrtupdUser("Batch Job");
    tenure.setRecordActive(true);
    tenure.setTenureDisplayName(tenureMonth + " Months");
    tenure.setTenureMonth(tenureMonth);
    return create(tenure);
  }

  @Override
  public List<Tenure> autoCompleteTenures(String term) {
    List<Tenure> tenures = tenureRepository.findAllTenuresWithTerm(term);
    if (tenures == null || !tenures.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);

    return tenures;
  }
}
