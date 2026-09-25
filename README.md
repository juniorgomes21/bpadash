# BPADASH

Java/Spring Boot backend for a business management platform, providing REST APIs, database integration, authentication, authorization and business logic.

## Overview

BPADASH is a backend application developed with **Java and Spring Boot**, designed to support a business management platform through a structured REST API and relational database integration.

The project demonstrates practical experience with backend development, including:

- REST API development
- Business logic implementation
- Relational database integration
- Data persistence with JPA/Hibernate
- Authentication and authorization
- JWT-based security
- Request validation
- Email integration
- Object mapping between application layers

The application was developed as a real-world oriented backend project, focusing on building a maintainable foundation for business-oriented web applications.

## Tech Stack

### Backend

- **Java 17**
- **Spring Boot 2.6.1**
- **Spring Web**
- **Spring Data JPA**
- **Hibernate**
- **Spring Security**
- **JWT**
- **OAuth2**
- **Bean Validation**
- **ModelMapper**

### Database

- **MySQL**

### Supporting Technologies

- **Maven**
- **Spring Boot DevTools**
- **JavaMail / Spring Boot Mail**
- **Jasypt**
- **Apache Commons Lang**
- **dotenv-java**

## Key Backend Capabilities

### REST API

The backend exposes HTTP-based endpoints to support communication between the application and its clients.

The API layer is responsible for receiving requests, validating input and coordinating the execution of the corresponding business operations.

### Authentication & Authorization

Security is an important part of the application.

The project uses Spring Security together with JWT and OAuth2-related components to support authenticated access and authorization within the backend.

### Database Integration

The application uses **MySQL** as its relational database and **Spring Data JPA / Hibernate** for persistence.

This allows the backend to work with domain data through Java entities and repositories while keeping database access integrated with the application's business layer.

### Validation

Spring Boot Validation is used to support validation of incoming application data and help prevent invalid requests from reaching the business logic.

### Email Integration

The project includes Spring Boot Mail support for backend email-related operations.

### Object Mapping

**ModelMapper** is used to simplify the conversion between application objects, helping keep data-transfer concerns separated from persistence models.

## Project Structure

The project follows the conventional Maven/Spring Boot project structure:

```text
bpadash/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── .mvn/
├── pom.xml
├── mvnw
└── mvnw.cmd
```

The main application code is organized under `src/main/java`, while application resources and configuration are maintained under `src/main/resources`.

## Configuration

The application uses environment-based configuration for sensitive or environment-specific values.

Typical configuration includes database connection and application environment settings.

Sensitive credentials should be provided through environment variables or a local `.env` configuration rather than committed directly to the repository.

## Requirements

Before running the project, make sure you have:

- Java 17 or compatible JDK
- Maven (or use the included Maven Wrapper)
- MySQL

## Running the Project

Clone the repository:

```bash
git clone https://github.com/juniorgomes21/bpadash.git
cd bpadash
```

Configure the required environment variables and database connection.

Then build the application:

```bash
./mvnw clean package
```

On Windows:

```bash
mvnw.cmd clean package
```

Run the application with:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

## Development Notes

This repository represents a backend project built around a real business-oriented application scenario.

Its main purpose in this portfolio is to demonstrate practical experience with:

- Java backend development
- Spring Boot
- REST APIs
- Relational databases
- JPA/Hibernate
- Authentication and authorization
- JWT
- API validation
- Business logic
- Backend integrations

## License

This project is licensed under the MIT License.

Copyright (c) 2023 Junior Gomes.
