# Responsibility

- Owns data: list tables/entities.
- Exposes APIs: you already have a first draft (from earlier answer).
- Depends on: other services or Kafka topics.

## Example:
```text
Finance Service
Owns: accounts, categories, transactions, budgets.
Exposes: transaction CRUD, account & category CRUD, basic summaries.
Depends on: Auth for user identity (via JWT claims), no direct DB FK.
```