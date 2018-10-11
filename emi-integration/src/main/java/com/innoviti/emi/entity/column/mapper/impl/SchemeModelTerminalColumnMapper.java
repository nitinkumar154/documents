package com.innoviti.emi.entity.column.mapper.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.innoviti.emi.constant.OnUsOffUsStatus;
import com.innoviti.emi.constant.UTIDUpdateStatus;
import com.innoviti.emi.constant.UpdateReason;
import com.innoviti.emi.entity.column.mapper.ColumnMapper;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.core.SchemeModelTerminalComposite;
import com.innoviti.emi.entity.master.DealerProductMaster;
import com.innoviti.emi.entity.master.ModelProductMaster;
import com.innoviti.emi.entity.master.SchemeDealerMaster;
import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.model.PortalResponseModel;
import com.innoviti.emi.model.UtidBtid;
import com.innoviti.emi.model.UtidListModel;
import com.innoviti.emi.repository.core.ModelRepository;
import com.innoviti.emi.repository.core.ProductRepository;
import com.innoviti.emi.repository.core.SchemeModelRepository;
import com.innoviti.emi.repository.core.SchemeRepository;
import com.innoviti.emi.repository.master.DealerManufacturerMasterRepository;
import com.innoviti.emi.repository.master.DealerProductMasterRepository;
import com.innoviti.emi.repository.master.ModelProductMasterRepository;
import com.innoviti.emi.repository.master.SchemeDealerMasterRepository;
import com.innoviti.emi.repository.master.SchemeMasterRepository;
import com.innoviti.emi.service.core.DefaultDataService;
import com.innoviti.emi.service.core.ProductService;

