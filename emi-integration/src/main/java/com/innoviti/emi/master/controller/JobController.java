package com.innoviti.emi.master.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innoviti.emi.entity.objectstore.ObjectStoreLookUp;
import com.innoviti.emi.model.ErrorMessage;
import com.innoviti.emi.model.MappingFileInfo;
import com.innoviti.emi.service.core.JobService;
import com.innoviti.emi.service.master.BajajEMIFileListService;
import com.innoviti.emi.util.Util;

@RestController
@RequestMapping(value = "/jobs")
public class JobController {

  @Autowired
  private BajajEMIFileListService bajajEMIFileListService;

  @Value("${store.upload.url}")
  private String objectStoreUrl;

  @Autowired
  private JobService jobService;
  
  @GetMapping(value = "/getList")
  public List<ObjectStoreLookUp> getList() {
    return bajajEMIFileListService.getList();
  }
  @GetMapping(value = "/list")
  public List<ObjectStoreLookUp> getList(@RequestParam(name="date", required = true) String date) {
    return bajajEMIFileListService.getList(date);
  }
  @PostMapping(value = "/master/launch")
  public ResponseEntity<ErrorMessage> launchJob() throws Exception {
    jobService.launchMasterJob(Util.TEMP_FOLDER);
    return ResponseEntity
        .<ErrorMessage>ok(new ErrorMessage("Congrats!! you have launched your job successfully", 200));
  }
  
  @PostMapping(value = "/csv/launch/{utid}")
  public ResponseEntity<ErrorMessage> launchDatabaseToCsvJob(@PathVariable("utid") String utid) throws Exception{
    jobService.launchDatabaseToCsv(utid);
    return ResponseEntity
        .<ErrorMessage>ok(new ErrorMessage("Wait!! I am generating a csv for you : "+utid, 200));
  }
  
  @PostMapping(value = "/core/launch")
  public ResponseEntity<ErrorMessage> launchCoreJob() throws Exception{
    jobService.launchMasterToCoreJob(Util.TEMP_FOLDER);
    return ResponseEntity
        .<ErrorMessage>ok(new ErrorMessage("Congrats!! you have launched your job successfully", 200));
  }
  @GetMapping(value = "/mapping/file/list")
  public List<MappingFileInfo> getDealerEslipList(){
    return jobService.getCSVFileList(Util.EMI_FOLDER);
  }
  @GetMapping(value = "/download/file/{filepath}")
  public ResponseEntity<FileSystemResource> downloadDealerFile(@PathVariable("filepath") String fileName){
    File file = jobService.getCSVFile(fileName);
    HttpHeaders header = getHeaders(file);
    FileSystemResource fileSystemResouce = getFile(file);
    return new ResponseEntity<>(fileSystemResouce, header, HttpStatus.OK);
  }
  @PostMapping(value = "/file/launch/{folderName}")
  public ResponseEntity<ErrorMessage> launchFileToDatabaseJob(@PathVariable("folderName") String folderName) throws Exception {
    jobService.launchFileToDatabaseJobOnAvailableFiles(folderName);
    return ResponseEntity
        .<ErrorMessage>ok(new ErrorMessage("Congrats!! you have launched your job successfully", 200));
  }
  private HttpHeaders getHeaders(File file){
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_PDF);
    header.set(HttpHeaders.CONTENT_DISPOSITION,
                   "attachment; filename=" + file.getName());
    header.setContentLength(file.length());
    return header;
  }
  private FileSystemResource  getFile(File file){
    return new FileSystemResource(file);
  }
  
}
