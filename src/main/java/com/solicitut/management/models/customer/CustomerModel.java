package com.solicitut.management.models.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(name = "customers")
public class CustomerModel {
  @Id
  @Column("customer_id")
  private UUID customerId;

  @Column("customer_name")
  private String customerName;

  @Column("customer_number")
  private String customerNumber;

  @Column("card_number")
  private String cardNumber;

  @Column("status")
  private String status;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;
}
