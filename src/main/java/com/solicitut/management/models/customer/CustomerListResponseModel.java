package com.solicitut.management.models.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListResponseModel {
  private int dataCount;
  private int pageCount;
  private List<CustomerModel> customerList;
}
