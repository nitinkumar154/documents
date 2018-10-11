package com.innoviti.emi.service.impl.core;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.constant.ErrorMessages;
import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.entity.core.IssuerBankComposite;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.IssuerBankRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.IssuerBankService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class IssuerBankServiceImpl extends CrudServiceHelper<IssuerBank, IssuerBankComposite>
    implements IssuerBankService {

  private IssuerBankRepository issuerBankRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  private static final String SEQ_NAME = "IssuerBank";
  private static final String IDENTIFIER = "ISB";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public IssuerBankServiceImpl(IssuerBankRepository issuerBankRepository) {
    super(issuerBankRepository);
    this.issuerBankRepository = issuerBankRepository;
  }

  @Override
  public IssuerBank create(IssuerBank u) {
    IssuerBankComposite issuerBankComposite = new IssuerBankComposite();
    if (u.getInnoIssuerBankCode() == null) {
      if (!issuerExists(u.getIssuerBankDisplayName()))
        throw new AlreadyMappedException(
            "Requested Issuer Bank -> " + u.getIssuerBankDisplayName() + " already exists", 422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);
      String innoIssuerBankCode = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);

      issuerBankComposite.setInnoIssuerBankCode(innoIssuerBankCode);
    } else {
      issuerBankComposite.setInnoIssuerBankCode(u.getInnoIssuerBankCode());
    }
    issuerBankComposite.setCrtupdDate(u.getCrtupdDate());
    u.setIssuerBankComposite(issuerBankComposite);
    return helperCreate(u);
  }

  private boolean issuerExists(String issuerBankDisplayName) {
    if (issuerBankDisplayName == null)
      throw new BadRequestException("Requested field should not be null !!");
    return issuerBankRepository.findIssuerByName(issuerBankDisplayName.trim()).isEmpty();
  }

  @Override
  public void deleteById(IssuerBankComposite id) {
    helperDeleteById(id);
  }

  @Override
  public IssuerBank findById(IssuerBankComposite id) {
    return helperFindById(id);
  }

  @Override
  public IssuerBank update(IssuerBank u) {
    return null;
  }

  @Override
  public List<IssuerBank> findAll() {
    return helperFindAll();
  }

  @Override
  public IssuerBank findIssuerBankByInnoIssuerBankCode(String innoIssuerBankCode) {
    if (innoIssuerBankCode == null || innoIssuerBankCode.isEmpty())
      throw new BadRequestException("Requested id cannot be blank", 400);

    IssuerBank issuerBank = issuerBankRepository
        .findTop1ByIssuerBankCompositeInnoIssuerBankCodeOrderByIssuerBankCompositeCrtupdDateDesc(
            innoIssuerBankCode);
    if (issuerBank == null) {
      throw new NotFoundException("Categories not found for id : " + innoIssuerBankCode, 404);
    }
    return issuerBank;
  }

  @Override
  public Iterable<IssuerBank> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Query string cannot be blank", 400);
    }
    Iterable<IssuerBank> issuerBanks = issuerBankRepository.findAll(predicate);
    if (issuerBanks == null) {
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    }
    return issuerBanks;
  }

  @Override
  public Page<IssuerBank> findAllIssuerBank(Pageable pageable) {

    Page<IssuerBank> issuerBanks = issuerBankRepository.findAllIssuerBanks(pageable);
    if (issuerBanks == null || !issuerBanks.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return issuerBanks;
  }

  @Override
  public Page<IssuerBank> findAllIssuerBankForDataMovement(String recordStatus, Pageable pageable) {
    Page<IssuerBank> issuerBanks = null;
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.ISSUER_BANK.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      issuerBanks = issuerBankRepository.findAllIssuerBanksForDataMovement(recordStatus,
          latestDateForEntity, pageable);
    } else {
      issuerBanks = issuerBankRepository.findAllIssuerBanksForDataMovement(recordStatus, pageable);
    }
    if (issuerBanks == null || !issuerBanks.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return issuerBanks;
  }

}
