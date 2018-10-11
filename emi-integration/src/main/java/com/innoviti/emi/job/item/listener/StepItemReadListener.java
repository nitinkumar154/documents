package com.innoviti.emi.job.item.listener;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.core.io.FileSystemResource;

public class StepItemReadListener<T,S> extends StepItemListenerSupport{
  
  private static Logger log = LoggerFactory.getLogger(StepItemReadListener.class);
  
  private String filePath;
  
  @OnReadError
  public void onReadError(Exception ex) {
    if(ex instanceof FlatFileParseException){
      FlatFileParseException flatFileParserException = (FlatFileParseException) ex;
      log.debug("Skipped while reading line number : {}", flatFileParserException.getLineNumber());
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(flatFileParserException.getLineNumber()).append("  ")
      .append(flatFileParserException.getInput()).append("|").append(getExceptionMessage(ex));
      doWrite(stringBuilder.toString());
    }
    else{
      doWrite(ex.getMessage());
    }
  }
  @OnSkipInWrite
  public void onWriteError(S item, Throwable t) {
    doWrite(createSkippedData(item.toString(), t));
  }
  @OnSkipInProcess
  public void onSkipInProcess(T item, Throwable t){
    doWrite(createSkippedData(item.toString(), t));;
  }
  private String createSkippedData(String data, Throwable cause){
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(data).append("|").append(getExceptionMessage(cause));
   
    return stringBuilder.toString();
  }
  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath, String fileType) {
    this.filePath = filePath;
    fileType = fileType == null || fileType.isEmpty() ? "result" : fileType;
    Path resultFilePath = createResultFile(filePath, fileType);
    setResource(new FileSystemResource(resultFilePath.toString()));
  }
  
}
