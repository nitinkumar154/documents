package com.innoviti.emi.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum OnUsOffUsStatus {
  ON_US, OFF_US;
}
