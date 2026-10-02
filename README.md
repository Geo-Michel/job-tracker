# Job Tracker API

> One-line pitch: a REST API that helps job seekers track applications from wishlist to offer.

<!-- Badges: build status (GitHub Actions), Java version, license -->

**Live demo:** <link to Swagger UI on your deployment>

## Overview

<!-- 2-3 sentences. What problem does it solve? Who is it for? Why did you build it? -->

## Features

- User registration and login with JWT authentication
- CRUD for job applications, scoped to the authenticated user
- Filter by status, paginate and sort results
- Stats endpoint: number of applications per status
- Validation and consistent JSON error responses
- OpenAPI documentation via Swagger UI

## Tech stack

| Area | Choice |
|------|--------|
| Language | Java 21 |
| Framework | Spring Boot 3 (Web, Data JPA, Security, Validation) |
| Database | PostgreSQL, schema managed with Flyway |
| Auth | JWT |
| Testing | JUnit 5, Mockito, Testcontainers |
| Tooling | Maven, Docker Compose, GitHub Actions |

## Architecture

<!-- Add a small diagram or a short description of the layers:
     controller -> service -> repository -> database.
     Mention DTOs, exception handling, and where security is configured. -->

## Getting started

### Prerequisites
- JDK 21
- Docker

### Run locally
```bash
docker compose up -d      # starts PostgreSQL
mvnw spring-boot:run    # starts the API on http://localhost:8080
```
Swagger UI: http://localhost:8080/swagger-ui.html

### Run the tests
```bash
mvnw test
```
Integration tests start a throwaway PostgreSQL container, so Docker must be running.

## API overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Create an account |
| POST | `/api/auth/login` | Get a JWT |
| GET | `/api/applications` | List your applications (filter, page, sort) |
| POST | `/api/applications` | Create an application |
| GET | `/api/applications/{id}` | Get one application |
| PUT | `/api/applications/{id}` | Update an application |
| DELETE | `/api/applications/{id}` | Delete an application |
| GET | `/api/applications/stats` | Count per status |

## Design decisions

<!-- This section impresses interviewers. Short bullets on the "why":
     - Why DTOs instead of exposing entities?
     - Why Flyway instead of ddl-auto=update?
     - How do you prevent users from accessing each other's data?
     - Why this password hashing approach? -->

## Testing strategy

<!-- What do you unit test (services with Mockito) vs. integration test (controllers + DB with Testcontainers)? -->

## Roadmap

- [ ] Interview events attached to an application
- [ ] Status change history
- [ ] Simple frontend

## What I learned

<!-- Be honest and specific: one or two things that were hard and how you solved them. -->

## License

MIT
