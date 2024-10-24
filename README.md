# Brokerage Firm API

## Overview
This project is a backend API for a brokerage firm. It provides endpoints for managing customer stock orders, deposits, withdrawals, and asset listings. The API is built using the Spring Boot framework and follows a modular and scalable design.

## Features
- **Create Order**: Allows employees to create stock orders for customers.
- **List Orders**: List orders for a customer within a specific date range.
- **Cancel Order**: Cancel pending orders only.
- **Deposit Money**: Deposit TRY for customers.
- **Withdraw Money**: Withdraw TRY with IBAN validation.
- **List Assets**: List all assets for a given customer.

## Rate Limiting
- **Limit**: 100 requests per 10 minutes.
- **Configuration**: Using Bucket4j for rate limiting, each user can make a maximum of 100 requests within a 10-minute window. After 10 minutes, the request count resets.

## Technologies Used
- **Spring Boot 3.3.4**: The primary framework for building the application.
- **H2 Database**: Used for in-memory storage.
- **JWT Authentication**: Used for securing endpoints.
- **Internationalization (i18n)**: Supports English and Turkish error messages.
- **Logging**: Logs important actions and errors.
- **Interceptor**: Used for IBAN validation.
- **Spring Boot Actuator**: Provides endpoints for monitoring and managing the application.
- **Custom Indicator**: A custom health indicator is used to monitor order counts.
- **Bucket4j**: Used for API rate limiting.

## Design Patterns
- **Service Layer Pattern**: Used to separate business logic from controllers.
- **Repository Pattern**: Used to abstract database operations.
- **Singleton Pattern**: Spring's default scope for beans.
- **Aspect-Oriented Programming (AOP)**: Used for logging and error handling.

## Custom Annotations
- **`@LocalizedMessage`**: Used to retrieve error messages based on the current locale.

## Spring Boot Actuator and Custom Indicator
We use **Spring Boot Actuator** to provide built-in endpoints to monitor and manage the application. In addition to built-in health checks, we added a **Custom Health Indicator** to monitor the count of orders in the system.

The **Custom Indicator** monitors the order count and changes the health status based on a threshold. For instance, if the number of orders is below a defined threshold, the health status will change to `DOWN`, indicating that the system requires attention.

## Logging
- Logs are configured using **logback-spring.xml** and provide detailed information about application activities and errors.

## Interceptor Usage
We use an Interceptor for validating IBAN numbers during withdrawal transactions. This ensures that only valid IBAN numbers are processed and helps maintain data integrity.

## Setup and Run
1. Clone the repository.
2. Build the project using `mvn clean install`.
3. Run the application using `mvn spring-boot:run`.
4. Access the API via `http://localhost:8080`.

## Database Structure
### Asset Table:
- **customerId**: The ID of the customer who owns the asset.
- **assetName**: The name of the asset (e.g., TRY).
- **size**: Total size of the asset owned by the customer.
- **usableSize**: Size of the asset that can be used.

### Order Table:
- **customerId**: The ID of the customer who made the order.
- **assetName**: The name of the asset in the order.
- **orderSide**: BUY or SELL.
- **size**: Number of shares in the order.
- **price**: Price per share.
- **status**: PENDING, MATCHED, or CANCELED.
- **createDate**: The date and time when the order was created.

## API Authorization
- All endpoints are secured using JWT authentication and are role-based with admin and user roles.

## Unit Testing
- The application includes comprehensive unit tests using JUnit and Mockito to ensure code reliability and coverage.
