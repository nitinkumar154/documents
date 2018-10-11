package com.innoviti.emi.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CashbackType {
  PRE, POST, NA;
}
