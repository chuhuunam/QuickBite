# QuickBite - Claude Dev Workflow Design

**Created:** 2026-11-18  
**Status:** APPROVED  
**Author:** Team QuickBite

---

## 1. Overview

Custom `.claude` development workflow cho QuickBite project - food delivery microservices platform. Workflow hỗ trợ team nhiều người với quy trình đầy đủ 6 phases, không dùng Jira/Confluence.

### Goals
- Quy trình dev rõ ràng: Phân tích → Thiết kế → Code → Review → Test → Merge
- Coding standards thống nhất cho Java 21/Spring Boot và Angular 19
- Claude hỗ trợ review code trước khi tạo PR
- Task tracking qua GitHub Issues + local TODO.md
- Git workflow theo GitHub Flow

### Tech Stack
| Layer | Technology |
|-------|------------|
| Backend | Java 21, Spring Boot 3.3, Spring Cloud 2024 |
| Frontend | Angular 19 (standalone + signals) |
| Database | PostgreSQL 16, Redis 7 |
| Messaging | Apache Kafka, RabbitMQ |
| Infrastructure | Docker, Eureka, Config Server, Zipkin |

---

## 2. Directory Structure

```
d:\QuickBite\.claude\
├── CLAUDE.md                    # Project context cho Claude
├── commands/
│   ├── dev.md                   # /dev - Kích hoạt dev mode
│   ├── start.md                 # /start - Tạo branch từ GitHub Issue
│   ├── review.md                # /review - Claude review code
│   └── ship.md                  # /ship - Tạo PR và merge
├── skills/
│   ├── dev-workflow/
│   │   ├── SKILL.md             # Main workflow 6 phases
│   │   └── references/
│   │       ├── phase-analysis.md
│   │       ├── phase-design.md
│   │       ├── phase-coding.md
│   │       ├── phase-review.md
│   │       ├── phase-test.md
│   │       └── phase-merge.md
│   ├── coding-standards/
│   │   ├── SKILL.md             # Index
│   │   ├── be-standards.md      # Java 21 / Spring Boot 3.3
│   │   ├── fe-standards.md      # Angular 19
│   │   ├── db-standards.md      # PostgreSQL
│   │   └── git-standards.md     # Conventional Commits
│   └── github-workflow/
│       └── SKILL.md             # GitHub Issues + PR automation
└── agents/
    ├── code-reviewer.md         # Review code changes
    ├── test-writer.md           # Generate unit/integration tests
    └── doc-writer.md            # Generate API docs, README
```

### Task Files Location

```
docs/
├── ba/                              # Task descriptions (như tài liệu BA)
│   ├── user-service/
│   │   ├── TASK-001-user-registration.md
│   │   └── TASK-002-jwt-refresh.md
│   ├── order-service/
│   │   └── TASK-001-create-order.md
│   └── ...
├── api/                             # API documentation
└── diagrams/                        # Architecture diagrams
```

---

## 3. Commands

### 3.1 `/dev` - Kích hoạt Developer Mode

**Trigger:** `/dev <path-to-task.md>` hoặc `/dev <mô-tả-task>`

**Chức năng:**
- Load dev-workflow skill
- Đọc file task từ `docs/ba/` để hiểu scope
- Load coding-standards phù hợp (BE/FE/cả hai)
- Tạo file `.todo/TODO-<task-name>.md` để track progress
- Hiển thị checklist 6 phases

**Example:**
```
/dev docs/ba/user-service/TASK-001-user-registration.md
```

### 3.2 `/start` - Bắt đầu Task mới

**Trigger:** `/start <github-issue-url>` hoặc `/start <tên-feature>`

**Chức năng:**
- Fetch issue info từ GitHub (nếu có URL)
- Tạo branch theo convention: `feature/<issue-number>-<short-name>`
- Push branch lên origin với `-u`
- Tạo TODO.md với thông tin task

**Branch naming:**
- Feature: `feature/<issue>-<short-name>`
- Bugfix: `fix/<issue>-<short-name>`
- Hotfix: `hotfix/<issue>-<short-name>`

### 3.3 `/review` - Code Review

**Trigger:** `/review [path]` (để trống = all changes)

