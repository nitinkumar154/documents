package com.innoviti.emi.job.item.listener;

import java.io.FileNotFoundException;

import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.NonTransientResourceException;

import antlr.NoViableAltException;

public class ItemSkipPolicy implements SkipPolicy{

  private Logger logger = LoggerFactory.getLogger(ItemSkipPolicy.class);
  
  @Override
  public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
    Throwable exception = t.getCause() == null ? t : t.getCause();
    String  message = exception.getMessage();
    logger.debug("Skip count : {} exception : {}", skipCount, message);
    
    return !(t instanceof FileNotFoundException || exception instanceof NoViableAltException ||
        exception instanceof QuerySyntaxException || t instanceof NonTransientResourceException);
  }

}
