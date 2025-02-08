package com.solicitut.management.models.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListRequestModel {
  private int size;
  private int page;
  private String sortName;
  private String sortValue;
}
