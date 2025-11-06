# Event-Driven Microservices

A comprehensive microservices architecture implementation using Spring Boot and Spring Cloud.

## Project Structure

### Section 1: Core Microservices

- **nvm10-bom**: Bill of Materials (BOM) for dependency management
  - **common**: Shared library with common DTOs and utilities
- **eurekaserver**: Service discovery server (Port: 8070)
- **gatewayserver**: API Gateway with composite endpoints (Port: 8072)
- **accounts**: Accounts microservice (Port: 8080)
- **cards**: Cards microservice (Port: 8082)
- **customer**: Customer microservice (Port: 8081)
- **loans**: Loans microservice (Port: 8083)

## Technology Stack

- **Java 21**
- **Spring Boot 3.3.4**
- **Spring Cloud 2023.0.3**
- **Spring Cloud Gateway**
- **Spring Cloud Netflix Eureka**
- **H2 Database**
- **Lombok**
- **Maven**

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Build the Common Library

First, build and install the BOM and common library:

```bash
cd section1/nvm10-bom
mvn clean install -N

cd common
mvn clean install
```

### Running the Services

Start the services in the following order:

1. **Eureka Server** (Service Discovery)
```bash
cd section1/eurekaserver
mvn spring-boot:run
```
Access at: http://localhost:8070

2. **Microservices** (in any order)
```bash
# Accounts Service
cd section1/accounts
mvn spring-boot:run

# Cards Service
cd section1/cards
mvn spring-boot:run

# Customer Service
cd section1/customer
mvn spring-boot:run

# Loans Service
cd section1/loans
mvn spring-boot:run
```

3. **Gateway Server**
```bash
cd section1/gatewayserver
mvn spring-boot:run
```
Access at: http://localhost:8072

## API Endpoints

### Gateway Composite Endpoint

**Fetch Customer Summary**
```
GET http://localhost:8072/api/composite/fetchCustomerSummary?mobileNumber={mobileNumber}
```

### Individual Service Endpoints (via Gateway)

**Customer Service**
```
GET http://localhost:8072/nvm10/customer/api/fetch?mobileNumber={mobileNumber}
```

**Accounts Service**
```
GET http://localhost:8072/nvm10/accounts/api/fetch?mobileNumber={mobileNumber}
```

**Cards Service**
```
GET http://localhost:8072/nvm10/cards/api/fetch?mobileNumber={mobileNumber}
```

**Loans Service**
```
GET http://localhost:8072/nvm10/loans/api/fetch?mobileNumber={mobileNumber}
```

## Architecture Features

- **Service Discovery**: Eureka for dynamic service registration and discovery
- **API Gateway**: Spring Cloud Gateway with routing and filtering
- **Composite Pattern**: Aggregates data from multiple microservices
- **Error Handling**: Graceful degradation when optional services are unavailable
- **Load Balancing**: Client-side load balancing via Ribbon/Spring Cloud LoadBalancer

## Development

### Building All Services

```bash
cd section1
mvn clean install
```

### Running Tests

```bash
mvn test
```

## License

This project is for educational purposes.
