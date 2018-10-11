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
import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.ModelComposite;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.repository.core.ModelRepository;
import com.innoviti.emi.service.CrudServiceHelper;
import com.innoviti.emi.service.core.CategoryService;
import com.innoviti.emi.service.core.DataMoveKeeperService;
import com.innoviti.emi.service.core.ManufacturerService;
import com.innoviti.emi.service.core.ModelService;
import com.innoviti.emi.service.core.ProductService;
import com.innoviti.emi.service.core.SequenceGeneratorService;
import com.innoviti.emi.util.Util;
import com.querydsl.core.types.Predicate;

@Service
@Transactional(transactionManager = "transactionManager")
public class ModelServiceImpl extends CrudServiceHelper<Model, ModelComposite>
    implements ModelService {

  private ModelRepository modelRepository;

  @Autowired
  SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  ProductService productService;

  @Autowired
  CategoryService categoryService;

  @Autowired
  ManufacturerService manufacturerService;

  @Autowired
  DataMoveKeeperService dataMoveKeeperService;

  private static final String SEQ_NAME = "Model";
  private static final String IDENTIFIER = "MOD";
  private static final int INCREMENT_VAL = 0;

  @Autowired
  public ModelServiceImpl(ModelRepository modelRepository) {
    super(modelRepository);
    this.modelRepository = modelRepository;

  }

  @Override
  public Model create(Model u) {

    String innoProductTypeCode = u.getProductCode();
    Product product = productService.findProductByInnoProductTypeCode(innoProductTypeCode);
    u.setProduct(product);
    u.setProductCode(innoProductTypeCode);

    String innoCategoryCode = u.getInnoCategoryCode();
    Category category = categoryService.findCategoryByInnoCategoryCode(innoCategoryCode);
    u.setCategory(category);
    u.setInnoCategoryCode(innoCategoryCode);

    String innoManufacturerCode = u.getInnoManufacturerCode();
    Manufacturer manufacturer =
        manufacturerService.findManufacturerByInnoManufacturerCode(innoManufacturerCode);
    u.setManufacturer(manufacturer);
    u.setInnoManufacturerCode(innoManufacturerCode);

    ModelComposite modelComposite = new ModelComposite();
    if (u.getInnoModelCode() == null) {
      if (!modelExists(u.getModelDisplayNo()))
        throw new AlreadyMappedException(
            "Requested Model -> " + u.getModelDisplayNo() + " already exists", 422);
      int seqNum = (int) sequenceGeneratorService.getSeqNumber(SEQ_NAME);
      String newInsertId = String.valueOf(INCREMENT_VAL + seqNum);
      String innoModelCode = IDENTIFIER + Util.getLeftPaddedValueZeroFilled(newInsertId, 10);

      modelComposite.setInnoModelCode(innoModelCode);
    } else {
      modelComposite.setInnoModelCode(u.getInnoModelCode());
    }
    modelComposite.setCrtupdDate(u.getCrtupdDate());
    u.setModelComposite(modelComposite);
    return helperCreate(u);
  }

  private boolean modelExists(String modelDisplayName) {
    if (modelDisplayName == null)
      throw new BadRequestException("Requested field should not be null !!");
    return modelRepository.findModelByName(modelDisplayName.trim()).isEmpty();
  }

  @Override
  public void deleteById(ModelComposite id) {
    helperDeleteById(id);
  }

  @Override
  public Model findById(ModelComposite id) {
    return helperFindById(id);
  }

  @Override
  public Model update(Model u) {
    return null;
  }

  @Override
  public List<Model> findAll() {
    return helperFindAll();
  }

  @Override
  public Model findModelByInnoModelCode(String innoModelCode) {
    if (innoModelCode == null || innoModelCode.isEmpty())
      throw new BadRequestException("Requested id cannot be blank", 400);

    Model lookedUpModel = modelRepository
        .findTop1ByModelCompositeInnoModelCodeOrderByModelCompositeCrtupdDateDesc(innoModelCode);
    if (lookedUpModel == null) {
      throw new NotFoundException("Model not found for id : " + innoModelCode, 404);
    }

    return lookedUpModel;
  }

  @Override
  public Iterable<Model> findAll(Predicate predicate) {
    if (predicate == null) {
      throw new BadRequestException("Query string cannot be blank", 400);
    }
    Iterable<Model> models = modelRepository.findAll(predicate);
    if (models == null) {
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);
    }
    return models;
  }

  @Override
  public Page<Model> findAllModels(Pageable pageable) {
    Page<Model> modelList = null;
    modelList = modelRepository.findAllModels(pageable);
    if (modelList == null || !modelList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return modelList;
  }

  @Override
  public Iterable<Model> findAllModelFromModelCodeList(List<String> modelCodeList) {
    Iterable<Model> modelList = null;
    modelList = modelRepository.findAllModels(modelCodeList);
    if (modelList == null || !modelList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return modelList;
  }

  @Override
  public Page<Model> findAllModelsFromFilter(String productCode, String manufacturerCode,
      String categoryCode, Pageable pageable) {

    Page<Model> modelList = null;
    modelList = modelRepository.findAllModelsByFilter(productCode, manufacturerCode, categoryCode,
        pageable);
    if (modelList == null || !modelList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return modelList;
  }

  @Override
  public Page<Model> findAllModelsForDataMovement(String recordStatus, Pageable pageable) {
    Page<Model> modelList = null;
    Date latestDateForEntity =
        dataMoveKeeperService.findById(SequenceType.MODEL.getSequenceName()).getTimeStamp();
    if (latestDateForEntity != null) {
      modelList =
          modelRepository.findAllModelsForDataMovement(recordStatus, latestDateForEntity, pageable);
    } else {
      modelList = modelRepository.findAllModelsForDataMovement(recordStatus, pageable);
    }
    if (modelList == null || !modelList.iterator().hasNext())
      throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND, 404);

    return modelList;
  }

  @Override
  public List<Model> autoCompleteModels(String term) {
    List<Model> models = modelRepository.findAllModelsWithTerm(term);
    if (models == null || !models.iterator().hasNext())
      throw new NotFoundException("No records found for the given query", 404);
    return models;
  }

}