**Chức năng:**
- Chạy `git diff` để xác định changes
- Load coding-standards tương ứng (BE/FE)
- Review theo checklist: bugs, security, convention, test coverage
- Output báo cáo với severity levels

**Output format:**
```
## Tóm tắt
<1-3 câu đánh giá tổng quan>

## 🔴 Blocker (phải sửa trước khi merge)
- file:line — vấn đề → đề xuất

## 🟠 Nên sửa
- ...

## 🟡 Gợi ý
- ...

## ✅ Điểm tốt
- ...
```

### 3.4 `/ship` - Hoàn thành và Merge

**Trigger:** `/ship`

**Chức năng:**
- Chạy `/review` tự động
- Nếu không có 🔴 Blocker → tạo PR trên GitHub
- PR description tự động từ commits + TODO.md
- Link PR với GitHub Issue (closes #xxx)

---

## 4. Dev Workflow (6 Phases)

### Phase 1: Phân tích (Analysis)

**Input:** File task từ `docs/ba/`  
**Output:** Hiểu rõ scope, acceptance criteria

**Actions:**
- Đọc file task description
- Xác định service(s) liên quan (BE/FE/cả hai)
- Liệt kê files cần thay đổi
- Ghi vào TODO.md phần "Scope"

**Checkpoint:** Xác nhận với user trước khi tiếp tục

### Phase 2: Thiết kế (Design)

**Input:** Scope từ Phase 1  
**Output:** Technical design ngắn gọn

**Actions:**
- API design (endpoints, request/response)
- Data model changes (nếu có)
- Component structure (FE)
- Sequence diagram (nếu phức tạp)
- Ghi vào TODO.md phần "Design"

**Checkpoint:** User approve design trước khi code

### Phase 3: Coding

**Input:** Design đã approve  
**Output:** Code hoàn chỉnh

**Actions:**
- Tuân theo coding-standards (BE/FE)
- Commit thường xuyên với conventional commits
- Update TODO.md progress
- Self-check trước khi sang phase tiếp

### Phase 4: Review

**Input:** Code changes  
**Output:** Review report

**Actions:**
- Claude review theo checklist
- Fix tất cả 🔴 Blocker
- Fix hoặc ghi nhận 🟠 Nên sửa
- Xem xét 🟡 Gợi ý

### Phase 5: Test

**Input:** Code đã review  
**Output:** Tests pass

**Actions:**
- Viết/update unit tests
- Viết integration tests (nếu cần)
- Chạy `mvn test` / `ng test`
- Đảm bảo coverage không giảm

### Phase 6: Merge

**Input:** Code tested  
**Output:** PR merged

**Actions:**
- Tạo PR với description đầy đủ
- Link GitHub Issue (closes #xxx)
- Request team review
- Merge sau khi approved
- Delete feature branch

---

## 5. Coding Standards

### 5.1 Backend Standards (`be-standards.md`)

**Java 21 / Spring Boot 3.3:**

- **Layered architecture:** controller → service → repository
- **Naming conventions:**
  - Class: PascalCase (`UserService`, `OrderController`)
  - Method: camelCase (`findById`, `createOrder`)
  - Constant: UPPER_SNAKE_CASE (`MAX_RETRY_COUNT`)
- **DTO pattern:** Request/Response DTOs riêng biệt
- **Mapping:** Dùng MapStruct
- **Exception handling:** GlobalExceptionHandler với @ControllerAdvice
- **Logging:** SLF4J + structured logs
- **JPA:** Avoid N+1, dùng projections khi cần
- **Inter-service:** Feign client
- **Events:** Kafka/RabbitMQ với naming convention `quickbite.<domain>`

### 5.2 Frontend Standards (`fe-standards.md`)

**Angular 19:**

- **Components:** Standalone (không dùng NgModules)
- **State:** Signals cho reactive state
- **Pattern:** Smart/Dumb components
- **Injection:** Dùng `inject()` function
- **RxJS:** `takeUntilDestroyed()` cho subscriptions
- **Forms:** Reactive Forms
- **HTTP:** Interceptors cho auth, error handling
- **Structure:**
  ```
  web-app/src/app/
  ├── core/           # Services, guards, interceptors
  ├── shared/         # Shared components, pipes, directives
  ├── features/       # Feature modules (lazy loaded)
  └── layouts/        # Layout components
  ```

### 5.3 Database Standards (`db-standards.md`)

**PostgreSQL:**

- **Naming:** snake_case cho tables và columns
- **Primary key:** `id BIGSERIAL PRIMARY KEY`
- **Timestamps:** `created_at`, `updated_at`
- **Indexes:** Tạo index cho foreign keys và frequently queried columns
- **Migrations:** Flyway với naming `V{version}__{description}.sql`

### 5.4 Git Standards (`git-standards.md`)

**Conventional Commits:**

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

**Types:**
- `feat` - Tính năng mới
- `fix` - Sửa bug
- `docs` - Documentation
- `style` - Formatting, không thay đổi logic
- `refactor` - Refactoring code
- `test` - Thêm/sửa tests
- `chore` - Maintenance tasks

**Scopes:** `user-service`, `order-service`, `restaurant-service`, `payment-service`, `delivery-service`, `notification-service`, `web-app`, `gateway`, `infra`, `common`

**Examples:**
```
feat(user-service): add JWT refresh endpoint
fix(order-service): handle null cart items
docs(api): update authentication flow
refactor(common): extract shared DTOs to common module
```

---

## 6. Agents

### 6.1 Code Reviewer (`code-reviewer.md`)

**Trigger:** `/review` hoặc tự động trong workflow  
**Tools:** Read, Grep, Glob, Bash

**Checklist:**
- Tính đúng đắn: logic, null-safety, edge cases, exception handling
- Security: không hardcode secrets, validate input, prevent injection
- Convention: đúng layering, naming, coding standards
- Performance: N+1 queries, memory leaks, unnecessary loops
- Test coverage: có test cho code mới không

### 6.2 Test Writer (`test-writer.md`)

**Trigger:** Yêu cầu viết test hoặc trong Phase 5  
**Tools:** Read, Write, Edit, Bash

**Chức năng:**
- Đọc source code cần test
- Generate unit tests (JUnit 5 / Jasmine)
- Generate integration tests (nếu cần)
- Đảm bảo coverage cho happy path + edge cases
- Follow existing test patterns trong project

### 6.3 Doc Writer (`doc-writer.md`)

**Trigger:** Yêu cầu viết docs hoặc sau khi ship  
**Tools:** Read, Write, Edit

**Chức năng:**
- Generate/update API documentation
- Update README nếu có thay đổi lớn
- Generate changelog từ commits
- Update ARCHITECTURE.md nếu có thay đổi kiến trúc

---

## 7. CLAUDE.md Template

```markdown
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
- Dùng `/review` để review code trước khi tạo PR
- Dùng `/ship` để tạo PR và merge

## Git Convention
- Branch: `feature/<issue>-<short-name>`, `fix/<issue>-<short-name>`
- Commit: Conventional Commits format
- PR: Link GitHub Issue với `closes #xxx`

## Coding Standards
Đọc `.claude/skills/coding-standards/` trước khi code.
```

---

## 8. Implementation Plan

### Phase 1: Core Structure
1. Tạo thư mục `.claude/` với cấu trúc đầy đủ
2. Viết `CLAUDE.md` project context
3. Viết commands: `dev.md`, `start.md`, `review.md`, `ship.md`

### Phase 2: Skills
4. Viết `dev-workflow/SKILL.md` và 6 phase references
5. Viết `coding-standards/` (BE, FE, DB, Git)
6. Viết `github-workflow/SKILL.md`

### Phase 3: Agents
7. Viết `code-reviewer.md`
8. Viết `test-writer.md`
9. Viết `doc-writer.md`

### Phase 4: Testing
10. Test workflow end-to-end với một task mẫu
11. Điều chỉnh nếu cần

---

## 9. Success Criteria

- [ ] Team members có thể dùng `/dev` để bắt đầu task
- [ ] Claude review code trước khi tạo PR
- [ ] Coding standards được enforce qua review
- [ ] PR tự động link với GitHub Issue
- [ ] Workflow 6 phases hoạt động smooth
