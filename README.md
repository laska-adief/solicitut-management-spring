# Solicitut Management Spring

Solicitut Management Spring is a CRUD application built with Spring Boot that manages data using a Reactive Spring Web architecture. This application is connected to a PostgreSQL database.

## Features
### Customer Management

#### Customers Table
```
CREATE TABLE customers (
 customer_id UUID primary key default gen_random_uuid(),
 customer_name VARCHAR(255),
 customer_number VARCHAR(255),
 card_number VARCHAR(255),
 status VARCHAR(20),
 created_at TIMESTAMP default CURRENT_TIMESTAMP,
 updated_at TIMESTAMP default CURRENT_TIMESTAMP
)
```

#### api/customer/list
Retrieve data list of customers that also implement pagination, sorting, and filtering (filterCustomerName, filterCustomerStatus)
```
localhost:8080/api/customer/list?size={size}&page={page}&sortName={column}&sortValue={value}&filterCustomerName={name}&filterCustomerStatus={status}
```

Description
- size : Specifies the number of items to display per page (pagination)
- page : The page number to retrieve. This is used in conjunction with size for pagination
- sortName : The name of the field/column to sort by. This determines how the data will be ordered (customerName, customerNumber, cardNumber, status)
- sortValue : The direction of sorting (ascending or descending). Typically, this will be either asc for ascending or desc for descending order
- filterCustomerName : A filter to search for customers by their name. This allows the user to narrow down the results to only those that match the provided customer name
- filterCustomerStatus : A filter to search for customers by their status. This filter matches exactly and is case-sensitive

## Technologies Used

Java Spring Boot – Backend framework for building the application.
Spring WebFlux – Reactive programming model for building non-blocking web applications.
PostgreSQL – Relational database for storing customer data.

## Tools Environtment
- [JDK version 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Postgresql](https://www.postgresql.org/download/)

## Setup Environtment
- Create database and tables in postgresql
- Setup database configs (database name, username, and password) in ``` src/main/resources/application.properties ```

## Usage
Once application is running, you can navigate using the default port http://localhost:8080

## Next Step
Upcoming features include:
- Customer detail
- Add, edit, and delete customer functionality
