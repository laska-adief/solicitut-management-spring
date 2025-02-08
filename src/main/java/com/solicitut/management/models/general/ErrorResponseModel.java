package com.solicitut.management.models.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseModel {
  private String errorCode;
  private String errorTitle;
  private String errorMessage;
}
