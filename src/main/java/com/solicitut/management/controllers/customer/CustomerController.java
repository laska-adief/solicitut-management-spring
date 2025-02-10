package com.solicitut.management.controllers.customer;

import com.solicitut.management.models.customer.CustomerListRequestModel;
import com.solicitut.management.models.general.ApiResponseModel;
import com.solicitut.management.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
    @RequestParam(defaultValue = "") String filterCustomerName,
    @RequestParam(defaultValue = "") String filterCustomerStatus
  ){
    CustomerListRequestModel payload = new CustomerListRequestModel(size, page, sortName, sortValue, filterCustomerName, filterCustomerStatus);
    return customerService.getAllCustomer(payload);
  }

  @GetMapping("detail/{id}")
  public Mono<ResponseEntity<ApiResponseModel<Object>>> getDetailCustomer(
    @PathVariable(value = "id") UUID id
  ) {
    return customerService.getDetailCustomer(id);
  }
}
