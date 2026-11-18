# QuickBite - Project Context

## Tổng quan
Food delivery microservices platform - học tập và portfolio.

## Tech Stack
- **Backend:** Java 21, Spring Boot 3.3, Spring Cloud 2024
- **Frontend:** Angular 19 (standalone + signals)
- **Database:** PostgreSQL 16, Redis 7
- **Messaging:** Apache Kafka, RabbitMQ
- **Infrastructure:** Docker, Eureka, Config Server, Zipkin

## Cấu trúc Project
- `infrastructure/` - discovery-server, config-server, api-gateway
- `services/` - user, restaurant, order, payment, delivery, notification
- `common/` - shared DTOs, events, exceptions
- `web-app/` - Angular frontend
- `docs/ba/` - Task descriptions cho dev workflow

## Workflow
- Dùng `/dev <path-to-task.md>` để bắt đầu task
- Dùng `/start <issue-url>` để tạo branch mới
- Dùng `/review` để review code trước khi tạo PR
- Dùng `/ship` để tạo PR và merge

## Git Convention
- Branch: `feature/<issue>-<short-name>`, `fix/<issue>-<short-name>`
- Commit: Conventional Commits format
- PR: Link GitHub Issue với `closes #xxx`

## Coding Standards
Đọc `.claude/skills/coding-standards/` trước khi code.
