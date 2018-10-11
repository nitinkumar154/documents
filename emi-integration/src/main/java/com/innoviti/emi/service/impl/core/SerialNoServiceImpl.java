package com.innoviti.emi.service.impl.core;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.SerialNo;
import com.innoviti.emi.entity.core.SerialNoComposite;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.SerialNoRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.ModelService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.service.core.SerialNoService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class SerialNoServiceImpl extends CrudServiceHelper<SerialNo, SerialNoComposite>
    implements SerialNoService {

  private SerialNoRepository serialNoRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  ModelService modelService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  private static final String SEQ_NAME = "SerialNo";
  private static final String IDENTIFIER = "SRN";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public SerialNoServiceImpl(SerialNoRepository serialNoRepository) {
    super(serialNoRepository);
    this.serialNoRepository = serialNoRepository;
  }

  @Override
  public SerialNo create(SerialNo u) {
    SerialNoComposite serialNoComposite = new SerialNoComposite();

    String innoModelCode = u.getInnoModelCode();
    Model model = modelService.findModelByInnoModelCode(innoModelCode);
    u.setModel(model);
    u.setInnoModelCode(innoModelCode);

    if (u.getInnoModelSerialNumber() == null) {
      if (!serialNoExists(u.getManufacturerSerialNumber()))
        throw new AlreadyMappedException(
            "Requested Serial Number -> " + u.getManufacturerSerialNumber() + " already exists",
            422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);
      String innoSerialNo = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);

      serialNoComposite.setInnoModelSerialNumber(innoSerialNo);
    } else {
      serialNoComposite.setInnoModelSerialNumber(u.getInnoModelSerialNumber());
    }
    serialNoComposite.setCrtupdDate(u.getCrtupdDate());
    u.setSerialNoComposite(serialNoComposite);
    return helperCreate(u);
  }

  private boolean serialNoExists(String serialNoDisplayName) {
    if (serialNoDisplayName == null)
      throw new BadRequestException("Requested field should not be null !!");
    return serialNoRepository.findSerialNoByName(serialNoDisplayName.trim()).isEmpty();
  }

  @Override
  public void deleteById(SerialNoComposite id) {
    helperDeleteById(id);
  }

  @Override
  public SerialNo findById(SerialNoComposite id) {
    return helperFindById(id);
  }

  @Override
  public SerialNo update(SerialNo u) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<SerialNo> findAll() {
    return helperFindAll();
  }

  @Override
  public SerialNo getSerialNoByInnoModelSerialNumber(String innoModelSerialNumber) {
    if (innoModelSerialNumber == null || innoModelSerialNumber.isEmpty()) {
      throw new BadRequestException("Model serial number cannot be blank", 400);
    }
    SerialNo lookedUpSerialNo = serialNoRepository
        .findTop1BySerialNoCompositeInnoModelSerialNumberOrderBySerialNoCompositeCrtupdDateDesc(
            innoModelSerialNumber);
    if (lookedUpSerialNo == null) {
      throw new NotFoundException("Serial number cannot be foudn for id : " + innoModelSerialNumber,
          400);
    }

    lookedUpSerialNo.setInnoModelCode(lookedUpSerialNo.getModel().getInnoModelCode());
    return lookedUpSerialNo;
  }

  @Override
  public Iterable<SerialNo> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Query string cannot be blank", 400);
    }
    Iterable<SerialNo> serialNos = serialNoRepository.findAll(predicate);
    if (serialNos == null) {
      throw new NotFoundException("No records found for the given query", 404);
    }
    return serialNos;
  }

  @Override
  public Page<SerialNo> findAllSerialNo(Pageable pageable) {
    Page<SerialNo> serialNoList = serialNoRepository.findAllSerialNo(pageable);
    if (serialNoList == null || !serialNoList.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);

    return serialNoList;
  }

  @Override
  public Page<SerialNo> findAllSerialNoForDataMovement(String recordStatus, Pageable pageable) {
    Page<SerialNo> serialNoList = null;
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.SERIAL_NO.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      serialNoList = serialNoRepository.findAllSerialNoForDataMovement(recordStatus,
          latestDateForEntity, pageable);
    } else {
      serialNoList = serialNoRepository.findAllSerialNoForDataMovement(recordStatus, pageable);
    }
    if (serialNoList == null || !serialNoList.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);

    return serialNoList;
  }

}
