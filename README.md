# personal-finance-investment-tracker

## Stack:

- Backend: Java, Spring Boot, Spring Data JPA, Spring Security (JWT), Postgres
- Version Control: Git
- Build Tool: Gradle
- Frontend: React + TypeScript, React Query, Material UI
- Infra: Docker Compose (backend + Postgres + frontend)

## V1

### V1 Architecture: Minimum Viable Product (MVP)

``` text
    This version focuses on the essential components to deliver basic user and financial CRUD (Create, Read, Update, Delete) functionality.
```

Core Components

| Component | Technology     | Rationale                                                                                    |
|-----------|----------------|----------------------------------------------------------------------------------------------|
| Backend   | Java (Quarkus) | "High-performance, low-memory footprint for efficient containerization."                     |
| Database  | PostgreSQL     | "Industry standard for strong transactional integrity (ACID), essential for financial data." |
| Security  | JWT Token      | Used for stateless authentication between services.                                          |

## V2

### V2 Architecture: Target Design (Roadmap)

``` text
This version includes all planned services and introduces the Event-Driven Architecture (EDA) with Kafka, which is a major project goal and will require significant effort to learn and implement correctly.
```

Key Learning Topics for V1 to V2 Transition

| Topic                     | Rationale for V2                                                                                       |
|---------------------------|--------------------------------------------------------------------------------------------------------|
| Apache Kafka              | Essential for implementing the asynchronous reporting and notification patterns.                       |
| Change Data Capture (CDC) | The mechanism to reliably feed data from your core databases (finance_db) into Kafka for reporting.    |
| Polyglot Persistence      | Understanding when to use different databases (like MongoDB for notifications) and how to manage them. |