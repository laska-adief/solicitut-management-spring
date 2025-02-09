package com.solicitut.management.models.customer;

import com.solicitut.management.models.general.ListRequestModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CustomerListRequestModel extends ListRequestModel {
  String filterCustomerName;
  public CustomerListRequestModel(int size, int page, String sortName, String sortValue, String filterCustomerName) {
    super(size, page, sortName, sortValue);
    this.filterCustomerName = filterCustomerName;
  }
}
