package com.innoviti.emi.service.impl.core;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.constant.ErrorMessages;
import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.ManufacturerComposite;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.ManufacturerRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.ManufacturerService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class ManufacturerServiceImpl extends CrudServiceHelper<Manufacturer, ManufacturerComposite>
    implements ManufacturerService {

  private static Logger logger = Logger.getLogger(ManufacturerServiceImpl.class);

  private ManufacturerRepository manufacturerRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  private static final String SEQ_NAME = "Manufacturer";
  private static final String IDENTIFIER = "MAN";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
    super(manufacturerRepository);
    this.manufacturerRepository = manufacturerRepository;
  }

  @Override
  public Manufacturer create(Manufacturer u) {
    ManufacturerComposite manufacturerComposite = new ManufacturerComposite();
    if (u.getInnoManufacturerCode() == null) {
      if (!manufacturerExists(u.getManufacturerDisplayName()))
        throw new AlreadyMappedException(
            "Requested Manufacturer -> " + u.getManufacturerDisplayName() + " already exists", 422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);
      String innoManufacturerCode = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);

      manufacturerComposite.setInnoManufacturerCode(innoManufacturerCode);
    } else {
      manufacturerComposite.setInnoManufacturerCode(u.getInnoManufacturerCode());
    }
    manufacturerComposite.setCrtupdDate(u.getCrtupdDate());
    u.setManufacturerComposite(manufacturerComposite);
    return helperCreate(u);
  }

  private boolean manufacturerExists(String manufacturerDisplayName) {
    if (manufacturerDisplayName == null)
      throw new BadRequestException("Requested field should not be null !!");
    return manufacturerRepository.findManufacturerByName(manufacturerDisplayName.trim()).isEmpty();
  }

  @Override
  public void deleteById(ManufacturerComposite id) {
    helperDeleteById(id);
  }

  @Override
  public Manufacturer findById(ManufacturerComposite id) {
    return helperFindById(id);
  }

  @Override
  public Manufacturer update(Manufacturer u) {
    return null;
  }

  @Override
  public List<Manufacturer> findAll() {
    return helperFindAll();
  }

  @Override
  public Manufacturer findManufacturerByInnoManufacturerCode(String innoManufacturerCode) {
    if (innoManufacturerCode == null)
      throw new BadRequestException("Requested id cannot be Null !");
    return manufacturerRepository
        .findTop1ByManufacturerCompositeInnoManufacturerCodeOrderByManufacturerCompositeCrtupdDateDesc(
            innoManufacturerCode);
  }

  @Override
  public Iterable<Manufacturer> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Query string cannot be blank", 400);
    }
    Iterable<Manufacturer> manufacturerList = manufacturerRepository.findAll(predicate);
    if (manufacturerList == null) {
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    }
    return manufacturerList;
  }

  @Override
  public Page<Manufacturer> findAllManufacturers(Pageable pageable) {

    Page<Manufacturer> manufacturers = manufacturerRepository.findAllManufacturers(pageable);
    if (manufacturers == null || !manufacturers.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return manufacturers;
  }

  @Override
  public Page<Manufacturer> findAllManufacturersForDataMovement(String recordStatus,
      Pageable pageable) {
    Page<Manufacturer> manufacturers = null;
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.MANUFACTURER.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      manufacturers = manufacturerRepository.findAllManufacturersForDataMovement(recordStatus,
          latestDateForEntity, pageable);
    } else {
      manufacturers =
          manufacturerRepository.findAllManufacturersForDataMovement(recordStatus, pageable);
    }
    if (manufacturers == null || !manufacturers.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    return manufacturers;
  }

  @Override
  public List<Manufacturer> autoCompleteManufacturers(String term) {
    logger.info("Entering autoCompleteManufacturers :: ManufacturerServiceImpl");
    List<Manufacturer> manufacturers = manufacturerRepository.findAllManufacturerWithTerm(term);
    if (manufacturers == null || !manufacturers.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    logger.info("Exiting autoCompleteManufacturers :: ManufacturerServiceImpl");
    return manufacturers;
  }


}
