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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innoviti.emi.core.resource.SchemeResource;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.service.core.SchemeService;
import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/schemes")
public class SchemeController {

	@Autowired
	SchemeService schemeService;

	@PostMapping
	public HttpEntity<Scheme> create(@RequestBody Scheme scheme) {
		Scheme createScheme = schemeService.create(scheme);
		SchemeResource resourceScheme = new SchemeResource(createScheme);
		return ResponseEntity.created(URI.create(resourceScheme.createSchemeLink())).build();
	}

	@PutMapping(value = "/{innoIssuerSchemeCode}")
	public HttpEntity<String> update(@RequestBody Scheme scheme,
			@PathVariable("innoIssuerSchemeCode") String innoIssuerSchemeCode) {
		String result = null;
		Scheme tempResult = new Scheme();
		if (scheme.getInnoIssuerSchemeCode().equals(innoIssuerSchemeCode)) {
			tempResult = schemeService.update(scheme);
			SchemeResource resourceScheme = new SchemeResource(tempResult);
			result = resourceScheme.createSchemeLink();
		} else {
			result = "Invalid request object";
		}
		return ResponseEntity.created(URI.create(result)).build();
	}

	@GetMapping(value = "/{innoIssuerSchemeCode}")
	public HttpEntity<SchemeResource> getSchemeByInnoSchemeCode(
			@PathVariable("innoIssuerSchemeCode") String innoIssuerSchemeCode) {
		Scheme lookedUpSchemeList = schemeService.findSchemeByInnoIssuerSchemeCode(innoIssuerSchemeCode);
		SchemeResource schemeResource = new SchemeResource(lookedUpSchemeList);
		schemeResource.createSelfLink();
		return ResponseEntity.<SchemeResource>ok(schemeResource);
	}

	@GetMapping(value = "/query")
	public HttpEntity<List<SchemeResource>> findAllSchemeByQueryDsl(
			@QuerydslPredicate(root = Scheme.class) Predicate predicate) {
		Iterable<Scheme> schemeList = schemeService.findAll(predicate);
		List<SchemeResource> schemeResourcesList = new ArrayList<>();
		for (Scheme scheme : schemeList) {
			SchemeResource schemeResource = new SchemeResource(scheme);
			schemeResource.createSelfLink();
			schemeResourcesList.add(schemeResource);
		}
		return ResponseEntity.<List<SchemeResource>>ok(schemeResourcesList);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<PagedResources<SchemeResource>> findAllScheme(Pageable pageable) {
		Page<Scheme> schemePage = schemeService.findAllSchemes(pageable);
		List<SchemeResource> schemeResourcesList = new ArrayList<>();
		for (Scheme scheme : schemePage.getContent()) {
			SchemeResource schemeResource = new SchemeResource(scheme);
			schemeResource.createSelfLink();
			schemeResourcesList.add(schemeResource);
		}
		PageMetadata pageMetaData = new PageMetadata(schemePage.getSize(), schemePage.getNumber(),
				schemePage.getTotalElements(), schemePage.getTotalPages());
		PagedResources<SchemeResource> pagedResource = new PagedResources<>(schemeResourcesList, pageMetaData);
		return ResponseEntity.<PagedResources<SchemeResource>>ok(pagedResource);
	}

	@GetMapping(value = "/allFromFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<PagedResources<SchemeResource>> findAllSchemesFromFilter(
			@RequestParam("bankCode") String bankCode, @RequestParam("tenureCode") String tenureCode,
			Pageable pageable) {
		Page<Scheme> schemeList = schemeService.findAllSchemesFromFilter(bankCode, tenureCode, pageable);
		List<SchemeResource> schemeResourcesList = new ArrayList<>();
		for (Scheme scheme : schemeList.getContent()) {
			SchemeResource schemeResource = new SchemeResource(scheme);
			schemeResource.createSelfLink();
			schemeResourcesList.add(schemeResource);
		}
		PageMetadata pageMetaData = new PageMetadata(schemeList.getSize(), schemeList.getNumber(),
				schemeList.getTotalElements(), schemeList.getTotalPages());
		PagedResources<SchemeResource> pagedResource = new PagedResources<>(schemeResourcesList, pageMetaData);
		return ResponseEntity.<PagedResources<SchemeResource>>ok(pagedResource);
	}

	@GetMapping(value = "/allForDataMovement", produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<PagedResources<SchemeResource>> getAllSchemeForDataMovement(Pageable pageable,
			@RequestParam("recordStatus") String recordStatus) {
		Page<Scheme> schemeList = schemeService.findAllSchemesForDataMovement(recordStatus, pageable);
		List<SchemeResource> schemeResourcesList = new ArrayList<>();
		for (Scheme scheme : schemeList.getContent()) {
			SchemeResource schemeResource = new SchemeResource(scheme);
			schemeResource.createSelfLink();
			schemeResourcesList.add(schemeResource);
		}
		PageMetadata pageMetaData = new PageMetadata(schemeList.getSize(), schemeList.getNumber(),
				schemeList.getTotalElements(), schemeList.getTotalPages());
		PagedResources<SchemeResource> pagedResource = new PagedResources<>(schemeResourcesList, pageMetaData);
		return ResponseEntity.<PagedResources<SchemeResource>>ok(pagedResource);
	}

}
