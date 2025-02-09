package com.solicitut.management.controllers.customer;

import com.solicitut.management.models.customer.CustomerListRequestModel;
import com.solicitut.management.models.general.ApiResponseModel;
import com.solicitut.management.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

  @Autowired
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("list")
  public Mono<ResponseEntity<ApiResponseModel<Object>>> getAllCustomer(
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "") String sortName,
    @RequestParam(defaultValue = "") String sortValue,
    @RequestParam(defaultValue = "") String filterCustomerName
  ){
    CustomerListRequestModel payload = new CustomerListRequestModel(size, page, sortName, sortValue, filterCustomerName);
    return customerService.getAllCustomer(payload);
  }
}
