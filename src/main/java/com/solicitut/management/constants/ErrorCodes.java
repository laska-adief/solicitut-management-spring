package com.solicitut.management.constants;

import com.solicitut.management.models.general.ErrorResponseModel;

public class ErrorCodes {
  public static final ErrorResponseModel ErrorNotFound = new ErrorResponseModel(
    "404",
    "Not Found",
    "No Data Found"
  );
}
