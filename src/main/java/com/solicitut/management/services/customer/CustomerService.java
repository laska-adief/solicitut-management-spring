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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

  @Autowired
  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  ApiResponseModel<Object> response;
  ErrorResponseModel errorNotFound = ErrorCodes.ErrorNotFound;
  ApiResponseModel<Object> responseNotFound = new ApiResponseModel<>(errorNotFound);

  public Mono<ResponseEntity<ApiResponseModel<Object>>> getAllCustomer(CustomerListRequestModel payload) {
    Pageable pageable;
    int size = payload.getSize();
    int page = payload.getPage();
    String sortName = payload.getSortName();
    String sortValue = payload.getSortValue();

    if(!sortName.isEmpty() && !sortValue.isEmpty()) {
      Sort.Order order = new Sort.Order(Sort.Direction.fromString(sortValue), sortName);
      Sort sort = Sort.by(order);
      pageable = PageRequest.of(page, size, sort);
    } else {
      pageable = PageRequest.of(page, size);
    }

    String filterCustomerName = payload.getFilterCustomerName();
    String filterCustomerStatus = payload.getFilterCustomerStatus();

    Mono<Long> customerCountMono = customerRepository.count();
    Mono<List<CustomerModel>> customerListMono;

    if(!filterCustomerStatus.isEmpty()) {
      customerListMono = customerRepository.findAllByCustomerNameContainingIgnoreCaseAndStatus(pageable, filterCustomerName, filterCustomerStatus).collectList();
    } else {
      customerListMono = customerRepository.findAllByCustomerNameContainingIgnoreCase(pageable, filterCustomerName).collectList();
    }

    return Mono.zip(customerCountMono, customerListMono).map( tuple -> {
      long totalCount = tuple.getT1();
      List<CustomerModel> customerList = tuple.getT2();

      if(customerList.isEmpty()) {
        return new ResponseEntity<>(responseNotFound, HttpStatus.CONFLICT);
      }

      int dataCount = (int) totalCount;
      int pageCount = (int )Math.ceil((double) dataCount / size);
      CustomerListResponseModel customerListRes = new CustomerListResponseModel(dataCount, pageCount, customerList);
      response = new ApiResponseModel<>(customerListRes);
      return new ResponseEntity<>(response, HttpStatus.OK);
    });
  }

  public Mono<ResponseEntity<ApiResponseModel<Object>>> getDetailCustomer(UUID customerId) {
    return customerRepository.findById(customerId)
      .flatMap(customer -> {
        response = new ApiResponseModel<>(customer);
        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
      })
      .switchIfEmpty(Mono.just(new ResponseEntity<>(responseNotFound, HttpStatus.CONFLICT)));
  }

  public Mono<ResponseEntity<ApiResponseModel<Object>>> postAddCustomer(CustomerModel payload) {
    CustomerModel payloadCustomer = new CustomerModel();
    payloadCustomer.setCustomerName(payload.getCustomerName());
    payloadCustomer.setCustomerNumber(payload.getCustomerNumber());
    payloadCustomer.setCardNumber(payload.getCardNumber());
    payloadCustomer.setStatus(payload.getStatus());
    payloadCustomer.setCreatedAt(LocalDateTime.now());
    payloadCustomer.setUpdatedAt(LocalDateTime.now());
    return customerRepository.save(payloadCustomer)
      .map(customer -> {
        response = new ApiResponseModel<>(customer);
        return new ResponseEntity<>(response, HttpStatus.OK);
      });
  }

  public Mono<ResponseEntity<ApiResponseModel<Object>>> editCustomer(CustomerModel payload) {
    return customerRepository.findById(payload.getCustomerId())
      .flatMap(existingCustomer -> {
        CustomerModel updatedCustomer = new CustomerModel();
        if(payload.getCustomerName() != null) {
          updatedCustomer.setCustomerName(payload.getCustomerName());
        } else {
          updatedCustomer.setCustomerName(existingCustomer.getCustomerName());
        }

        if(payload.getCustomerNumber() != null) {
          updatedCustomer.setCustomerNumber(payload.getCustomerNumber());
        } else {
          updatedCustomer.setCustomerNumber(existingCustomer.getCustomerNumber());
        }

        if(payload.getCardNumber() != null) {
          updatedCustomer.setCardNumber(payload.getCardNumber());
        } else {
          updatedCustomer.setCardNumber(existingCustomer.getCardNumber());
        }

        if(payload.getStatus() != null) {
          updatedCustomer.setStatus(payload.getStatus());
        } else {
          updatedCustomer.setStatus(existingCustomer.getStatus());
        }

        updatedCustomer.setCustomerId(existingCustomer.getCustomerId());
        updatedCustomer.setCreatedAt(existingCustomer.getCreatedAt());
        updatedCustomer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(updatedCustomer)
          .map(customer -> {
            response = new ApiResponseModel<>(customer);
            return new ResponseEntity<>(response, HttpStatus.OK);
          });
      })
      .switchIfEmpty(Mono.just(new ResponseEntity<>(responseNotFound, HttpStatus.CONFLICT)));
  }
}
