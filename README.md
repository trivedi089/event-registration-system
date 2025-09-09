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

---
```
#	Spring Boot Configuration

- spring.datasource.url=jdbc:postgresql://localhost:5432/eventdb
- spring.datasource.username=eventuser
- spring.datasource.password=password

- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true
- spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

---

# Run the Project
- mvn spring-boot:run

---

## API Documentation

### Auth APIs (AuthController)

**Register User**  
- **Endpoint:** `/api/auth/register`  
- **Method:** POST  
- **Description:** Registers a new user with default role "USER".  
**Request:**
```json
{
  "name": "Alice",
  "email": "alice@example.com",
  "password": "pass"
}
```
**Response:**
```json
{
  "id": 1,
  "name": "Alice",
  "email": "alice@example.com",
  "roles": ["USER"]
}
```
- **Endpoint:** `/api/auth/login`  
- **Method:** POST  
- **Description:** Authenticates a user and returns a success message.  
**Request:**
```json
{
  "email": "alice@example.com",
  "password": "pass"
}
```
**Response:**
Login successful for: alice@example.com

### Event APIs (EventController)

### Create Event

**Endpoint:** `/api/events`  
**Method:** POST  
**Description:** Creates a new event.  
**Request:**
```json
{
  "name": "Hackathon",
  "date": "2025-10-01",
  "location": "Pune",
  "description": "24-hour coding event"
}
```
**Response:**
```json
{
  "id": 4,
  "name": "Hackathon",
  "date": "2025-10-01",
  "location": "Pune",
  "description": "24-hour coding event"
}
```
**Get All Events**  
- **Endpoint:** `/api/events`  
- **Method:** GET  
- **Description:** Retrieves a list of all events.  
**Response:**
```json
[
  {
    "id": 1,
    "name": "Meetup",
    "date": "2025-09-12",
    "location": "Mumbai",
    "description": ""
  },
  {
    "id": 2,
    "name": "Workshop",
    "date": "2025-09-15",
    "location": "Bangalore",
    "description": ""
  },
  {
    "id": 3,
    "name": "Conference",
    "date": "2025-09-20",
    "location": "Delhi",
    "description": ""
  }
]
```
**Update Event**  
- **Endpoint:** `/api/events/{id}`  
- **Method:** PUT  
- **Description:** Updates an existing event by its ID.  
**Request:**
```json
{
  "name": "Updated Hackathon",
  "date": "2025-10-02",
  "location": "Pune",
  "description": "48-hour coding event"
}
```
**Delete Event**  
- **Endpoint:** `/api/events/{id}`  
- **Method:** DELETE  
- **Description:** Deletes an event by its ID.  
**Response:**
```json
{
  "message": "Event deleted successfully"
}
```
### Registration APIs (RegistrationController)

**Register User for Event**  
- **Endpoint:** `/api/registrations/register/{userId}/{eventId}`  
- **Method:** POST  
- **Description:** Registers a user for a specific event.  
**Response:**
```json
{
  "id": 1,
  "user": {
    "id": 1,
    "name": "Alice",
    "email": "alice@example.com",
    "roles": ["USER"]
  },
  "event": {
    "id": 1,
    "name": "Meetup",
    "date": "2025-09-12",
    "location": "Mumbai",
    "description": ""
  },
  "status": "CONFIRMED"
}
```
### Get All Registrations

- **Endpoint:** `/api/registrations`  
- **Method:** GET  
- **Description:** Retrieves a list of all registrations in the system.  
- **Response:**
```json
[
  {
    "id": 1,
    "user": {
      "id": 1,
      "name": "Alice",
      "email": "alice@example.com",
      "roles": ["USER"]
    },
    "event": {
      "id": 1,
      "name": "Meetup",
      "date": "2025-09-12",
      "location": "Mumbai",
      "description": ""
    },
    "status": "CONFIRMED"
  },
  {
    "id": 2,
    "user": {
      "id": 2,
      "name": "Bob",
      "email": "bob@example.com",
      "roles": ["USER"]
    },
    "event": {
      "id": 2,
      "name": "Workshop",
      "date": "2025-09-15",
      "location": "Bangalore",
      "description": ""
    },
    "status": "CONFIRMED"
  }
]
```
### Get Registrations by User

- **Endpoint:** `/api/registrations/user/{userId}`  
- **Method:** GET  
- **Description:** Retrieves all registrations for a specific user based on their `userId`.  
- **Response:**
```json
[
  {
    "id": 1,
    "user": {
      "id": 1,
      "name": "Alice",
      "email": "alice@example.com",
      "roles": ["USER"]
    },
    "event": {
      "id": 1,
      "name": "Meetup",
      "date": "2025-09-12",
      "location": "Mumbai",
      "description": ""
    },
    "status": "CONFIRMED"
  },
  {
    "id": 3,
    "user": {
      "id": 1,
      "name": "Alice",
      "email": "alice@example.com",
      "roles": ["USER"]
    },
    "event": {
      "id": 3,
      "name": "Conference",
      "date": "2025-09-20",
      "location": "Delhi",
      "description": ""
    },
    "status": "CONFIRMED"
  }
]
```
### Cancel Registration

- **Endpoint:** `/api/registrations/cancel/{registrationId}`  
- **Method:** PUT  
- **Description:** Cancels a registration by updating its status to `"CANCELLED"`.  
- **Response:**
```json
{
  "id": 1,
  "user": {
    "id": 1,
    "name": "Alice",
    "email": "alice@example.com",
    "roles": ["USER"]
  },
  "event": {
    "id": 1,
    "name": "Meetup",
    "date": "2025-09-12",
    "location": "Mumbai",
    "description": ""
  },
  "status": "CANCELLED"
}
```
