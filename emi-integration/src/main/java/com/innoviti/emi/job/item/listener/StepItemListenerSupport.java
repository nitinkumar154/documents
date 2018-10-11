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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;

public abstract class StepItemListenerSupport extends FlatFileItemWriter<String>{
  private Logger log = LoggerFactory.getLogger(StepItemListenerSupport.class);

  protected boolean isFileExist(Path path){
    File file = path.toFile();
    return file.exists();
  }
  private String createResultFileName(Path inputFilePath, String fileType){
    String fileName = inputFilePath.getFileName().toString();
    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
    fileName = fileName+"_"+fileType+".txt";
    return fileName;
  }
  protected Path createResultFile(String filePath, String fileType){
    Path path = Paths.get(filePath);
    String fileName = createResultFileName(path, fileType);
    Path resultFilePath = Paths.get(path.getName(0) + File.separator + fileName);
    if(!isFileExist(resultFilePath)){
      try {
        resultFilePath = Files.createFile(resultFilePath);
      } catch (IOException e) {
        log.error("Unable to create file",e);
      }
    }
    return resultFilePath;
  }
  protected void doWrite(List<String> items){
    try {
      write(items);
    } catch (Exception e) {
      log.error("Exception while writing to output file", e);
    }
  }
  protected void doWrite(String value){
    List<String> items = new ArrayList<>();
    items.add(value);
    doWrite(items);
  }
  protected String getExceptionMessage(Throwable t){
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