@Component
public class SchemeModelTerminalColumnMapper
    implements ColumnMapper<Integer, Deque<SchemeModelTerminal>> {

  private static final Logger logger =
      LoggerFactory.getLogger(SchemeModelTerminalColumnMapper.class);

  @Autowired
  private SchemeModelRepository schemeModelRepository;

  @Autowired
  private DealerProductMasterRepository dealerProductMasterRepository;

  @Autowired
  private ModelRepository modelRepository;

  @Autowired
  private ProductRepository productRepository;

  @Value("${portal.unipaynext.url}")
  private String portalUrl;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ProductService productService;

  @Autowired
  private SchemeMasterRepository schemeMasterRepository;

  @Autowired
  private SchemeRepository schemeRepository;

  @Autowired
  private ModelProductMasterRepository modelProductMasterRepository;

  @Autowired
  private DealerManufacturerMasterRepository dealerManufacturerMasterRepository;

  @Autowired
  private DefaultDataService defaultDataService;
  
  @Autowired
  private SchemeDealerMasterRepository schemeDealerMasterRepository;

  @Override
  public Deque<SchemeModelTerminal> mapColumn(Integer supplierId) {
    UtidListModel utidListModel = getUtidByDealerId(supplierId);
    if (utidListModel == null || utidListModel.getUtidBtidList() == null
        || utidListModel.getUtidBtidList().isEmpty()) {
      throw new NotFoundException("No Utids : " + supplierId);
    }
    List<DealerProductMaster> dealerProductMasterList =
        dealerProductMasterRepository.findByDealerProductMasterCompositeSupplierId(supplierId);
    if (dealerProductMasterList == null || dealerProductMasterList.isEmpty()) {
      throw new NotFoundException("No dealer product mapping : " + supplierId);
    }
    List<String> productCodeList = new ArrayList<>();
    for(DealerProductMaster dealerProductMaster : dealerProductMasterList){
      String productCode = dealerProductMaster.getDealerProductMasterComposite().getCode();
      if(!productCodeList.contains(productCode)){
        productCodeList.add(productCode);
      }
    }
    List<SchemeModel> schemeModels = new ArrayList<>();
    List<Product> products = getProductByProductCodes(productCodeList);

    List<Integer> manufactuerIds =
        dealerManufacturerMasterRepository.findManufacturerBySupplierId(supplierId);
    if (manufactuerIds != null && !manufactuerIds.isEmpty()) {
      List<String> strManufacturerIds =
          manufactuerIds.stream().map(s -> String.valueOf(s)).collect(Collectors.toList());
      List<Model> models = getModelsByProductCodesAndManufacturerIds(products, strManufacturerIds);
      if (!models.isEmpty()) {
        schemeModels.addAll(getSchemeModelMapping(models));
      }

    }
    List<SchemeMaster> generalSchemes = schemeMasterRepository.findGeneralSchemes();
    List<SchemeModel> generalSchemeModelList = getSchemeModelByMasterSchemes(generalSchemes);
    if (generalSchemeModelList != null && !generalSchemeModelList.isEmpty()) {
      schemeModels.addAll(generalSchemeModelList);
    }
    //Invoice financing scheme
    List<SchemeMaster> invoiceFinancingSchemes = new ArrayList<>();
    for (Product product : products) {
      List<SchemeMaster> schemeMasterList = schemeMasterRepository
          .findInvoiceFinancingSchemesByProduct(product.getBajajProductTypeCode());
      invoiceFinancingSchemes.addAll(schemeMasterList);
    }
    if(invoiceFinancingSchemes != null && !invoiceFinancingSchemes.isEmpty()){
      List<SchemeMaster> dealerMappedSchemes = new ArrayList<>();
      Pageable topOne = new PageRequest(0, 1);
      for(SchemeMaster schemeMaster : invoiceFinancingSchemes){
        Integer schemeId = schemeMaster.getSchemeMasterComposite().getSchemeId();
        List<SchemeDealerMaster> schemeDealerMasters = schemeDealerMasterRepository.findLatestBySchemeIdAndSupplierId(schemeId, supplierId, topOne);
        if(schemeDealerMasters != null && !schemeDealerMasters.isEmpty()){
          dealerMappedSchemes.add(schemeMaster);
        }
      }
      List<SchemeModel> schemeModelList = getSchemeModelByMasterSchemes(dealerMappedSchemes);
      if (schemeModelList != null && !schemeModelList.isEmpty()) {
        schemeModels.addAll(schemeModelList);
      }
    }
    // Persist btid in portal config db
    for (UtidBtid utid : utidListModel.getUtidBtidList()) {
      utid.setInnoSchemeModelCode(defaultDataService.getDefaultSchemeModel()
          .getSchemeModelComposite().getInnoSchemeModelCode());
      utid.setOnUsOffUs(OnUsOffUsStatus.OFF_US.toString());
      boolean isBtidPersisted = persistBtid(utid);
      if (!isBtidPersisted) {
        logger.info("Utid not persisted {} ", utid);
      }
    }
    return createSchemeModelTerminals(schemeModels, utidListModel);

  }

  private List<SchemeModel> getSchemeModelByMasterSchemes(List<SchemeMaster> schemeMaster) {
    if (schemeMaster != null && !schemeMaster.isEmpty()) {
      List<Scheme> schemeList = getSchemesBySchemeId(schemeMaster);
      if (!schemeList.isEmpty()) {
        return getSchemeModelMapplingBySchemes(schemeList);
      }
    }
    return new ArrayList<>();
  }

  private Deque<SchemeModelTerminal> createSchemeModelTerminals(List<SchemeModel> schemeModels,
      UtidListModel utidListModel) {
    Deque<SchemeModelTerminal> schemeModelTerminals = new ArrayDeque<>();
    for (SchemeModel schemeModel : schemeModels) {
      String productTypeCode = schemeModel.getModel().getProduct().getBajajProductTypeCode();
      for (UtidBtid utid : utidListModel.getUtidBtidList()) {
        SchemeModelTerminal schemeModelTerminal = new SchemeModelTerminal();
        SchemeModelTerminalComposite schemeModelTerminalComposite =
            new SchemeModelTerminalComposite();
        schemeModelTerminalComposite.setCrtupdDate(new Date());
        schemeModelTerminalComposite.setSchemeModel(schemeModel);
        schemeModelTerminalComposite.setUtid(utid.getUtid());
        schemeModelTerminal.setSchemeModelTerminalComposite(schemeModelTerminalComposite);
        schemeModelTerminal
            .setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
        schemeModelTerminal.setCrtupdStatus("N");
        schemeModelTerminal.setCrtupdUser("Batch");
        schemeModelTerminal.setDealerId(String.valueOf(utidListModel.getDealerId()));
        schemeModelTerminal.setIssuerCustomField("NA");
        schemeModelTerminal.setIssuerSchemeTerminalSyncStatus(UTIDUpdateStatus.NOT_SENT);
        schemeModelTerminal.setRecordActive(true);
        schemeModelTerminal.setBajajProductTypeCode(productTypeCode);
        schemeModelTerminal.setOnUsOffUs(OnUsOffUsStatus.OFF_US);
        schemeModelTerminals.add(schemeModelTerminal);
      }
    }
    return schemeModelTerminals;
  }

  private List<SchemeModel> getSchemeModelMapping(List<Model> modelList) {
    List<SchemeModel> schemeModelList = new ArrayList<>();
    for(Model model : modelList){
      SchemeModel schemeModel = schemeModelRepository.findTop1ByModelOrderBySchemeModelCompositeCrtupdDateDesc(model);
      if(schemeModel != null){
        schemeModelList.add(schemeModel);
      }
    }
//    if (modelList.size() > 30) {
//      schemeModelList = new ArrayList<>();
//      Iterable<List<Model>> modelSubList = Iterables.partition(modelList, modelList.size() / 30);
//      for (List<Model> models : modelSubList) {
//        List<SchemeModel> schemeModels = schemeModelRepository.findByModelIn(models);
//        if (schemeModels == null || schemeModels.isEmpty()) {
//          continue;
//        }
//        schemeModelList.addAll(schemeModels);
//      }
//    } else {
//      schemeModelList = schemeModelRepository.findByModelIn(modelList);
//    }
    return schemeModelList;
  }

  private List<SchemeModel> getSchemeModelMapplingBySchemes(List<Scheme> schemeList) {
    List<SchemeModel> schemeModelList = new ArrayList<>();
    for(Scheme scheme : schemeList){
      SchemeModel schemeModel = schemeModelRepository.findTop1BySchemeOrderBySchemeModelCompositeCrtupdDateDesc(scheme);
      if(schemeModel != null){
        schemeModelList.add(schemeModel);
      }
    }
//    if (schemeList.size() > 30) {
//      schemeModelList = new ArrayList<>();
//      Iterable<List<Scheme>> schemeSubList =
//          Iterables.partition(schemeList, schemeList.size() / 30);
//      for (List<Scheme> schemes : schemeSubList) {
//        List<SchemeModel> schemeModels = schemeModelRepository.findBySchemeIn(schemes);
//        if (schemeModels == null || schemeModels.isEmpty()) {
//          continue;
//        }
//        schemeModelList.addAll(schemeModels);
//      }
//    } else {
//      schemeModelList = schemeModelRepository.findBySchemeIn(schemeList);
//    }
    return schemeModelList;
  }

  private List<Product> getProductByProductCodes(List<String> productCodeList) {
    Pageable topOne = new PageRequest(0, 1);
    List<Product> productList = new ArrayList<>();
    for (String productCode : productCodeList) {
      List<Product> products = productRepository.getProductByBajajProductAndStatus(productCode, topOne);
      Product product = null;
      if (products == null || products.isEmpty()) {
        product = saveProduct(productCode);
      }
      else{
        product = products.get(0);
      }
      productList.add(product);
    }
    return productList;
  }

  private List<Model> getModelsByProductCodesAndManufacturerIds(List<Product> products,
      List<String> strManufacturerIds) {
    List<Model> models = new ArrayList<>();
    List<ModelProductMaster> productModels = new ArrayList<>();
    for (Product product : products) {
      List<ModelProductMaster> fetchedProductModels = modelProductMasterRepository
          .findLatestRecordByProductCode(product.getBajajProductTypeCode());
      if (fetchedProductModels != null && !fetchedProductModels.isEmpty()) {
        productModels.addAll(fetchedProductModels);
      }
    }
    for (ModelProductMaster modelProductMaster : productModels) {
      Integer modelId = modelProductMaster.getModelProductMasterComposite().getModelId();
      Model model = modelRepository
          .findLatestModelByBajajModelCode(String.valueOf(modelId));
      if (model != null) {
        String manufacturerCode = model.getManufacturer().getBajajManufacturerCode();
        if (strManufacturerIds.contains(manufacturerCode)) {
          models.add(model);
        }
      }
    }
    return models;
  }

  private Product saveProduct(String productCode) {
    Product product = new Product();
    product.setInnoProductTypeCode(productCode);
    product.setBajajProductTypeCode(productCode);
    product.setCrtupdReason(UpdateReason.BAJAJ_BATCH_UPDATE.getRecordUpdateReason());
    product.setCrtupdStatus("N");
    product.setCrtupdUser("Batch");
    product.setRecordActive(true);
    product.setIssuerProductTypeName(productCode);
    return productService.create(product);
  }

  private List<Scheme> getSchemesBySchemeId(List<SchemeMaster> schemeMasters) {
    List<Scheme> schemes = new ArrayList<>();
    Pageable topOne = new PageRequest(0, 1);
    for (SchemeMaster schemeMaster : schemeMasters) {
      String schemeId = String.valueOf(schemeMaster.getSchemeMasterComposite().getSchemeId());
      List<Scheme> schemeList = getLatestSchemeById(schemeId, topOne);
      if (schemeList == null || schemeList.isEmpty()) {
        continue;
      }
      schemes.add(schemeList.get(0));
    }

    return schemes;
  }
  private List<Scheme> getLatestSchemeById(String schemeId, Pageable page){
    return schemeRepository.findLatestSchemeByBajajSchemeCode(String.valueOf(schemeId), page);
  }
  private boolean persistBtid(UtidBtid utidBid) {
    String url = portalUrl + "/createSchemeModelMapping";
    utidBid.setFlag("create");
    try {
      ResponseEntity<PortalResponseModel> responseEntity =
          restTemplate.postForEntity(url, utidBid, PortalResponseModel.class);
      HttpStatus httpStatus = responseEntity.getStatusCode();
      if (httpStatus == HttpStatus.OK) {
        PortalResponseModel portalResponseModel = responseEntity.getBody();
        if ("00".equals(portalResponseModel.getResponseCode())
            || "422".equals(portalResponseModel.getResponseCode())) {
          return true;
        }
      }
    } catch (HttpClientErrorException e) {
      logger.error("HttpClientErrorException", e);
      logger.debug(e.getResponseBodyAsString());
    } catch (Exception e) {
      logger.error("Web service call exception", e);

    }
    return false;
  }

  private UtidListModel getUtidByDealerId(Integer supplierId) {
    String url = portalUrl + "/rest/UTIDListForDealers?dealerId=" + supplierId;
    try {
      ResponseEntity<UtidListModel> responseEntity =
          restTemplate.getForEntity(url, UtidListModel.class);
      HttpStatus httpStatus = responseEntity.getStatusCode();
      if (httpStatus == HttpStatus.OK) {
        return responseEntity.getBody();
      }
    } catch (HttpClientErrorException e) {
      logger.error("HttpClientErrorException", e);
      logger.debug(e.getResponseBodyAsString());
    } catch (Exception e) {
      logger.error("Web service call exception", e);
    }
    return null;
  }
}
