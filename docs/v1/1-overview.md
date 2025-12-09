# Sections:

## Goal
- “Personal Finance & Investment Tracker that lets users track expenses, accounts, investments, and get weekly summary emails.”

## Core Features (MVP)
- Record income/expense transactions.
- Track accounts and balances.
- Track investments & SIPs.
- Dashboard (monthly summary, category split).
- Weekly statement emails.

## Non-Functional Requirements
- Single user-focused, but design as if multi-tenant.
- Per-request latency target (e.g., <300 ms for dashboard).
- Data integrity: financial correctness > performance.
- Auditing: every transaction/investment change is traceable.

## Service Landscape
- Auth Service
- Finance Service
- Investment Service
- Reporting Service
- Notification Service
- API Gateway

## Tech Stack
- Backend, DB, Kafka, React, Docker, etc.