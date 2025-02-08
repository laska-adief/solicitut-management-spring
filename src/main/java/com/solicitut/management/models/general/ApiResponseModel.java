package com.solicitut.management.models.general;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseModel<T> {
  private Object data;

  public ApiResponseModel(T data) {
    this.data = data;
  }

  public ApiResponseModel(String errorCode, String errorTitle, String errorMessage) {
    this.data = new ErrorResponseModel(errorCode, errorTitle, errorMessage);
  }
}
