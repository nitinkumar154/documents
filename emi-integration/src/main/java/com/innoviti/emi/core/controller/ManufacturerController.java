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
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innoviti.emi.core.resource.ManufacturerResource;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.service.core.ManufacturerService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {

  private static Logger logger = Logger.getLogger(CategoryController.class);

  @Autowired
  ManufacturerService manufacturerService;

  @PostMapping
  public HttpEntity<?> createManufacturer(@RequestBody Manufacturer manufacturer) {
    Manufacturer createdManufacturer = manufacturerService.create(manufacturer);
    ManufacturerResource manufacturerResource = new ManufacturerResource(createdManufacturer);

    return ResponseEntity.created(URI.create(manufacturerResource.createManufacturerLink()))
        .build();
  }

  @GetMapping(value = "/{innoManufacturerCode}")
  public HttpEntity<ManufacturerResource> getListOfManufacturer(
      @PathVariable("innoManufacturerCode") String innoManufacturerCode) {

    Manufacturer lookedUpManufacturerList =
        manufacturerService.findManufacturerByInnoManufacturerCode(innoManufacturerCode);

    ManufacturerResource manufacturerResource = null;
    if (lookedUpManufacturerList != null) {
      manufacturerResource = new ManufacturerResource(lookedUpManufacturerList);
      manufacturerResource.createSelfLink();
    }
    return ResponseEntity.<ManufacturerResource>ok(manufacturerResource);
  }

  @GetMapping(value = "/query")
  public ResponseEntity<List<ManufacturerResource>> getIssuerBank(
      @QuerydslPredicate(root = Manufacturer.class) Predicate predicate) {
    Iterable<Manufacturer> manufacturers = manufacturerService.findAll(predicate);
    List<ManufacturerResource> manufacturerResources = new ArrayList<>();
    for (Manufacturer manufacturer : manufacturers) {
      ManufacturerResource manufacturerResource = new ManufacturerResource(manufacturer);
      manufacturerResource.createSelfLink();
      manufacturerResources.add(manufacturerResource);
    }
    return ResponseEntity.<List<ManufacturerResource>>ok(manufacturerResources);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public HttpEntity<PagedResources<ManufacturerResource>> findAllManufacturer(Pageable pageable) {

    Page<Manufacturer> manufacturerPage = manufacturerService.findAllManufacturers(pageable);

    List<ManufacturerResource> manufacturerResourceList = new ArrayList<>();
    for (Manufacturer manufacturer : manufacturerPage.getContent()) {
      ManufacturerResource manufacturerResource = new ManufacturerResource(manufacturer);
      manufacturerResource.createSelfLink();
      manufacturerResourceList.add(manufacturerResource);
    }
    PageMetadata pageMetaData =
        new PageMetadata(manufacturerPage.getSize(), manufacturerPage.getNumber(),
            manufacturerPage.getTotalElements(), manufacturerPage.getTotalPages());
    PagedResources<ManufacturerResource> pagedResource =
        new PagedResources<>(manufacturerResourceList, pageMetaData);
    return ResponseEntity.<PagedResources<ManufacturerResource>>ok(pagedResource);
  }

  @GetMapping(value = "/allForDataMovement")
  public HttpEntity<PagedResources<ManufacturerResource>> getAllManufacturerForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {

    Page<Manufacturer> manufacturerPage =
        manufacturerService.findAllManufacturersForDataMovement(recordStatus, pageable);

    List<ManufacturerResource> manufacturerResourceList = new ArrayList<>();
    for (Manufacturer manufacturer : manufacturerPage.getContent()) {
      ManufacturerResource manufacturerResource = new ManufacturerResource(manufacturer);
      manufacturerResource.createSelfLink();
      manufacturerResourceList.add(manufacturerResource);
    }
    PageMetadata pageMetaData =
        new PageMetadata(manufacturerPage.getSize(), manufacturerPage.getNumber(),
            manufacturerPage.getTotalElements(), manufacturerPage.getTotalPages());
    PagedResources<ManufacturerResource> pagedResource =
        new PagedResources<>(manufacturerResourceList, pageMetaData);
    return ResponseEntity.<PagedResources<ManufacturerResource>>ok(pagedResource);
  }

  @GetMapping(value = "/autoComplete")
  public ResponseEntity<List<ManufacturerResource>> autoCompleteManufacturers(
      @RequestParam("term") String term) {

    logger.info("Entering autoCompleteManufacturers :: ManufacturerController");
    List<Manufacturer> manufacturers = manufacturerService.autoCompleteManufacturers(term);
    List<ManufacturerResource> resources = new ArrayList<>();
    for (Manufacturer manufacturer : manufacturers) {
      ManufacturerResource manufacturerResource = new ManufacturerResource(manufacturer);
      manufacturerResource.createSelfLink();
      resources.add(manufacturerResource);
    }
    logger.info("Exiting autoCompleteManufacturers :: ManufacturerController");
    return ResponseEntity.<List<ManufacturerResource>>ok(resources);
  }

}
