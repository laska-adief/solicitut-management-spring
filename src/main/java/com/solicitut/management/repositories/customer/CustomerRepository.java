package com.solicitut.management.repositories.customer;

import com.solicitut.management.models.customer.CustomerModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<CustomerModel, UUID> {
  Flux<CustomerModel> findAllByCustomerNameContainingIgnoreCase(Pageable pageable, String filterCustomerName);
  Flux<CustomerModel> findAllByCustomerNameContainingIgnoreCaseAndStatus(Pageable pageable, String filterCustomerName, String filterCustomerStatus);
}
