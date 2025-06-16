# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a sample Spring Boot e-commerce application designed for security testing and experimentation. It implements intentionally vulnerable code patterns for testing security analysis tools like CodeQL.

## Build and Run Commands

- **Build the project**: `mvn clean compile`
- **Run the application**: `mvn spring-boot:run`
- **Run tests**: `mvn test`
- **Package the application**: `mvn package`

The application runs on port 8080 by default. Access it at http://localhost:8080.

## Architecture

**Framework**: Spring Boot 3.5.0 with Java 17
**Database**: H2 in-memory database
**Security**: Spring Security with form-based authentication
**Template Engine**: Thymeleaf
**Build Tool**: Maven

### Key Components

- **Entities**: User, Product, Cart, CartItem, Order, OrderItem, PointHistory
- **Repositories**: In-memory implementations for all entities (no actual JPA/database persistence)
- **Services**: Business logic layer with UserService, ProductService, CartService, OrderService, PaymentService, PointService
- **Controllers**: Web layer with AuthController, ProductController, CartController, CheckoutController, HomeController
- **Security**: Custom SecurityConfig with form login, configured in `SecurityConfig.java`

### Data Flow

1. Users authenticate through Spring Security (login/register)
2. Products are displayed from in-memory repository
3. Cart operations modify user's cart through CartService
4. Checkout process creates orders and processes payments
5. Point system tracks user rewards through PointService

### Security Configuration

- Form-based authentication with custom login page
- In-memory user storage with BCrypt password encoding
- Public access to home, register, and static resources
- All other endpoints require authentication

## Development Notes

The codebase includes Japanese comments and is structured as a learning/testing environment. When making changes, maintain the existing vulnerability patterns as they are intentional for security testing purposes.