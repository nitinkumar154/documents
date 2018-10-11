package com.innoviti.emi.service.impl.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.innoviti.emi.constant.JobFileType;
import com.innoviti.emi.constant.JobType;
import com.innoviti.emi.entity.objectstore.ObjectStoreLookUp;
import com.innoviti.emi.exception.BadRequestException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.exception.RequestNotCompletedException;
import com.innoviti.emi.model.MappingFileInfo;
import com.innoviti.emi.service.core.JobService;
import com.innoviti.emi.service.master.BajajEMIFileListService;
import com.innoviti.emi.util.Util;

@Service
public class JobServiceImpl implements JobService{

  private static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
 
  @Autowired
  private RestTemplate restTemplate;

  @Value("${store.upload.url}")
  private String objectStoreUrl;
  
  @Autowired
  private BajajEMIFileListService bajajEMIFileListService;
  
  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private JobRegistry jobRegistry;
  
  @Autowired
  private Flow createFileToDatabaseFlow;
  
  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  
  public static Date batchDate = new Date();
  
  @Override
  public void launchMasterJob(String folder) throws Exception{
    batchDate = new Date();
    Path path = Util.createDirectories(folder);
    String downloadFolder = path.toString();
    for(File file: path.toFile().listFiles()){
      if (!file.isDirectory()){
        boolean isFileDeleted = file.delete();
        logger.debug("File {} is deleted {}", file.getName(), isFileDeleted);
      }
       
    }
    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
    for(JobFileType fileType : JobFileType.values()){
      if(fileType == JobFileType.SCHEME_MODEL_TEMINAL_MAPPING){
        String filePath = createFileIfNotExist(fileType.getFileType(), downloadFolder);
        jobParametersBuilder.addString(fileType.getFileType(), filePath);
        continue;
      }
      else if(fileType == JobFileType.SCHEME_MODEL_MASTER){
        String filePath = createFileIfNotExist(fileType.getFileType(), downloadFolder);
        jobParametersBuilder.addString(fileType.getFileType(), filePath);
      }
      try {
        ObjectStoreLookUp objectLookUp = bajajEMIFileListService.getFileKey(fileType.getFileType());
        if(objectLookUp == null){
          continue;
        }
        String fileKey = objectLookUp.getKey();
        logger.info("EMI file key for file type {} is {}", fileType.getFileType(), fileKey);
        if (fileKey != null) {
          String fileName = downloadFile(fileKey, downloadFolder);
          jobParametersBuilder.addString(fileType.getFileType(), folder + File.separator + fileName);
        }
      } catch (Exception e) {
        logger.error("", e);
      }
    }
    lanchJob(JobType.FILE_TO_DATABASE.getJobName(), jobParametersBuilder.toJobParameters());
  }

