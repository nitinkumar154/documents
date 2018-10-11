package com.innoviti.emi.core.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

import com.innoviti.emi.core.resource.SerialNumberResource;
import com.innoviti.emi.entity.core.SerialNo;
import com.innoviti.emi.service.core.SerialNoService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping(value = "/serialNos")
public class SerialNumberController {

  @Autowired
  private SerialNoService serialNoService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody SerialNo serialNo) {
    SerialNo createdSerialNo = serialNoService.create(serialNo);
    SerialNumberResource serialNumberResource = new SerialNumberResource(createdSerialNo);
    return ResponseEntity.created(URI.create(serialNumberResource.getCreateCategoryLink())).build();
  }

  @GetMapping(value = "/{innoModelSerialNumber}")
  public ResponseEntity<SerialNumberResource> getByInnoModelSerialNumber(
      @PathVariable("innoModelSerialNumber") String innoModelSerialNumber) {

    SerialNo lookedUpSerialNo =
        serialNoService.getSerialNoByInnoModelSerialNumber(innoModelSerialNumber);

    SerialNumberResource serialNumberResource = new SerialNumberResource(lookedUpSerialNo);
    serialNumberResource.addSelfLink();

    return ResponseEntity.<SerialNumberResource>ok(serialNumberResource);
  }

  @GetMapping(value = "/query")
  public ResponseEntity<List<SerialNumberResource>> filterSerialNo(
      @QuerydslPredicate(root = SerialNo.class) Predicate predicate) {
    Iterable<SerialNo> serialNumbers = serialNoService.findAll(predicate);
    List<SerialNumberResource> serialNumberResources = new ArrayList<>();
    for (SerialNo serialNo : serialNumbers) {
      SerialNumberResource serialNumberResource = new SerialNumberResource(serialNo);
      serialNumberResource.addSelfLink();
      serialNumberResources.add(serialNumberResource);
    }
    return ResponseEntity.<List<SerialNumberResource>>ok(serialNumberResources);
  }

  @GetMapping
  public ResponseEntity<PagedResources<SerialNumberResource>> getAllSerialNo(Pageable pageable) {
    Page<SerialNo> serialNumbers = serialNoService.findAllSerialNo(pageable);
    List<SerialNumberResource> serialNumberResources = new ArrayList<>();
    for (SerialNo serialNo : serialNumbers.getContent()) {
      SerialNumberResource serialNumberResource = new SerialNumberResource(serialNo);
      serialNumberResource.addSelfLink();
      serialNumberResources.add(serialNumberResource);
    }
    PageMetadata pageMetaData = new PageMetadata(serialNumbers.getSize(), serialNumbers.getNumber(),
        serialNumbers.getTotalElements(), serialNumbers.getTotalPages());
    PagedResources<SerialNumberResource> pagedResource =
        new PagedResources<>(serialNumberResources, pageMetaData);
    return ResponseEntity.<PagedResources<SerialNumberResource>>ok(pagedResource);

  }

  @GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PagedResources<SerialNumberResource>> getAllSerialNoForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {

    Page<SerialNo> serialNumbers =
        serialNoService.findAllSerialNoForDataMovement(recordStatus, pageable);
    List<SerialNumberResource> serialNumberResources = new ArrayList<>();
    for (SerialNo serialNo : serialNumbers.getContent()) {
      SerialNumberResource serialNumberResource = new SerialNumberResource(serialNo);
      serialNumberResource.addSelfLink();
      serialNumberResources.add(serialNumberResource);
    }
    PageMetadata pageMetaData = new PageMetadata(serialNumbers.getSize(), serialNumbers.getNumber(),
        serialNumbers.getTotalElements(), serialNumbers.getTotalPages());
    PagedResources<SerialNumberResource> pagedResource =
        new PagedResources<>(serialNumberResources, pageMetaData);
    return ResponseEntity.<PagedResources<SerialNumberResource>>ok(pagedResource);
  }

}
