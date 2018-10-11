package com.innoviti.emi.job.step.spliter;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class StepSplitter implements Partitioner{
  private static final Logger logger = LoggerFactory.getLogger(StepSplitter.class);
  private int gridSize = 4;
  private int chunkSize = 50;
  @Override
  public Map<String, ExecutionContext> partition(int gridSize) {
    setGridSize(gridSize);
    Map<String, ExecutionContext> result = new HashMap<>();
    
    int fromId = 0;
    
    for(int i = 1; i <= gridSize; i++){
      ExecutionContext executionContext = new ExecutionContext();
      logger.info("Starting thread : {}", i);
      logger.info("From id : {}", fromId);
      
      executionContext.putInt("fromId", fromId);
      executionContext.putInt("gridSize", gridSize);
      executionContext.putString("threadName", "SplitterThread"+i);
      
      result.put("partition" + i, executionContext);
      fromId = fromId + chunkSize;
    
    }
    return result;
  }
  public int getGridSize() {
    return gridSize;
  }
  public void setGridSize(int gridSize) {
    this.gridSize = gridSize;
  }
  public int getChunkSize() {
    return chunkSize;
  }
  public void setChunkSize(int chunkSize) {
    this.chunkSize = chunkSize;
  }
  
}