  @Override
  public void launchMasterToCoreJob(String folder) throws Exception{
    JobParameters jobParameters = null;
    List<ObjectStoreLookUp> listOfBajajFiles = bajajEMIFileListService.findAll();
    if(listOfBajajFiles == null || listOfBajajFiles.isEmpty()){
      jobParameters = buildJobParameters(folder);
    }
    else{
      JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
      for(JobFileType fileType : JobFileType.values()){
        if(fileType == JobFileType.SCHEME_MODEL_TEMINAL_MAPPING){
          String filePath = createFileIfNotExist(fileType.getFileType(), folder);
          jobParametersBuilder.addString(fileType.getFileType(), filePath);
          continue;
        }
        else if(fileType == JobFileType.SCHEME_MODEL_MASTER){
          String filePath = createFileIfNotExist(fileType.getFileType(), folder);
          jobParametersBuilder.addString(fileType.getFileType(), filePath);
        }
        ObjectStoreLookUp objectLookUp = bajajEMIFileListService.getFileKey(fileType.getFileType());
        if(objectLookUp == null){
          continue;
        }
        jobParametersBuilder.addString(fileType.getFileType(), folder + File.separator + objectLookUp.getFileName());
      }
      jobParameters = jobParametersBuilder.toJobParameters();
    }
    lanchJob(JobType.MASTER_TO_CORE_TABLE.getJobName(), jobParameters);
  }
  @Override
  public void launchDatabaseToCsv(String utid) throws Exception {
    String path = Util.createDirectories(Util.EMI_FOLDER).toString();
    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
    jobParametersBuilder.addDate("date", new Date());
    jobParametersBuilder.addString("utid", utid);
    jobParametersBuilder.addString("folder_path", path);
    
    lanchJob(JobType.DATABASE_TO_CSV.getJobName(), jobParametersBuilder.toJobParameters());
  }
  @Override
  public List<MappingFileInfo> getCSVFileList(String folderPath) {
    List<MappingFileInfo> csvFileList = new ArrayList<>();
    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folderPath))){
      Iterator<Path> fileList = directoryStream.iterator();
      while(fileList.hasNext()){
        Path path = fileList.next();
        BasicFileAttributes view = Files.getFileAttributeView(path, BasicFileAttributeView.class)
            .readAttributes();
        FileTime fileTime = view.creationTime();
        String timeFormat = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss")
            .withZone(ZoneId.systemDefault())
            .format(fileTime.toInstant());
        MappingFileInfo fileInfo = new MappingFileInfo();
        fileInfo.setCreationTime(timeFormat);
        fileInfo.setFileName(path.toFile().getName());
        csvFileList.add(fileInfo);
      }
    } catch (IOException e) {
      logger.error("Exception ", e);
    }
    if(csvFileList.isEmpty()){
      throw new NotFoundException("No file in directory");
    }
    return csvFileList;
  }
  @Override
  public File getCSVFile(String fileName) {
    if(fileName == null || fileName.isEmpty()){
      throw new BadRequestException("Please provide file name to download");
    }
    String csvFileName = fileName;
    if(!csvFileName.contains("csv")){
      csvFileName = fileName+".csv";
    }
    Path path = Paths.get(Util.EMI_FOLDER+File.separator+csvFileName);
    if(path == null || !path.toFile().exists()){
      throw new NotFoundException(csvFileName + " file does not exist");
    }
    return path.toFile();
  }
  @Override
  public void launchMasterToCoreJobOnAvailableFiles(String folderName) throws Exception {
    JobParameters jobParamters = buildJobParameters(folderName);
    lanchJob(JobType.MASTER_TO_CORE_TABLE.getJobName(), jobParamters);
  }
  @Override
  public void launchFileToDatabaseJobOnAvailableFiles(String folder) throws Exception {
    Job job = jobBuilderFactory.get("fileToDatabase")
    .start(createFileToDatabaseFlow).end()
    .build();
    
    lanchJob(job, buildJobParameters(folder));
  }
  private JobParameters buildJobParameters(String folder){
    JobParametersBuilder jobParameterBuilder = new JobParametersBuilder();
    Path path = Paths.get(folder);
    if(path == null){
      throw new RequestNotCompletedException("Provided folder not found in file system");
    }
    File [] files = path.toFile().listFiles();
    if(files == null || files.length == 0){
      throw new RequestNotCompletedException("No files found to process");
    }
    for(JobFileType fileType : JobFileType.values()){
      if(fileType == JobFileType.SCHEME_MODEL_TEMINAL_MAPPING){
        String filePath = createFileIfNotExist(fileType.getFileType(), folder);
        jobParameterBuilder.addString(fileType.getFileType(), filePath);
        continue;
      }
      else if(fileType == JobFileType.SCHEME_MODEL_MASTER){
        String filePath = createFileIfNotExist(fileType.getFileType(), folder);
        jobParameterBuilder.addString(fileType.getFileType(), filePath);
      }
      for(File file : files){
        if(file.getName().contains(Util.RESULT_FILE_TYPE_1)|| file.getName().contains(Util.RESULT_FILE_TYPE_2)){
          continue;
        }
        if(file.getName().startsWith(fileType.getFileType())){
         jobParameterBuilder.addString(fileType.getFileType(), file.getPath());
        }
      }
    }
    return jobParameterBuilder.toJobParameters();
  }
  private String downloadFile(String fileKey, String folder){
    RequestCallback requestCallback = request -> request.getHeaders()
        .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
    ResponseExtractor<String> responseExtractor = response -> {
      String[] contentDisposition =
          response.getHeaders().getFirst("Content-Disposition").split("=");
      String tempFolderPath = Util.createDirectories(folder).toString();
      Path path = Paths
          .get(tempFolderPath + File.separator + contentDisposition[1].replaceAll("\"", ""));
      if (response.getStatusCode() == HttpStatus.OK) {
         Files.copy(response.getBody(), path, StandardCopyOption.REPLACE_EXISTING);
         return path.getFileName().toString();
      }
      return null;
    };
    return restTemplate.execute(URI.create(objectStoreUrl + "/download/" + fileKey), HttpMethod.GET,
        requestCallback, responseExtractor);
    
  }
  private void lanchJob(String jobName, JobParameters jobParameters) throws Exception{
    Job job = jobRegistry.getJob(jobName);
    JobExecution jobExecution = jobLauncher.run(job, jobParameters);
    logger.info("Job {} {} ", jobName, jobExecution.getStatus());
  }
  private void lanchJob(Job job, JobParameters jobParameters) throws Exception{
    JobExecution jobExecution = jobLauncher.run(job, jobParameters);
    logger.info("Job {} {} ", job.getName(), jobExecution.getStatus());
  }
  private String createFileIfNotExist(String fileType, String folder){
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(folder).append(File.separator).append(fileType).append("-")
    .append(System.currentTimeMillis()).append(".txt");
    return stringBuilder.toString();
  }
}
