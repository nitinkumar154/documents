package com.innoviti.emi.job.item.listener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.core.io.FileSystemResource;

public class FileItemSkipListener<T, S> extends FlatFileItemWriter<String> implements SkipListener<T, S>{
  
  private final static Logger log = LoggerFactory.getLogger(FileItemSkipListener.class);
 
  private String filePath;
  private String fileType = "result";
  
  public FileItemSkipListener(String filePath, String fileType) {
    this.filePath = filePath;
    this.fileType = fileType == null || fileType.isEmpty() ? "result" : fileType;
    setResource(new FileSystemResource(getOutputFilePath()));
  }
  
  public String getFilePath() {
    return filePath;
  }
  public void setFilePath(String filePath) {
    this.filePath = filePath;
   
  }
  public String getFileType() {
    return fileType;
  }
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }
  @Override
  public void onSkipInRead(Throwable t) {
    if(t instanceof FlatFileParseException){
      FlatFileParseException flatFileParserException = (FlatFileParseException) t;
      log.debug("Skipped while reading line number : {}", flatFileParserException.getLineNumber());
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(flatFileParserException.getLineNumber()).append("  ")
      .append(flatFileParserException.getInput()).append("|").append(getExceptionMessage(t));
      doWrite(stringBuilder.toString());
    }
    else{
      log.debug(t.getMessage());
      doWrite(t.getMessage());
    }
  }

  @Override
  public void onSkipInWrite(S item, Throwable t) {
    log.debug(getExceptionMessage(t));
    if(item instanceof List<?>){
      List<?> objects = (List<?>) item;
      for(Object obj : objects){
        doWrite(createSkippedData(obj.toString(), t));
      }
      return;
    }
    doWrite(createSkippedData(item.toString(), t));
  }

  @Override
  public void onSkipInProcess(T item, Throwable t) {
    log.debug(getExceptionMessage(t));
    doWrite(createSkippedData(item.toString(), t));;
    
  }
  private String createSkippedData(String data, Throwable cause){
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(data).append("|").append(getExceptionMessage(cause));
   
    return stringBuilder.toString();
  }
  private String getOutputFilePath() {
    Path path = Paths.get(getFilePath());
    
    String fileName = path.getFileName().toString();
    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
    fileName = fileName+"_"+getFileType()+".txt";
    Path resultFilePath = Paths.get(path.getName(0) + File.separator + fileName);
    if(resultFilePath.toFile().exists()){
      return resultFilePath.toString();
    }
    String createdFilePath = null;
    try {
      createdFilePath = Files.createFile(resultFilePath).toString();
    } catch (IOException e) {
      log.error("",e);
    }
    return createdFilePath;
    
  }
  
  private void doWrite(String value){
    if(getOutputFilePath() == null){
      log.info("Unable to create output file");
      return;
    }
    List<String> items = new ArrayList<>();
    items.add(value);
    try {
      write(items);
    } catch (Exception e) {
      logger.error("Exception while writing to output file", e);
    }
  }
  private String getExceptionMessage(Throwable t){
    Throwable exception = t.getCause() == null ? t : t.getCause();
    String errorMessage = exception.getMessage();
    if(exception instanceof ConstraintViolationException){
      int code = ((ConstraintViolationException)exception).getErrorCode();
      if(code == 1062){
        errorMessage = "DUPLICATE";
      }
      else if(code == 1048){
        errorMessage = "NULL VALUE";
      }
    }
    else if(t instanceof javax.validation.ConstraintViolationException){
      errorMessage = "VALIDATION FAILED";
    }
    else if(t instanceof FlatFileParseException && exception instanceof ArrayIndexOutOfBoundsException){
      errorMessage = "BLANK LINE";
     }
    return errorMessage;
  }
  
}
