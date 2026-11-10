# BookNest 📚

## A Personal Digital Library & Reading Tracker

BookNest is a personal digital library platform that helps readers organize their book collections, track reading progress, and build better reading habits.

Users can discover books through an external book catalog, manage their personal libraries, monitor their reading progress, and explore their reading statistics.

## Features

### Core Features (MVP)

- **Authentication:** User registration, login, and secure JWT-based authentication.
- **Book Discovery:** Search and discover books using an external book catalog API.
- **Personal Library:** Add and manage books in a private personal library.
- **Reading Status:** Organize books into Want to Read, Currently Reading, Completed, and Dropped.
- **Reading Progress:** Track current pages and reading completion.
- **Reading History:** Review previously completed books.
- **Reading Statistics:** Monitor reading activity and personal achievements.

### Planned Features

- Reading goals and reading streaks.
- Personal notes and favorite quotes.
- Book ratings and reviews.
- Custom book collections and shelves.
- User profiles and social interactions.
- Personalized book recommendations.

## Architecture

BookNest follows a microservices architecture to separate business responsibilities and support independent development and deployment.

Planned services:

| Service         | Responsibility                                      |
| --------------- | --------------------------------------------------- |
| Auth Service    | User accounts and authentication                    |
| Book Service    | Book catalog and external book API integration      |
| Library Service | Personal libraries and reading progress             |
| Social Service  | Reviews, ratings, and social interactions (planned) |
| API Gateway     | Request routing and centralized entry point         |
| Eureka Server   | Service discovery                                   |

### Infrastructure

- **PostgreSQL:** Persistent data storage.
- **Redis:** Caching.
- **RabbitMQ:** Asynchronous communication between services.
- **Docker Compose:** Containerization and local development.

## Technology Stack

| Category           | Technologies                 |
| ------------------ | ---------------------------- |
| Backend            | Java 21, Spring Boot         |
| Frontend           | React, TypeScript            |
| Database           | PostgreSQL                   |
| ORM                | Spring Data JPA, Hibernate   |
| Security           | Spring Security, JWT         |
| Microservices      | Spring Cloud Gateway, Eureka |
| Messaging          | RabbitMQ                     |
| Caching            | Redis                        |
| Database Migration | Flyway                       |
| Deployment         | Docker, Docker Compose       |

## Project Structure

```text
booknest/
├── backend/
│   ├── auth-service/
│   ├── book-service/
│   ├── library-service/
│   ├── api-gateway/
│   └── discovery-server/
│
├── frontend/
│
├── infrastructure/
│   └── docker-compose.yml
│
└── README.md
```

## Getting Started

### Prerequisites

- Java 21+
- Node.js and npm
- PostgreSQL
- Docker and Docker Compose
- Gradle

### Installation

Installation and deployment instructions will be added as the services and frontend are implemented.

## Development Roadmap

- [ ] Define database schemas and service boundaries.
- [ ] Implement Auth Service.
- [ ] Implement Book Service and external book catalog integration.
- [ ] Implement Library Service.
- [ ] Configure API Gateway and service discovery.
- [ ] Develop the React frontend.
- [ ] Integrate Redis and RabbitMQ where needed.
- [ ] Write unit and integration tests.
- [ ] Containerize and deploy the application.

## Project Goals

BookNest is a portfolio project focused on demonstrating practical backend engineering skills, including:

- Microservices architecture and service communication.
- RESTful API design.
- Authentication and authorization with JWT.
- Database design and data ownership.
- External API integration.
- Caching and asynchronous processing.
- Containerization and deployment.

---

**Project Status:** In Development
