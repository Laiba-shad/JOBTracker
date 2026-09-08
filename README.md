# JOBTracker -  Job Application Tracker

## Project Overview

JOBTrackr is a tracking system that helps users organize job opportunities throughout the hiring process. Users can create an account, verify their email with a one-time password, sign in securely, and manage job application records with details such as company, role, location, salary, description, link, and application status.

The project is structured as a Spring Boot REST API.

## Features

- User registration with email and password validation
- Email verification using a time-limited six-digit OTP
- OTP expiration and failed-attempt handling
- BCrypt password hashing
- JWT-based stateless authentication
- Job application CRUD operations
- Job status tracking across stages such as Bookmarked, Applying, Applied, Interviewing, Accepted, Rejected, and Not Replied
- DTO-based request and response models
- Centralized exception handling for API errors
- JPA persistence with H2 for local development and MySQL support
- React and Vite frontend foundation for the client application

## Application Flow

1. A user registers with an email, username, and password.
2. The API sends a six-digit verification OTP by email.
3. The user verifies the OTP before logging in.
4. Successful login returns a JWT token.
5. Authenticated users can create, view, update, and delete job application records.

## API Endpoints

### Authentication

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/auth/register` | Create a new account and send an OTP |
| `POST` | `/api/auth/login` | Authenticate a verified user and return a JWT |
| `POST` | `/api/auth/send-otp` | Send a verification OTP |
| `POST` | `/api/auth/resend-otp` | Resend a verification OTP |
| `POST` | `/api/auth/verify-otp` | Verify the email OTP |

### Job Applications

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/api/job/{userId}` | Create a job application |
| `GET` | `/api/job` | Retrieve all job applications |
| `GET` | `/api/job/{id}` | Retrieve one job application |
| `PUT` | `/api/job/{id}` | Update a job application |
| `DELETE` | `/api/job/{id}` | Delete a job application |

## Tech Stack

### Backend

- Java 17
- Spring Boot 3.2.5
- Spring Web
- Spring Data JPA and Hibernate
- Spring Security
- Jakarta Bean Validation
- JSON Web Tokens with JJWT
- Spring Mail
- Lombok
- H2 database for development
- MySQL connector for production database support
- Maven

### Frontend

- React 19
- TypeScript
- Vite
- ESLint
- CSS

## Project Structure

```text
JOBTrackr/
├── frontend/                 # React and Vite client
├── src/main/java/            # Spring Boot application and REST API
│   └── com/jobtrackr/server/
│       ├── config/           # Security and password configuration
│       ├── controller/       # Authentication and job endpoints
│       ├── dto/              # API request and response models
│       ├── model/            # JPA entities
│       ├── repo/             # Spring Data repositories
│       ├── service/          # Business logic
│       └── utils/             # JWT utilities
├── src/main/resources/       # Application configuration
├── pom.xml                   # Maven configuration
└── mvnw.cmd                  # Maven Wrapper for Windows
```

## Getting Started

### Prerequisites

- Java 17 or later
- Maven, or the included Maven Wrapper
- Node.js and npm
- An SMTP account for OTP email delivery when using email verification

### Run the Backend on Windows

From the project root:

```powershell
.\mvnw.cmd spring-boot:run
```

The API starts at `http://localhost:8080` by default.

### Run the Frontend

Open a second terminal:

```powershell
cd frontend
npm install
npm run dev
```

The Vite development server runs at the URL shown in the terminal, normally `http://localhost:5173`.

### Build the Backend

```powershell
.\mvnw.cmd clean package
```

### Build the Frontend

```powershell
cd frontend
npm run build
```

### Lint the Frontend

```powershell
cd frontend
npm run lint
```

## Configuration

Backend configuration is stored in `src/main/resources/application.properties`. The default profile uses an in-memory H2 database and enables the H2 console at `/h2-console` for local development.

Before deploying, replace development credentials and secrets with environment-backed values, especially:

- `jwt.secret`
- `spring.mail.username`
- `spring.mail.password`
- Database connection properties

## Portfolio Summary

**JOBTrackr** is a full-stack job application tracker built with Spring Boot, Spring Security, JWT, JPA, and React. I designed a REST API that supports secure account registration, email OTP verification, and stateless authentication, then implemented CRUD operations for tracking job applications across customizable hiring stages. The project demonstrates backend architecture, data persistence, validation, authentication, exception handling, and integration between a Java API and a modern TypeScript frontend.

## Current Status

The backend authentication and job-management API are implemented. The React frontend currently provides the Vite application foundation and is ready to be extended with the authentication and job-tracking screens.
