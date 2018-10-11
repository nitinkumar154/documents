package com.innoviti.emi.core.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.innoviti.emi.core.resource.SchemeModelTerminalResource;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.service.core.SchemeModelTerminalService;

@RestController
@RequestMapping("/schemeModelTerminals")
public class SchemeModelTerminalController {

  private static Logger logger = Logger.getLogger(SchemeModelTerminalController.class);

  @Autowired
  SchemeModelTerminalService schemeModelTerminalService;

  @PostMapping
  public HttpEntity<?> createSchemeModelTerminal(
      @RequestBody SchemeModelTerminal schemeModelTerminal) {

    // DO NOT DELETE
    // if
    // (!schemeModelTerminalService.findByCodeIfExists(schemeModelTerminal.getInnoSchemeModelCode(),
    // schemeModelTerminal.getUtid())) {
    // SchemeModelTerminal createdSchemeModelTerminal =
    // schemeModelTerminalService.create(schemeModelTerminal);
    //
    // SchemeModelTerminalResource schemeModelTerminalResource =
    // new SchemeModelTerminalResource(createdSchemeModelTerminal);
    //
    // return ResponseEntity
    // .created(URI.create(schemeModelTerminalResource.createSchemeModelTerminalLink())).build();
    // }
    // else
    // throw new RequestNotCompletedException("Scheme Model mapping with Terminal already exists
    // !!", 417);
    SchemeModelTerminal createdSchemeModelTerminal =
        schemeModelTerminalService.create(schemeModelTerminal);

    SchemeModelTerminalResource schemeModelTerminalResource =
        new SchemeModelTerminalResource(createdSchemeModelTerminal);

    return ResponseEntity
        .created(URI.create(schemeModelTerminalResource.createSchemeModelTerminalLink())).build();
  }

  @GetMapping(value = "/{utid}/{innoSchemeModelCode}")
  public HttpEntity<List<SchemeModelTerminalResource>> getSchemeModelTerminalResourceList(
      @PathVariable("utid") String utid,
      @PathVariable("innoSchemeModelCode") String innoSchemeModelCode) {

    List<SchemeModelTerminal> lookedUpSchemeModelTerminalList =
        schemeModelTerminalService.getSchemeModelTerminalListById(utid, innoSchemeModelCode);

    List<SchemeModelTerminalResource> schemeModelTerminalResourceList = new ArrayList<>();

    for (SchemeModelTerminal schemeModelTerminal : lookedUpSchemeModelTerminalList) {
      SchemeModelTerminalResource schemeModelTerminalResource =
          new SchemeModelTerminalResource(schemeModelTerminal);
      schemeModelTerminalResource.createSelfLink();
      schemeModelTerminalResourceList.add(schemeModelTerminalResource);
    }

    return ResponseEntity.<List<SchemeModelTerminalResource>>ok(schemeModelTerminalResourceList);
  }

  @GetMapping
  public ResponseEntity<PagedResources<SchemeModelTerminalResource>> getSchemeModelList(
      Pageable pageable) {
    Page<SchemeModelTerminal> schemeModelTerminalList =
        schemeModelTerminalService.findAllSchemeModelTerminal(pageable);
    List<SchemeModelTerminalResource> resources = new ArrayList<>();
    for (SchemeModelTerminal schemeModelTerminal : schemeModelTerminalList.getContent()) {
      SchemeModelTerminalResource schemeModelTerminalResource =
          new SchemeModelTerminalResource(schemeModelTerminal);
      schemeModelTerminalResource.createSelfLink();
      resources.add(schemeModelTerminalResource);
    }
    PageMetadata pageMetaData =
        new PageMetadata(schemeModelTerminalList.getSize(), schemeModelTerminalList.getNumber(),
            schemeModelTerminalList.getTotalElements(), schemeModelTerminalList.getTotalPages());
    PagedResources<SchemeModelTerminalResource> pagedResource =
        new PagedResources<>(resources, pageMetaData);
    return ResponseEntity.<PagedResources<SchemeModelTerminalResource>>ok(pagedResource);

  }

  @PostMapping(value = "/createSchemeModelTerminal")
  public ResponseEntity<List<SchemeModelTerminal>> createIssuerSchemeModelTerminal(
      @RequestBody List<SchemeModelTerminal> issuerSchemeModel) {

    List<SchemeModelTerminal> response =
        schemeModelTerminalService.createIssuerSchemeModel(issuerSchemeModel);
    return ResponseEntity.<List<SchemeModelTerminal>>ok(response);
  }

  @GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<SchemeModelTerminalResource>> getSchemeModelTerminalForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {

    Page<SchemeModelTerminal> schemeModelTerminalPage = schemeModelTerminalService
        .findAllSchemeModelTerminalForDataMovement(recordStatus, pageable);
    List<SchemeModelTerminalResource> resources = new ArrayList<>();
    for (SchemeModelTerminal schemeModelTerminal : schemeModelTerminalPage.getContent()) {
      SchemeModelTerminalResource schemeModelTerminalResource =
          new SchemeModelTerminalResource(schemeModelTerminal);
      schemeModelTerminalResource.createSelfLink();
      resources.add(schemeModelTerminalResource);
    }
    PageMetadata pageMetaData =
        new PageMetadata(schemeModelTerminalPage.getSize(), schemeModelTerminalPage.getNumber(),
            schemeModelTerminalPage.getTotalElements(), schemeModelTerminalPage.getTotalPages());
    PagedResources<SchemeModelTerminalResource> pagedResource =
        new PagedResources<>(resources, pageMetaData);
    return ResponseEntity.<PagedResources<SchemeModelTerminalResource>>ok(pagedResource);
  }

  @PostMapping(value = "/updateSyncStatus")
  public ResponseEntity<List<SchemeModelTerminalResource>> updateSchemeModelTerminalSyncStatus(
      @RequestParam("innoSchemeModelCode") String innoSchemeModelCode,
      @RequestParam("utid") String utid, @RequestParam("status") String status) {
    logger.info("Entering updateSchemeModelTerminalSyncStatus :: SchemeModelTerminalController");
    List<SchemeModelTerminal> schemeModelTerminalList = schemeModelTerminalService
        .updateSchemeModelTerminalSyncStatus(innoSchemeModelCode, utid, status);
    List<SchemeModelTerminalResource> resources = new ArrayList<>();

    for (SchemeModelTerminal schemeModelTerminal : schemeModelTerminalList) {
      SchemeModelTerminalResource schemeModelTerminalResource =
          new SchemeModelTerminalResource(schemeModelTerminal);
      schemeModelTerminalResource.createSelfLink();
      resources.add(schemeModelTerminalResource);
    }
    logger.info("Exiting updateSchemeModelTerminalSyncStatus :: SchemeModelTerminalController");
    return ResponseEntity.<List<SchemeModelTerminalResource>>ok(resources);
  }

}
