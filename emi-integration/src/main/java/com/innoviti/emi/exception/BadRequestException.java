package com.innoviti.emi.exception;

public class BadRequestException extends BaseException {

  private static final long serialVersionUID = 8166159100172646175L;

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, int code) {
    super(message, code);
  }
}
