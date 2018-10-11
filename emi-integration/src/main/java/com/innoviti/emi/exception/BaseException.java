package com.innoviti.emi.exception;

public class BaseException extends RuntimeException{
 
  private static final long serialVersionUID = 2827308907740826575L;
  
  private String message;
  private int code;
  
  public BaseException(String message) {
    super(message);
    this.message = message;
  }
  public BaseException(String message, int code) {
    super(message);
    this.message = message;
    this.code = code;
  }
  public String getMessage() {
    return message;
  }
  public int getCode() {
    return code;
  }
}
