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

import com.innoviti.emi.core.resource.IssuerBankResource;
import com.innoviti.emi.entity.core.IssuerBank;
import com.innoviti.emi.service.core.IssuerBankService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping(value = "/issuerBanks")
public class IssuerBankController {

  @Autowired
  private IssuerBankService issuerBankService;

  @PostMapping
  public ResponseEntity<?> create(@RequestBody IssuerBank issuerBank) {
    IssuerBank createdIssuerBank = issuerBankService.create(issuerBank);
    IssuerBankResource issuerBankResource = new IssuerBankResource(createdIssuerBank);

    return ResponseEntity.created(URI.create(issuerBankResource.getCreateIssuerBankLink())).build();
  }

  @GetMapping(value = "/{innoIssuerBankCode}")
  public ResponseEntity<IssuerBankResource> getIssuerBankById(
      @PathVariable("innoIssuerBankCode") String innoIssuerBankCode) {

    IssuerBank issuerBank =
        issuerBankService.findIssuerBankByInnoIssuerBankCode(innoIssuerBankCode);

    IssuerBankResource issuerBankResource = new IssuerBankResource(issuerBank);
    issuerBankResource.addSelfLink();

    return ResponseEntity.<IssuerBankResource>ok(issuerBankResource);
  }


  @GetMapping(value = "/query")
  public ResponseEntity<List<IssuerBankResource>> getIssuerBank(
      @QuerydslPredicate(root = IssuerBank.class) Predicate predicate) {
    Iterable<IssuerBank> issuerBanks = issuerBankService.findAll(predicate);
    List<IssuerBankResource> resources = new ArrayList<>();
    for (IssuerBank issuerBank : issuerBanks) {
      IssuerBankResource issuerBankResource = new IssuerBankResource(issuerBank);
      issuerBankResource.addSelfLink();
      resources.add(issuerBankResource);
    }
    return ResponseEntity.<List<IssuerBankResource>>ok(resources);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public HttpEntity<PagedResources<IssuerBankResource>> findAllIssuerBank(Pageable pageable) {

    Page<IssuerBank> issuerBankPage = issuerBankService.findAllIssuerBank(pageable);

    List<IssuerBankResource> issuerBankResourceList = new ArrayList<>();
    for (IssuerBank issuerBank : issuerBankPage.getContent()) {
      IssuerBankResource issuerBankResource = new IssuerBankResource(issuerBank);
      issuerBankResource.addSelfLink();
      issuerBankResourceList.add(issuerBankResource);
    }

    PageMetadata pageMetaData =
        new PageMetadata(issuerBankPage.getSize(), issuerBankPage.getNumber(),
            issuerBankPage.getTotalElements(), issuerBankPage.getTotalPages());
    PagedResources<IssuerBankResource> pagedResource =
        new PagedResources<>(issuerBankResourceList, pageMetaData);
    return ResponseEntity.<PagedResources<IssuerBankResource>>ok(pagedResource);
  }

  @GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
  public HttpEntity<PagedResources<IssuerBankResource>> getAllIssuerBankForDataMovement(
      Pageable pageable, @RequestParam("recordStatus") String recordStatus) {

    Page<IssuerBank> issuerBankPage =
        issuerBankService.findAllIssuerBankForDataMovement(recordStatus, pageable);

    List<IssuerBankResource> issuerBankResourceList = new ArrayList<>();
    for (IssuerBank issuerBank : issuerBankPage.getContent()) {
      IssuerBankResource issuerBankResource = new IssuerBankResource(issuerBank);
      issuerBankResource.addSelfLink();
      issuerBankResourceList.add(issuerBankResource);
    }
    PageMetadata pageMetaData =
        new PageMetadata(issuerBankPage.getSize(), issuerBankPage.getNumber(),
            issuerBankPage.getTotalElements(), issuerBankPage.getTotalPages());
    PagedResources<IssuerBankResource> pagedResource =
        new PagedResources<>(issuerBankResourceList, pageMetaData);
    return ResponseEntity.<PagedResources<IssuerBankResource>>ok(pagedResource);
  }

}
