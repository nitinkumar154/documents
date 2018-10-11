package com.innoviti.emi.exception;

public class NotFoundException extends BaseException {

  private static final long serialVersionUID = 7156525748893002528L;

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, int code) {
    super(message, code);
  }

}
