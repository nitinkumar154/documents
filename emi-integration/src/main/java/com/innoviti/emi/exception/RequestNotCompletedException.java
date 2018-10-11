package com.innoviti.emi.exception;

public class RequestNotCompletedException extends BaseException {

  private static final long serialVersionUID = 2711338626980401968L;

  public RequestNotCompletedException(String message) {
    super(message);
  }

  public RequestNotCompletedException(String message, int code) {
    super(message, code);
  }

}
