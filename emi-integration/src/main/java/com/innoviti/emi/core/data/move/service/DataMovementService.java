package com.innoviti.emi.core.data.move.service;

public interface DataMovementService {

  void prepareEntriesForPublishing();

  void updateTimestampForMovedData();

}
