package com.innoviti.emi.core.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innoviti.emi.core.resource.ModelResource;
import com.innoviti.emi.core.resource.ModelViewResource;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.ModelView;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.service.core.ModelService;
import com.innoviti.emi.service.core.ModelViewService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping(value = "/models")
public class ModelController {

  private static Logger logger = Logger.getLogger(ModelController.class);

  @Autowired
  private ModelService modelService;

  @Autowired
  private ModelViewService modelViewService;

  @PersistenceContext
  private EntityManager entityManager;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Model model) {
    Model modelProduct = modelService.create(model);
    ModelResource productResource = new ModelResource(modelProduct);

    return ResponseEntity.created(URI.create(productResource.getCreateModelLink())).build();
  }

  @GetMapping(value = "/{innoModelCode}")
  public ResponseEntity<ModelResource> getModelById(
      @PathVariable("innoModelCode") String innoModelCode) {

    Model model = modelService.findModelByInnoModelCode(innoModelCode);

    ModelResource modelResource = new ModelResource(model);
    modelResource.addSelfLink();

    return ResponseEntity.<ModelResource>ok(modelResource);
  }

  @GetMapping(value = "/query")
  public ResponseEntity<List<ModelResource>> getModel(
      @QuerydslPredicate(root = Model.class) Predicate predicate) {
    Iterable<Model> models = modelService.findAll(predicate);
    List<ModelResource> resources = new ArrayList<>();
    for (Model model : models) {
      ModelResource modelResource = new ModelResource(model);
      modelResource.addSelfLink();
      resources.add(modelResource);
    }
    return ResponseEntity.<List<ModelResource>>ok(resources);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<ModelResource>> getAllModel(Pageable pageable) {
    Page<Model> modelPage = modelService.findAllModels(pageable);
    List<ModelResource> resources = new ArrayList<>();
    for (Model model : modelPage.getContent()) {
      ModelResource modelResource = new ModelResource(model);
      modelResource.addSelfLink();
      resources.add(modelResource);
    }
    PageMetadata pageMetaData = new PageMetadata(modelPage.getSize(), modelPage.getNumber(),
        modelPage.getTotalElements(), modelPage.getTotalPages());
    PagedResources<ModelResource> pagedResource = new PagedResources<>(resources, pageMetaData);
    return ResponseEntity.<PagedResources<ModelResource>>ok(pagedResource);
  }

  @GetMapping(value = "/modelList")
  public ResponseEntity<List<ModelResource>> getAllModelFromModelCodeList(
      @RequestParam("modelCodeList") List<String> modelCodeList) {

    Iterable<Model> models = modelService.findAllModelFromModelCodeList(modelCodeList);
    List<ModelResource> resources = new ArrayList<>();
    for (Model model : models) {
      ModelResource modelResource = new ModelResource(model);
      modelResource.addSelfLink();
      resources.add(modelResource);
    }
    return ResponseEntity.<List<ModelResource>>ok(resources);
  }

  @GetMapping(value = "/allFromFilter", produces = MediaType.APPLICATION_JSON_VALUE)
  public HttpEntity<PagedResources<ModelResource>> getAllModelFromModelsFromFilter(
      @RequestParam("productCode") String productCode,
      @RequestParam("manufacturerCode") String manufacturerCode,
      @RequestParam("categoryCode") String categoryCode, Pageable pageable) {

    Page<Model> models =
        modelService.findAllModelsFromFilter(productCode, manufacturerCode, categoryCode, pageable);
    List<ModelResource> modalResourceList = new ArrayList<>();
    for (Model model : models.getContent()) {
      ModelResource modelResource = new ModelResource(model);
      modelResource.addSelfLink();
      modalResourceList.add(modelResource);
    }
    PageMetadata pageMetaData = new PageMetadata(models.getSize(), models.getNumber(),
        models.getTotalElements(), models.getTotalPages());
    PagedResources<ModelResource> pagedResource =
        new PagedResources<>(modalResourceList, pageMetaData);
    return ResponseEntity.<PagedResources<ModelResource>>ok(pagedResource);
  }

  @GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<ModelResource>> getAllModelsForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {

    Page<Model> modelPage = modelService.findAllModelsForDataMovement(recordStatus, pageable);
    List<ModelResource> resources = new ArrayList<>();
    for (Model model : modelPage.getContent()) {
      ModelResource modelResource = new ModelResource(model);
      modelResource.addSelfLink();
      resources.add(modelResource);
    }
    PageMetadata pageMetaData = new PageMetadata(modelPage.getSize(), modelPage.getNumber(),
        modelPage.getTotalElements(), modelPage.getTotalPages());
    PagedResources<ModelResource> pagedResource = new PagedResources<>(resources, pageMetaData);
    return ResponseEntity.<PagedResources<ModelResource>>ok(pagedResource);
  }

  @GetMapping(value = "/autoComplete")
  public ResponseEntity<List<ModelViewResource>> autoCompleteModels(
      @RequestParam("term") String term) {

    logger.info("Entering autoCompleteModels :: ModelController");
    List<ModelView> models = modelViewService.findAllModelNames(term);
    List<ModelViewResource> resources = new ArrayList<>();
    for (ModelView modelView : models) {
      ModelViewResource modelResource = new ModelViewResource(modelView);
      resources.add(modelResource);
    }
    logger.info("Exiting autoCompleteModels :: ModelController");
    return ResponseEntity.<List<ModelViewResource>>ok(resources);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Transactional(transactionManager = "transactionManager")
  @GetMapping(value = "/complete")
  public List<Model> searchByKeyword(@RequestParam("keyword") String keywords) {
    final FullTextEntityManager fullTextEntityManager =
        Search.getFullTextEntityManager(entityManager);

    // Prepare a search query builder
    final QueryBuilder modelQueryBuilder =
        fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Model.class).get();



    final QueryBuilder schemeQueryBuilder =
        fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Scheme.class).get();


    org.apache.lucene.search.Query query =
        modelQueryBuilder.keyword().onFields("modelDisplayNo").matching(keywords).createQuery();


    org.apache.lucene.search.Query schemeQuery =
        schemeQueryBuilder.keyword().onFields("schemeDisplayName").matching(keywords).createQuery();

    BooleanJunction boolJunc = modelQueryBuilder.bool();
    boolJunc = boolJunc.should(query).should(schemeQuery);

    org.apache.lucene.search.Query mainLuceneQuery = boolJunc.createQuery();

    FullTextQuery jpaQuery =
        fullTextEntityManager.createFullTextQuery(mainLuceneQuery, Model.class, Scheme.class);

    // execute search and return results (sorted by relevance as default)
    return jpaQuery.getResultList();
  }

}
