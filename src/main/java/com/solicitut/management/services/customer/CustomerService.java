package com.solicitut.management.services.customer;

import com.solicitut.management.constants.ErrorCodes;
import com.solicitut.management.models.customer.CustomerListRequestModel;
import com.solicitut.management.models.customer.CustomerListResponseModel;
import com.solicitut.management.models.customer.CustomerModel;
import com.solicitut.management.models.general.ApiResponseModel;
import com.solicitut.management.models.general.ErrorResponseModel;
import com.solicitut.management.repositories.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CustomerService {

  @Autowired
  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Mono<ResponseEntity<ApiResponseModel<Object>>> getAllCustomer(CustomerListRequestModel payload) {
    int size = payload.getSize();
    int page = payload.getPage();
    Pageable pageable;
    pageable = PageRequest.of(page, size);

    Mono<Long> customerCountMono = customerRepository.count();
    Mono<List<CustomerModel>> customerListMono = customerRepository.findAllBy(pageable).collectList();

    return Mono.zip(customerCountMono, customerListMono).map( tuple -> {
      long totalCount = tuple.getT1();
      List<CustomerModel> customerList = tuple.getT2();

      if(customerList.isEmpty()) {
        ErrorResponseModel errorNotFound = ErrorCodes.ErrorNotFound;
        ApiResponseModel<Object> responseNotFound = new ApiResponseModel<>(errorNotFound);
        return new ResponseEntity<>(responseNotFound, HttpStatus.CONFLICT);
      }

      int dataCount = (int) totalCount;
      int pageCount = (int )Math.ceil((double) dataCount / size);
      CustomerListResponseModel customerListRes = new CustomerListResponseModel(dataCount, pageCount, customerList);
      ApiResponseModel<Object> response = new ApiResponseModel<>(customerListRes);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }
}
