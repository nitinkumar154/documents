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

import com.innoviti.emi.core.resource.TenureResource;
import com.innoviti.emi.entity.core.Tenure;
import com.innoviti.emi.service.core.TenureService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping(value = "/tenures")
public class TenureController {

  private static Logger logger = Logger.getLogger(TenureController.class);

  @Autowired
  private TenureService tenureService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Tenure tenure) {
    Tenure createdTenure = tenureService.create(tenure);
    TenureResource tenureResource = new TenureResource(createdTenure);
    return ResponseEntity.created(URI.create(tenureResource.getCreateCategoryLink())).build();
  }

  @GetMapping(value = "/{innoTenureCode}")
  public ResponseEntity<TenureResource> getTenureByInnoTenureCode(
      @PathVariable("innoTenureCode") String innoTenureCode) {

    Tenure lookedUpTenure = tenureService.findTenureByInnoTenureCode(innoTenureCode);

    TenureResource tenureResource = new TenureResource(lookedUpTenure);
    tenureResource.addSelfLink();

    return ResponseEntity.<TenureResource>ok(tenureResource);
  }

  @GetMapping(value = "/query")
  public ResponseEntity<List<TenureResource>> filterTenure(
      @QuerydslPredicate(root = Tenure.class) Predicate predicate) {
    Iterable<Tenure> tenures = tenureService.findAll(predicate);
    List<TenureResource> reources = new ArrayList<>();
    for (Tenure tenure : tenures) {
      TenureResource tenureResource = new TenureResource(tenure);
      tenureResource.addSelfLink();
      reources.add(tenureResource);
    }
    return ResponseEntity.<List<TenureResource>>ok(reources);
  }

  @GetMapping
  public ResponseEntity<PagedResources<TenureResource>> getAllTenures(Pageable pageable) {

    Page<Tenure> tenures = tenureService.findAllTenures(pageable);
    List<TenureResource> reources = new ArrayList<>();
    for (Tenure tenure : tenures) {
      TenureResource tenureResource = new TenureResource(tenure);
      tenureResource.addSelfLink();
      reources.add(tenureResource);
    }
    PageMetadata pageMetaData = new PageMetadata(tenures.getSize(), tenures.getNumber(),
        tenures.getTotalElements(), tenures.getTotalPages());
    PagedResources<TenureResource> pagedResource = new PagedResources<>(reources, pageMetaData);
    return ResponseEntity.<PagedResources<TenureResource>>ok(pagedResource);

  }

  @GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<TenureResource>> getAllTenuresForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {

    Page<Tenure> tenures = tenureService.findAllTenuresForDataMovement(recordStatus, pageable);
    List<TenureResource> reources = new ArrayList<>();
    for (Tenure tenure : tenures.getContent()) {
      TenureResource tenureResource = new TenureResource(tenure);
      tenureResource.addSelfLink();
      reources.add(tenureResource);
    }
    PageMetadata pageMetaData = new PageMetadata(tenures.getSize(), tenures.getNumber(),
        tenures.getTotalElements(), tenures.getTotalPages());
    PagedResources<TenureResource> pagedResource = new PagedResources<>(reources, pageMetaData);
    return ResponseEntity.<PagedResources<TenureResource>>ok(pagedResource);
  }

  @GetMapping(value = "/autoComplete")
  public ResponseEntity<List<TenureResource>> autoCompleteTenures(
      @RequestParam("term") String term) {

    logger.info("Entering autoCompleteTenures :: TenureController");
    List<Tenure> tenures = tenureService.autoCompleteTenures(term);
    List<TenureResource> resources = new ArrayList<>();
    for (Tenure tenure : tenures) {
      TenureResource tenureResource = new TenureResource(tenure);
      tenureResource.addSelfLink();
      resources.add(tenureResource);
    }
    logger.info("Exiting autoCompleteTenures :: TenureController");
    return ResponseEntity.<List<TenureResource>>ok(resources);
  }

}
