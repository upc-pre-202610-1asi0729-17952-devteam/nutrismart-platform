# NutriSmart Platform

NutriSmart Platform is a RESTful API built on Domain-Driven Design (DDD) principles. Developed with Java 21 and Spring Boot 4, it provides the backend for a nutrition tracking and smart recommendation system. The codebase is organized into bounded contexts with strict CQRS separation between command and query operations.

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.0.6
- **Database:** MySQL 8+
- **Persistence:** Spring Data JPA / Hibernate
- **Security:** Spring Security with JWT (jjwt 0.12.6)
- **Documentation:** SpringDoc OpenAPI 3.0.2 (Swagger UI)
- **Build Tool:** Maven 3.9+

## Prerequisites

- Java 21 JDK
- Maven 3.9+
- MySQL 8.0+

## Project Structure

```text
src/main/java/com/devteam/nutrismart/platform/
├── iam/                    # Identity & Access Management (auth, users)
├── subscriptions/          # Subscription plans and billing history
├── nutritiontracking/      # Daily intake, food logs, nutrition balance
├── smartrecommendation/    # AI-powered recipes, pantry and recommendations
├── metabolicadaptation/    # Body metrics, activity logs, wearables
├── behavioralconsistency/  # Eating patterns and recovery plans
├── analytics/              # User progress analytics
└── shared/                 # Shared kernel (base classes, infrastructure)
```

## Configuration

Profile-based configuration in `src/main/resources`:
- `application.properties` — shared defaults
- `application-dev.properties` — local development (default)
- `application-prod.properties` — production

### Environment Variables

Required for the `prod` profile:

| Variable            | Description              | Default (Dev)   |
|---------------------|--------------------------|-----------------|
| `DATABASE_URL`      | MySQL host               | `localhost`     |
| `DATABASE_PORT`     | MySQL port               | `3306`          |
| `DATABASE_NAME`     | Database name            | `nutrismart`    |
| `DATABASE_USER`     | Database username        | `root`          |
| `DATABASE_PASSWORD` | Database password        | —               |
| `JWT_SECRET`        | Secret key for JWT signing | —             |
| `USDA_API_KEY`      | USDA FoodData API key    | —               |
| `DEEPSEEK_API_KEY`  | DeepSeek AI API key      | —               |
| `GEMINI_API_KEY`    | Gemini AI API key        | —               |
| `OPENWEATHER_KEY`   | OpenWeatherMap API key   | —               |
| `MAIL_USERNAME`     | SMTP email address       | —               |
| `MAIL_PASSWORD`     | SMTP app password        | —               |

## Getting Started

### 1. Database setup

Ensure MySQL is running and the database specified in `DATABASE_NAME` exists.

### 2. Run the application

```bash
# Development (default profile)
./mvnw clean spring-boot:run
```

### 3. API Documentation

Once running:
- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

## API Surface

| Context | Endpoints |
|---|---|
| **IAM** | `POST /api/v1/auth/login`, `POST /api/v1/auth/register`, `/api/v1/users` |
| **Subscriptions** | `/api/v1/subscriptions`, `/api/v1/billing-history` |
| **Nutrition Tracking** | `/api/v1/foods`, `/api/v1/nutrition-logs`, `/api/v1/daily-intake`, `/api/v1/daily-balance` |
| **Smart Recommendation** | `/api/v1/recipes`, `/api/v1/recommendation-sessions`, `/api/v1/pantry` |
| **Metabolic Adaptation** | `/api/v1/body-metrics`, `/api/v1/activity-logs`, `/api/v1/wearable-connections` |
| **Behavioral Consistency** | `/api/v1/eating-behavior-patterns`, `/api/v1/recovery-plans` |
| **Analytics** | `/api/v1/analytics` |

## Security Model

- **Authentication:** Stateless JWT-based.
- **Password storage:** BCrypt hashing.
- **Authorization:** `Authorization: Bearer <token>` header required on protected routes.

## Development Conventions

- **DDD + CQRS:** Write operations go through `CommandService`, reads through `QueryService`.
- **Persistence agnostic:** Domain aggregates have no JPA annotations; mapping is done via `PersistenceMapper` and `JpaEntity` classes.
- **Lombok:** Used only on JPA entities and DTOs; avoided in domain aggregates to maintain invariants.
- **Error handling:** Centralized via `GlobalExceptionHandler`.
- **i18n:** Supported via `Accept-Language` header (`en` default, `es` supported).
