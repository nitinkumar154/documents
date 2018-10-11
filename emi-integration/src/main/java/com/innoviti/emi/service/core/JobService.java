package com.innoviti.emi.service.core;

import java.io.File;
import java.util.List;

import com.innoviti.emi.model.MappingFileInfo;

public interface JobService {
  void launchMasterJob(String tempFolderPath) throws Exception;
  void launchMasterToCoreJob(String tempFolderPath) throws Exception;
  void launchDatabaseToCsv(String utid)throws Exception;
  List<MappingFileInfo> getCSVFileList(String folderPath);
  File getCSVFile(String fileName);
  void launchMasterToCoreJobOnAvailableFiles(String folderName)throws Exception;
  void launchFileToDatabaseJobOnAvailableFiles(String folderName)throws Exception;
}
