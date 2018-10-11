package com.innoviti.emi.core.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innoviti.emi.core.resource.SchemeModelResource;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.service.core.SchemeModelService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping(value = "/schemeModels")
public class SchemeModelController {

  private static Logger logger = Logger.getLogger(SchemeModelController.class);

  @Autowired
  private SchemeModelService schemeModelService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody SchemeModel schemeModel) {
    SchemeModel createdSchemeModel = schemeModelService.create(schemeModel);
    SchemeModelResource schemeModelResource = new SchemeModelResource(createdSchemeModel);

    return ResponseEntity.created(URI.create(schemeModelResource.getCreateSchemeModelLink()))
        .build();
  }

  @GetMapping(value = "/{innoSchemeModelCode}")
  public ResponseEntity<SchemeModelResource> getSchemeModelById(
      @PathVariable("innoSchemeModelCode") String innoSchemeModelCode) {
    SchemeModel schemeModels =
        schemeModelService.findSchemeModelByInnoSchemeModelCode(innoSchemeModelCode);

    SchemeModelResource schemeModelResource = new SchemeModelResource(schemeModels);
    schemeModelResource.addSelfLink();

    return ResponseEntity.<SchemeModelResource>ok(schemeModelResource);
  }

  @GetMapping(value = "/query")
  public ResponseEntity<List<SchemeModelResource>> getSchemeModel(
      @QuerydslPredicate(root = SchemeModel.class) Predicate predicate) {
    Iterable<SchemeModel> SchemeModels = schemeModelService.findAll(predicate);
    List<SchemeModelResource> resources = new ArrayList<>();
    for (SchemeModel SchemeModel : SchemeModels) {
      SchemeModelResource SchemeModelResource = new SchemeModelResource(SchemeModel);
      SchemeModelResource.addSelfLink();
      resources.add(SchemeModelResource);
    }
    return ResponseEntity.<List<SchemeModelResource>>ok(resources);
  }

  @GetMapping
  public ResponseEntity<PagedResources<SchemeModelResource>> getSchemeModelList(Pageable pageable,
      @RequestParam("bajajFlag") String bajajFlag) {

    Page<SchemeModel> schemeModels = schemeModelService.findAllSchemeModel(bajajFlag, pageable);

    List<SchemeModelResource> resources = new ArrayList<>();
    for (SchemeModel schemeModel : schemeModels.getContent()) {
      SchemeModelResource SchemeModelResource = new SchemeModelResource(schemeModel);
      SchemeModelResource.addSelfLink();
      resources.add(SchemeModelResource);
    }
    PageMetadata pageMetaData = new PageMetadata(schemeModels.getSize(), schemeModels.getNumber(),
        schemeModels.getTotalElements(), schemeModels.getTotalPages());
    PagedResources<SchemeModelResource> pagedResource =
        new PagedResources<>(resources, pageMetaData);
    return ResponseEntity.<PagedResources<SchemeModelResource>>ok(pagedResource);
  }

  @GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<SchemeModelResource>> getSchemeModelListForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {
    logger.info("ENTERING getSchemeModelListForDataMovement :: SchemeModelController");
    Page<SchemeModel> schemeModels =
        schemeModelService.findAllSchemeModelsForDataMovement(recordStatus, pageable);
    List<SchemeModelResource> resources = new ArrayList<>();
    for (SchemeModel SchemeModel : schemeModels.getContent()) {
      SchemeModelResource SchemeModelResource = new SchemeModelResource(SchemeModel);
      SchemeModelResource.addSelfLink();
      resources.add(SchemeModelResource);
    }
    PageMetadata pageMetadata = new PageMetadata(schemeModels.getSize(), schemeModels.getNumber(),
        schemeModels.getTotalElements(), schemeModels.getTotalPages());
    PagedResources<SchemeModelResource> pagedResources =
        new PagedResources<>(resources, pageMetadata);
    logger.info("EXITING getSchemeModelListForDataMovement :: SchemeModelController");
    return ResponseEntity.<PagedResources<SchemeModelResource>>ok(pagedResources);
  }
  @GetMapping(value="/getTenureNameBySchemeModelCode")
  public ResponseEntity<String> getTenureNameBySchemeModelCode(@RequestParam("innoSchemeModelCode") String  innoSchemeModelCode){
    logger.info("ENTERING getTenureNameBySchemeModelCode :: SchemeModelController With innoSchemeModelCode="+innoSchemeModelCode);
    SchemeModel schemeModels =
        schemeModelService.findSchemeModelByInnoSchemeModelCode(innoSchemeModelCode);
    String tenureName=schemeModels.getScheme().getTenure().getTenureDisplayName();
    logger.info("EXITTING getTenureNameBySchemeModelCode :: SchemeModelController With TenureName="+innoSchemeModelCode);
    return ResponseEntity.<String>ok(tenureName);
  }
}
