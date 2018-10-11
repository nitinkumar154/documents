package com.innoviti.emi.exception;

public class AlreadyMappedException extends BaseException{

  private static final long serialVersionUID = 5792621371953908852L;
  
  public AlreadyMappedException(String message) {
    super(message);
  }
  public AlreadyMappedException(String message, int code) {
    super(message, code);
  }
}
