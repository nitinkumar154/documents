package com.innoviti.emi.core.data.move.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innoviti.emi.core.data.move.service.DataMovementService;

@RestController
@RequestMapping(value = "/prepareData")
public class DataMovementController {

  private static final Logger LOGGER = Logger.getLogger(DataMovementController.class);

  @Autowired
  DataMovementService dataMovementService;

  @PostMapping
  public HttpEntity<?> triggerForDataMovement() {
    try {
      dataMovementService.prepareEntriesForPublishing();
    } catch (Exception e) {
      LOGGER.error("Error while publishing entries for Data Movement.", e);
      throw e;
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(value = "/update")
  public HttpEntity<?> updateTimestampForMovedData() {
    try {
      dataMovementService.updateTimestampForMovedData();
    } catch (Exception e) {
      LOGGER.error("Error while publishing entries for Data Movement.", e);
      throw e;
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
