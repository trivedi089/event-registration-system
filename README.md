1. Project Overview
	•	Project name: Event Registration System
	•	Description: A web application to register users, manage events, and allow users to register for events. Includes basic authentication using Spring Security and data persistence using PostgreSQL.
	•	Technology Stack:
	  •	Java 17
	  •	Spring Boot
	  •	Spring Security (Basic Auth)
	  •	JPA / Hibernate
	  •	PostgreSQL
	  •	Maven
	  •	Optional: Insomnia for testing

2. Features / Functionalities

	List all features your system supports. 
	Example:
		•	User registration and login
		•	CRUD operations for events (Create, Read, Update, Delete)
		•	User can register for events
		•	View all registrations and registrations per user
		•	Cancel a registration
		•	Data persisted in PostgreSQL
 
3. Setup Instructions

	setup required to run the project :

		1.	Prerequisites:
			•	Java JDK 17
			•	Maven
			•	PostgreSQL installed and running
 
  		2. 	Database Setup:
  			CREATE ROLE eventuser WITH LOGIN PASSWORD 'password';
  			CREATE DATABASE eventdb;
  			GRANT ALL PRIVILEGES ON DATABASE eventdb TO eventuser;

  		3.	Spring Boot Configuration:
     		application.properties:
    			spring.datasource.url=jdbc:postgresql://localhost:5432/eventdb
      			spring.datasource.username=eventuser
      			spring.datasource.password=password
      			spring.jpa.hibernate.ddl-auto=update
      			spring.jpa.show-sql=true
      			spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

4.	Run the project:
    mvn spring-boot:run

5. API Documentation :
   
   	1. Auth APIs (AuthController)

		Handles user authentication and registration:
		Register User Endpoint : /api/auth/register
		Method : POST

  		Register Request:
		{
 		 "name": "Alice",
 		 "email": "alice@example.com",
 		 "password": "pass"
		}

  		Register Response:
		{
  		"id": 1,
  		"name": "Alice",
  		"email": "alice@example.com",
  		"roles": ["USER"]
		}

 		Login user Endpoint : /api/auth/login
		Method : POST

  		Login Request:
		{
 		 "email": "alice@example.com",
  		"password": "pass"
		}

		Login Response:
		Login successful for: alice@example.com
