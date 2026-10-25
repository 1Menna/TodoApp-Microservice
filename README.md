# ToDo App Backend

A **microservices-based** backend for a Todo application, built with **Spring Boot**, **MySQL**, and **JWT authentication**.
 
> 🎓 Graduation project for the **6-month Java Spring diploma**, under the supervision of **Eng. Ahmed Saleh**.

## Overview

ToDa is split into two independently deployable microservices that talk to each other over REST:

- **Todo Service** — lets a user add, update, delete, and search their todo items.
- **User Service** — handles registration, login, JWT-based authentication/authorization, and password reset via email OTP.

Every request to the Todo Service is validated by calling `/checkToken` on the User Service, so the two services stay decoupled while still enforcing auth centrally.

```
Client → Todo Service ──validates token──▶ User Service
Client → User Service (login, register, password reset, user management)
```

## Features

-  JWT authentication & authorization
-  Full CRUD on todo items, scoped per user
-  OTP-based password reset via email
-  API docs generated with Swagger
-  Unit tested with JUnit 5
-  Postman collection included for quick testing

## API Endpoints

### User Service

| Method | Endpoint | Body | Header | Description |
|---|---|---|---|---|
| `POST` | `/register` | User | -- |Register a new user, sends activation OTP via email |
| `POST` | `/regenerateOtp?email=` | -- | -- | Resend a new OTP |
| `POST` | `/activate?username=` | -- | OTP | Activate account using OTP |
| `POST` | `/login` | username, password | -- | Authenticate user, returns a JWT |
| `POST` | `/forgetPassword?username=` | -- | -- | Trigger password reset OTP |
| `POST` | `/changePassword` | User | JwtToken | Reset password using OTP |
| `POST` | `/checkToken` | -- | JwtToken | Validate a JWT (used internally by Todo Service) |


### Todo Service
*(all routes require a valid JWT — validated internally against the User Service)*

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/add` | Add a new todo item |
| `PUT` | `/update/{id}` | Update a todo item by id |
| `DELETE` | `/delete/{id}` | Delete a todo item by id |
| `GET` | `/search/{id}` | Search for a todo item by id |
 

## Tech Stack

- **Backend:** Java, Spring Boot
- **Database:** MySQL
- **Auth:** JWT
- **Docs:** Swagger / OpenAPI
- **Testing:** JUnit 5
- **API Testing:** Postman
- **Email:** JavaMail (OTP delivery)

---

## Getting Started

```bash
# Clone the repo
git clone <repo-url>
cd toda-app-backend

# Configure your MySQL connection in application.properties / application.yml
# for each service (todo-service & user-service)

# Run each service
./mvnw spring-boot:run
```

> Update the database credentials, JWT secret, and mail server settings before running.

---

