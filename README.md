# Event Registration System

A web application to register users, manage events, and allow users to register for events. Includes **basic authentication using Spring Security** and data persistence using **PostgreSQL**.

---

## 1. Project Overview

- **Project Name:** Event Registration System
- **Description:** Manage users, events, and registrations with authentication and authorization.
- **Technology Stack:**
	- Java 17
	- Spring Boot
	- Spring Security (Basic Auth)
	- JPA / Hibernate
	- PostgreSQL
	- Maven
	- Optional: Insomnia / Postman for API testing

---

## 2. Features / Functionalities

The system supports:

- User registration and login
- CRUD operations for events (Create, Read, Update, Delete)
- User can register for events
- View all registrations and registrations per user
- Cancel a registration
- Data persisted in PostgreSQL

---

## 3. Setup Instructions

### Prerequisites

- Java JDK 17
- Maven
- PostgreSQL installed and running

### Database Setup

Connect to PostgreSQL and execute:

```sql
-- Create role for Spring Boot
CREATE ROLE eventuser WITH LOGIN PASSWORD 'password';

-- Create database
CREATE DATABASE eventdb;

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE eventdb TO eventuser;

# Spring Boot Configuration

spring.datasource.url=jdbc:postgresql://localhost:5432/eventdb
spring.datasource.username=eventuser
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Run the Project
mvn spring-boot:run

# API Documentation

## 1. Auth APIs
	Register User
	•	Endpoint: /api/auth/register
	•	Method: POST
	
	Request:
	{
  	"name": "Alice",
  	"email": "alice@example.com",
 	 "password": "pass"
	}

	Response :
	{
  	"id": 1,
  	"name": "Alice",
  	"email": "alice@example.com",
  	"roles": ["USER"]
	}