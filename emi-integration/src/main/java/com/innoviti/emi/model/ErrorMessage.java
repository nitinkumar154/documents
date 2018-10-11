package com.innoviti.emi.model;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

  private static final long serialVersionUID = 8306703019918181104L;
  
  private final String errorMessage;
  private final int code;

  public ErrorMessage(String errorMessage, int code) {
    this.errorMessage = errorMessage;
    this.code = code;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public int getCode() {
    return code;
  }

}
