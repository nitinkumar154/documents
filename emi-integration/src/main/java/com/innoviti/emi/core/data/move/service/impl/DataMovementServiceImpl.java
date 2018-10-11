package com.innoviti.emi.core.data.move.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innoviti.emi.constant.SequenceType;
import com.innoviti.emi.core.data.move.service.DataMovementService;
import com.innoviti.emi.entity.core.DataMoveKeeper;
import com.innoviti.emi.repository.core.CategoryRepository;
import com.innoviti.emi.repository.core.IssuerBankRepository;
import com.innoviti.emi.repository.core.ManufacturerRepository;
import com.innoviti.emi.repository.core.ModelRepository;
import com.innoviti.emi.repository.core.ProductRepository;
import com.innoviti.emi.repository.core.SchemeModelRepository;
import com.innoviti.emi.repository.core.SchemeModelTerminalRepository;
import com.innoviti.emi.repository.core.SchemeRepository;
import com.innoviti.emi.repository.core.SerialNoRepository;
import com.innoviti.emi.repository.core.TenureRepository;
import com.innoviti.emi.service.core.DataMoveKeeperService;

@Service
public class DataMovementServiceImpl implements DataMovementService {

  private static final Logger LOGGER = Logger.getLogger(DataMovementServiceImpl.class);

  @Autowired
  ManufacturerRepository manufacturerRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  IssuerBankRepository issuerBankRepository;

  @Autowired
  TenureRepository tenureRepository;

  @Autowired
  SchemeRepository schemeRepository;

  @Autowired
  ModelRepository modelRepository;

  @Autowired
  SerialNoRepository serialNoRepository;

  @Autowired
  SchemeModelRepository schemeModelRepository;

  @Autowired
  SchemeModelTerminalRepository schemeModelTerminalRepository;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  @Override
  public void prepareEntriesForPublishing() {
    LOGGER.info("Entering prepareEntriesForPublishing :: DataMovementServiceImpl");
    Date latestDateForEntity = null;
    try {
      latestDateForEntity = manufacturerRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        manufacturerRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        manufacturerRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = productRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        productRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        productRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = categoryRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        categoryRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        categoryRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = issuerBankRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        issuerBankRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        issuerBankRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = tenureRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        tenureRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        tenureRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = schemeRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        schemeRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        schemeRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = modelRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        modelRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        modelRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = serialNoRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        serialNoRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        serialNoRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = schemeModelRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        schemeModelRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        schemeModelRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    try {
      latestDateForEntity = schemeModelTerminalRepository.getLatestRecordForPreparingData();
      if (latestDateForEntity != null) {
        schemeModelTerminalRepository.prepareDataForMovement(latestDateForEntity);
      } else {
        schemeModelTerminalRepository.prepareDataForMovement();
      }
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
    LOGGER.info("Exiting prepareEntriesForPublishing :: DataMovementServiceImpl");
  }

  @Override
  public void updateTimestampForMovedData() {
    Date latestDateForEntity = null;
    DataMoveKeeper d = new DataMoveKeeper();
    try {
      latestDateForEntity = manufacturerRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.MANUFACTURER.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = productRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.PRODUCT.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = categoryRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.CATEGORY.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = issuerBankRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.ISSUER_BANK.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = tenureRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.TENURE.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = schemeRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.SCHEME.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = modelRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.MODEL.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = serialNoRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.SERIAL_NO.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = schemeModelRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.SCHEME_MODEL.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }

    try {
      latestDateForEntity = schemeModelTerminalRepository.getLatestRecordForPreparingData();
      d.setTableName(SequenceType.SCHEME_MODEL_TERMINAL.getSequenceName());
      d.setTimeStamp(latestDateForEntity);
      dataMoveKeeperService.create(d);
    } catch (Exception e) {
      LOGGER.error(e);
      throw e;
    }
  }

}
