# Claude Dev Workflow Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Create a complete `.claude` development workflow for QuickBite with commands, skills, and agents.

**Architecture:** File-based workflow using Claude Code's native `.claude/` directory structure. Commands trigger skills, skills define processes, agents handle specialized tasks.

**Tech Stack:** Claude Code CLI, Markdown, Bash, Git, GitHub CLI

## Global Constraints

- All files in `.claude/` directory
- Markdown format for all configuration
- Vietnamese language for user-facing messages
- Conventional Commits for git messages
- GitHub Flow branching strategy

---

## File Structure

```
.claude/
├── CLAUDE.md                           # Task 1
├── commands/
│   ├── dev.md                          # Task 2
│   ├── start.md                        # Task 2
│   ├── review.md                       # Task 2
│   └── ship.md                         # Task 2
├── skills/
│   ├── dev-workflow/
│   │   ├── SKILL.md                    # Task 3
│   │   └── references/
│   │       ├── phase-analysis.md       # Task 3
│   │       ├── phase-design.md         # Task 3
│   │       ├── phase-coding.md         # Task 3
│   │       ├── phase-review.md         # Task 3
│   │       ├── phase-test.md           # Task 3
│   │       └── phase-merge.md          # Task 3
│   ├── coding-standards/
│   │   ├── SKILL.md                    # Task 4
│   │   ├── be-standards.md             # Task 4
│   │   ├── fe-standards.md             # Task 4
│   │   ├── db-standards.md             # Task 4
│   │   └── git-standards.md            # Task 4
│   └── github-workflow/
│       └── SKILL.md                    # Task 5
└── agents/
    ├── code-reviewer.md                # Task 6
    ├── test-writer.md                  # Task 6
    └── doc-writer.md                   # Task 6

docs/
└── ba/
    └── _template.md                    # Task 7
```

---

### Task 1: Project Context (CLAUDE.md)

**Files:**
- Create: `.claude/CLAUDE.md`

**Interfaces:**
- Consumes: None (root configuration)
- Produces: Project context loaded by all commands and skills

- [ ] **Step 1: Create .claude directory**

```bash
mkdir -p .claude
```

- [ ] **Step 2: Write CLAUDE.md**

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
- Dùng `/start <issue-url>` để tạo branch mới
- Dùng `/review` để review code trước khi tạo PR
- Dùng `/ship` để tạo PR và merge

## Git Convention
- Branch: `feature/<issue>-<short-name>`, `fix/<issue>-<short-name>`
- Commit: Conventional Commits format
- PR: Link GitHub Issue với `closes #xxx`

## Coding Standards
Đọc `.claude/skills/coding-standards/` trước khi code.
```

- [ ] **Step 3: Commit**

```bash
git add .claude/CLAUDE.md
git commit -m "feat(claude): add project context CLAUDE.md"
```

---

### Task 2: Commands

**Files:**
- Create: `.claude/commands/dev.md`
- Create: `.claude/commands/start.md`
- Create: `.claude/commands/review.md`
- Create: `.claude/commands/ship.md`

**Interfaces:**
- Consumes: CLAUDE.md context
- Produces: `/dev`, `/start`, `/review`, `/ship` slash commands

- [ ] **Step 1: Create commands directory**

```bash
mkdir -p .claude/commands
```

- [ ] **Step 2: Write dev.md command**

```markdown
---
description: Kích hoạt Developer Mode và bắt đầu task từ file BA
argument-hint: "<path-to-task.md>"
---

Từ thời điểm này, bạn làm việc với **vai trò Developer**.

1. Đọc file task BA được cung cấp: $ARGUMENTS
2. Load skill `dev-workflow` và tuân thủ quy trình 6 phases
3. Load skill `coding-standards` phù hợp với task (BE/FE/cả hai)
4. Tạo file `.todo/TODO-<task-name>.md` để track progress
5. Hiển thị checklist 6 phases và xác nhận scope với user

Nếu không có file task, hỏi user cung cấp path hoặc mô tả task.
```

- [ ] **Step 3: Write start.md command**

```markdown
---
description: Tạo branch mới từ GitHub Issue hoặc tên feature
argument-hint: "<github-issue-url hoặc tên-feature>"
---

Tạo branch mới cho task:

1. Nếu có GitHub Issue URL → fetch issue info
2. Tạo branch theo convention:
   - Feature: `feature/<issue>-<short-name>`
   - Bugfix: `fix/<issue>-<short-name>`
3. Checkout và push branch lên origin với `-u`
4. Tạo TODO.md với thông tin task

Input: $ARGUMENTS

Quy trình:
```bash
git fetch origin
git checkout main && git pull origin main
git checkout -b <branch-name>
git push -u origin <branch-name>
```

Thông báo kết quả cho user khi hoàn tất.
```

- [ ] **Step 4: Write review.md command**

```markdown
---
description: Claude review code changes trước khi tạo PR
argument-hint: "[path hoặc để trống = all changes]"
---

Review code với scope: $ARGUMENTS (nếu trống = tất cả changes)

1. Chạy `git diff` để xác định files changed
2. Load `coding-standards` tương ứng (BE/FE)
3. Review theo checklist:
   - Tính đúng đắn: logic, null-safety, edge cases
   - Security: không hardcode secrets, validate input
   - Convention: đúng layering, naming standards
   - Performance: N+1, memory leaks
   - Test coverage: có test cho code mới

Output format:
```
## Tóm tắt
<đánh giá tổng quan>

## 🔴 Blocker (phải sửa)
- file:line — vấn đề → đề xuất

## 🟠 Nên sửa
- ...

## 🟡 Gợi ý
- ...

## ✅ Điểm tốt
- ...
```

Nếu không có blocker, thông báo "Sẵn sàng tạo PR".
```

- [ ] **Step 5: Write ship.md command**

```markdown
---
description: Hoàn thành task - review, tạo PR, và merge
argument-hint: ""
---

Hoàn thành và ship task hiện tại:

1. Chạy `/review` tự động
2. Nếu có 🔴 Blocker → dừng lại, yêu cầu fix
3. Nếu không có blocker:
   - Tạo PR trên GitHub với `gh pr create`
   - PR title từ branch name
   - PR body từ commits + TODO.md
   - Link GitHub Issue nếu có (closes #xxx)
4. Thông báo PR URL cho user
5. Hỏi user có muốn merge không (sau khi team review)

```bash
# Tạo PR
gh pr create --title "<title>" --body "<body>"

# Sau khi approved, merge
gh pr merge --squash --delete-branch
```
```

- [ ] **Step 6: Commit**

```bash
git add .claude/commands/
git commit -m "feat(claude): add slash commands (dev, start, review, ship)"
```

---

### Task 3: Dev Workflow Skill

**Files:**
- Create: `.claude/skills/dev-workflow/SKILL.md`
- Create: `.claude/skills/dev-workflow/references/phase-analysis.md`
- Create: `.claude/skills/dev-workflow/references/phase-design.md`
- Create: `.claude/skills/dev-workflow/references/phase-coding.md`
- Create: `.claude/skills/dev-workflow/references/phase-review.md`
- Create: `.claude/skills/dev-workflow/references/phase-test.md`
- Create: `.claude/skills/dev-workflow/references/phase-merge.md`

**Interfaces:**
- Consumes: Task file từ `docs/ba/`, coding-standards skill
- Produces: 6-phase workflow process

- [ ] **Step 1: Create directories**

```bash
mkdir -p .claude/skills/dev-workflow/references
```

- [ ] **Step 2: Write SKILL.md**

```markdown
---
name: dev-workflow
description: >
  Quy trình phát triển 6 phases cho QuickBite: Phân tích → Thiết kế → Coding → Review → Test → Merge.
  Trigger: /dev command, "bắt đầu task", "làm feature", "implement"
---

# Dev Workflow - 6 Phases

Quy trình phát triển chuẩn cho team QuickBite.

## Phases Overview

| Phase | Tên | Output | Checkpoint |
|-------|-----|--------|------------|
| 1 | Phân tích | Scope + files cần thay đổi | User confirm |
| 2 | Thiết kế | Technical design | User approve |
| 3 | Coding | Code hoàn chỉnh | Self-check |
| 4 | Review | Review report | Fix blockers |
| 5 | Test | Tests pass | Coverage OK |
| 6 | Merge | PR merged | Team approve |

## Quy trình

### Bắt đầu task
1. Đọc file task từ `docs/ba/`
2. Tạo file `.todo/TODO-<task-name>.md`
3. Xác định services liên quan (BE/FE)
4. Load `coding-standards` phù hợp

### Thực hiện từng phase
Đọc chi tiết từng phase trong `references/`:
- `phase-analysis.md` - Phase 1
- `phase-design.md` - Phase 2
- `phase-coding.md` - Phase 3
- `phase-review.md` - Phase 4
- `phase-test.md` - Phase 5
- `phase-merge.md` - Phase 6

### Checkpoint rules
- **Phase 1 & 2:** PHẢI có user confirm trước khi tiếp tục
- **Phase 3:** Commit thường xuyên, conventional commits
- **Phase 4:** Fix tất cả 🔴 Blocker trước khi sang Phase 5
- **Phase 5:** Tests PHẢI pass 100%
- **Phase 6:** Team review trước khi merge

## TODO.md Template

```markdown
# TODO: <Task Name>

## Task Info
- **Source:** docs/ba/<path>
- **Branch:** feature/<name>
- **Services:** [user-service, web-app, ...]
- **Started:** YYYY-MM-DD

## Phase Progress
- [ ] Phase 1: Phân tích
- [ ] Phase 2: Thiết kế
- [ ] Phase 3: Coding
- [ ] Phase 4: Review
- [ ] Phase 5: Test
- [ ] Phase 6: Merge

## Scope
<từ Phase 1>

## Design
<từ Phase 2>

## Notes
<ghi chú trong quá trình làm>
```
```

- [ ] **Step 3: Write phase-analysis.md**

```markdown
# Phase 1: Phân tích (Analysis)

## Input
- File task từ `docs/ba/`
- Hoặc mô tả task từ user

## Output
- Hiểu rõ scope và acceptance criteria
- Danh sách files cần thay đổi
- Xác định services liên quan

## Actions

### 1. Đọc và hiểu task
- Đọc file task description
- Xác định acceptance criteria
- Liệt kê các yêu cầu chính

### 2. Phân tích impact
- Service nào bị ảnh hưởng? (BE/FE/cả hai)
- Files nào cần thay đổi?
- Có cần thay đổi database schema không?
- Có cần thêm API endpoints không?

### 3. Ghi vào TODO.md
```markdown
## Scope
- **Mô tả:** <tóm tắt task>
- **Services:** <danh sách services>
- **Files cần thay đổi:**
  - `path/to/file1.java` - <lý do>
  - `path/to/file2.ts` - <lý do>
- **Database changes:** <có/không>
- **New APIs:** <danh sách endpoints>
```

## Checkpoint
**DỪNG LẠI** và xác nhận với user:
- Scope có đúng không?
- Có thiếu gì không?
- Có câu hỏi nào cần làm rõ?

Chỉ tiếp tục Phase 2 sau khi user confirm.
```

- [ ] **Step 4: Write phase-design.md**

```markdown
# Phase 2: Thiết kế (Design)

## Input
- Scope từ Phase 1 (đã được user confirm)

## Output
- Technical design ngắn gọn
- API contracts (nếu có)
- Data model changes (nếu có)

## Actions

### 1. API Design (nếu có endpoint mới)
```markdown
### POST /api/xxx
**Request:**
```json
{
  "field1": "string",
  "field2": 123
}
```

**Response:**
```json
{
  "id": "uuid",
  "status": "CREATED"
}
```
```

### 2. Data Model (nếu có thay đổi DB)
```markdown
### Table: xxx
| Column | Type | Description |
|--------|------|-------------|
| id | BIGSERIAL | Primary key |
| name | VARCHAR(200) | ... |
```

### 3. Component Structure (FE)
```markdown
### Components
- `XxxComponent` - Smart component, handles logic
- `XxxItemComponent` - Dumb component, displays item
```

### 4. Sequence Diagram (nếu phức tạp)
```
User → Frontend → API Gateway → Service → Database
```

### 5. Ghi vào TODO.md
```markdown
## Design
<paste design ở trên>
```

## Checkpoint
**DỪNG LẠI** và xin user approve design:
- Design có hợp lý không?
- Có cần điều chỉnh gì không?

Chỉ tiếp tục Phase 3 sau khi user approve.
```

- [ ] **Step 5: Write phase-coding.md**

```markdown
# Phase 3: Coding

## Input
- Design đã được user approve

## Output
- Code hoàn chỉnh theo design
- Commits với conventional commits format

## Actions

### 1. Setup
- Checkout đúng branch
- Pull latest từ main (nếu cần)
- Mở files cần thay đổi

### 2. Coding Rules
- **Tuân theo coding-standards** - đọc BE/FE standards trước khi code
- **Commit thường xuyên** - mỗi logical unit là 1 commit
- **Conventional commits:**
  - `feat(scope): description` - tính năng mới
  - `fix(scope): description` - sửa bug
  - `refactor(scope): description` - refactor
  - `test(scope): description` - thêm test

### 3. Backend Checklist
- [ ] Controller đúng pattern
- [ ] Service có interface
- [ ] DTO cho request/response
- [ ] Exception handling
- [ ] Logging với SLF4J
- [ ] Không hardcode values

### 4. Frontend Checklist
- [ ] Standalone component
- [ ] Signals cho state
- [ ] takeUntilDestroyed cho subscriptions
- [ ] Error handling
- [ ] Loading states

### 5. Update TODO.md progress
Đánh dấu Phase 3 đang thực hiện, ghi notes nếu có vấn đề.

## Self-check trước Phase 4
- [ ] Code compile/build thành công
- [ ] Không có linting errors
- [ ] Đã commit tất cả changes
```

- [ ] **Step 6: Write phase-review.md**

```markdown
# Phase 4: Review

## Input
- Code changes từ Phase 3

## Output
- Review report với severity levels
- Fixes cho tất cả blockers

## Actions

### 1. Chạy review
Dùng `/review` command hoặc gọi agent `code-reviewer`.

### 2. Review Checklist
- **Tính đúng đắn:** logic, null-safety, edge cases, exception handling
- **Security:** không hardcode secrets, validate input, prevent injection
- **Convention:** đúng layering, naming, coding standards
- **Performance:** N+1 queries, memory leaks, unnecessary loops
- **Test coverage:** có test cho code mới không

### 3. Severity Levels
- 🔴 **Blocker** - PHẢI fix trước khi merge
- 🟠 **Nên sửa** - Nên fix, có thể để technical debt
- 🟡 **Gợi ý** - Nice to have
- ✅ **Điểm tốt** - Những gì làm tốt

### 4. Fix Issues
- Fix tất cả 🔴 Blocker
- Cân nhắc fix 🟠 Nên sửa
- Commit fixes: `fix(scope): address review feedback`

## Gate
**KHÔNG được sang Phase 5 nếu còn 🔴 Blocker.**
```

- [ ] **Step 7: Write phase-test.md**

```markdown
# Phase 5: Test

## Input
- Code đã qua review (không còn blockers)

## Output
- Unit tests pass
- Integration tests pass (nếu có)
- Coverage không giảm

## Actions

### 1. Backend Tests
```bash
# Chạy tests cho service cụ thể
cd services/<service-name>
mvn test

# Chạy với coverage
mvn test jacoco:report
```

### 2. Frontend Tests
```bash
# Chạy tests
cd web-app
ng test --watch=false --browsers=ChromeHeadless

# Chạy với coverage
ng test --code-coverage --watch=false
```

### 3. Test Checklist
- [ ] Happy path tests
- [ ] Edge cases tests
- [ ] Error handling tests
- [ ] Null/empty input tests

### 4. Viết tests nếu thiếu
Dùng agent `test-writer` nếu cần generate tests.

### 5. Verify Coverage
- Coverage không được giảm so với trước
- New code phải có coverage >= 80%

## Gate
**KHÔNG được sang Phase 6 nếu tests fail.**
```

- [ ] **Step 8: Write phase-merge.md**

```markdown
# Phase 6: Merge

## Input
- Code đã test pass
- Branch sẵn sàng để merge

## Output
- PR created và merged
- Branch deleted
- Task completed

## Actions

### 1. Tạo PR
```bash
# Tạo PR với GitHub CLI
gh pr create \
  --title "feat(scope): description" \
  --body "## Summary
- Change 1
- Change 2

## Testing
- [ ] Unit tests pass
- [ ] Manual testing done

Closes #<issue-number>"
```

### 2. PR Description Template
```markdown
## Summary
<Mô tả ngắn gọn changes>

## Changes
- <Change 1>
- <Change 2>

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing done

## Screenshots (nếu có UI changes)
<screenshots>

## Related Issues
Closes #xxx
```

### 3. Request Review
- Tag team members để review
- Trả lời comments nếu có
- Fix requested changes

### 4. Merge
```bash
# Sau khi approved
gh pr merge --squash --delete-branch
```

### 5. Cleanup
- Delete local branch: `git branch -d <branch>`
- Update TODO.md: đánh dấu hoàn thành
- Move TODO file to `.todo/done/` (optional)

## Done!
Task hoàn thành. Thông báo cho user và hỏi có task tiếp theo không.
```

- [ ] **Step 9: Commit**

```bash
git add .claude/skills/dev-workflow/
git commit -m "feat(claude): add dev-workflow skill with 6 phases"
```

---

### Task 4: Coding Standards Skill

**Files:**
- Create: `.claude/skills/coding-standards/SKILL.md`
- Create: `.claude/skills/coding-standards/be-standards.md`
- Create: `.claude/skills/coding-standards/fe-standards.md`
- Create: `.claude/skills/coding-standards/db-standards.md`
- Create: `.claude/skills/coding-standards/git-standards.md`

**Interfaces:**
- Consumes: None
- Produces: Coding conventions for BE, FE, DB, Git

- [ ] **Step 1: Create directory**

```bash
mkdir -p .claude/skills/coding-standards
```

- [ ] **Step 2: Write SKILL.md (index)**

```markdown
---
name: coding-standards
description: >
  Quy chuẩn code cho QuickBite (BE: Java 21/Spring Boot 3.3; FE: Angular 19; DB: PostgreSQL; Git: Conventional Commits).
  Trigger: "viết code", "tạo API", "tạo component", "code theo chuẩn", "review code", "commit"
---

# Coding Standards - Index

Đọc đúng file trước khi code:

| Domain | File | Dùng khi |
|--------|------|----------|
| **Backend** | `be-standards.md` | Code Java/Spring Boot |
| **Frontend** | `fe-standards.md` | Code Angular/TypeScript |
| **Database** | `db-standards.md` | Schema, migrations, queries |
| **Git** | `git-standards.md` | Commits, branches, PRs |

## Quick Reference

### BE - Controller
```java
@RestController
@RequestMapping("/api/xxx")
@RequiredArgsConstructor
public class XxxController {
    private final XxxService service;
    
    @GetMapping("/{id}")
    public ResponseEntity<XxxResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
```

### FE - Component
```typescript
@Component({
  selector: 'app-xxx',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './xxx.component.html'
})
export class XxxComponent {
  private service = inject(XxxService);
  data = signal<Xxx | null>(null);
  loading = signal(false);
}
```

### Git Commit
```
feat(user-service): add login endpoint
fix(order-service): handle null cart
docs(api): update authentication flow
```
```

- [ ] **Step 3: Write be-standards.md**

```markdown
# Backend Standards - Java 21 / Spring Boot 3.3

## 1. Project Structure

```
services/<service-name>/src/main/java/com/quickbite/<service>/
├── controller/          # REST controllers
├── service/             # Business logic (interface + impl)
├── repository/          # JPA repositories
├── entity/              # JPA entities
├── dto/                 # Request/Response DTOs
├── mapper/              # MapStruct mappers
├── exception/           # Custom exceptions
├── config/              # Configuration classes
└── event/               # Kafka/RabbitMQ events
```

## 2. Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Class | PascalCase | `UserService`, `OrderController` |
| Method | camelCase | `findById`, `createOrder` |
| Variable | camelCase | `userId`, `orderItems` |
| Constant | UPPER_SNAKE | `MAX_RETRY_COUNT` |
| Package | lowercase | `com.quickbite.user` |

## 3. Controller Pattern

```java
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        log.info("Getting user by id: {}", id);
        return ResponseEntity.ok(userService.getById(id));
    }
    
    @PostMapping
    @Operation(summary = "Create new user")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        log.info("Creating user with email: {}", request.getEmail());
        UserResponse response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

## 4. Service Pattern

```java
// Interface (bắt buộc)
public interface UserService {
    UserResponse getById(Long id);
    UserResponse create(CreateUserRequest request);
}

// Implementation
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return userMapper.toResponse(user);
    }
    
    @Override
    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        User user = userMapper.toEntity(request);
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
```

## 5. Entity Pattern

```java
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.CUSTOMER;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

## 6. DTO Pattern

```java
// Request DTO
@Data
@Builder
public class CreateUserRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    private String fullName;
}

// Response DTO
@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private LocalDateTime createdAt;
}
```

## 7. Exception Handling

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.of("NOT_FOUND", ex.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .toList();
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of("VALIDATION_ERROR", errors));
    }
}
```

## 8. Logging

```java
// Dùng @Slf4j annotation
@Slf4j
public class UserService {
    
    public void process() {
        log.info("Processing started");           // INFO cho flow chính
        log.debug("Debug details: {}", data);     // DEBUG cho chi tiết
        log.warn("Warning: {}", warning);         // WARN cho cảnh báo
        log.error("Error occurred", exception);   // ERROR cho lỗi
    }
}

// KHÔNG log sensitive data
log.info("User login: {}", email);  // OK
log.info("Password: {}", password); // KHÔNG ĐƯỢC
```

## 9. Kafka Events

```java
// Event class
@Data
@Builder
public class OrderCreatedEvent {
    private String eventId;
    private String orderId;
    private Long customerId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}

// Producer
@Service
@RequiredArgsConstructor
public class OrderEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publishOrderCreated(Order order) {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
            .eventId(UUID.randomUUID().toString())
            .orderId(order.getId())
            .customerId(order.getCustomerId())
            .totalAmount(order.getTotalAmount())
            .createdAt(LocalDateTime.now())
            .build();
        kafkaTemplate.send("quickbite.orders", event);
    }
}
```

## 10. Common Mistakes to Avoid

- ❌ Không dùng `@Autowired` field injection → ✅ Dùng constructor injection
- ❌ Không catch generic `Exception` → ✅ Catch specific exceptions
- ❌ Không return entity trực tiếp → ✅ Return DTO
- ❌ Không hardcode values → ✅ Dùng `@Value` hoặc config
- ❌ Không dùng `@Transactional` ở controller → ✅ Dùng ở service
```

- [ ] **Step 4: Write fe-standards.md**

```markdown
# Frontend Standards - Angular 19

## 1. Project Structure

```
web-app/src/app/
├── core/                    # Singleton services, guards, interceptors
│   ├── services/
│   ├── guards/
│   ├── interceptors/
│   └── models/
├── shared/                  # Shared components, pipes, directives
│   ├── components/
│   ├── pipes/
│   └── directives/
├── features/                # Feature modules (lazy loaded)
│   ├── auth/
│   ├── home/
│   ├── restaurant/
│   └── order/
└── layouts/                 # Layout components
    ├── main-layout/
    └── auth-layout/
```

## 2. Component Pattern (Standalone + Signals)

```typescript
import { Component, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-restaurant-list',
  standalone: true,
  imports: [CommonModule, RestaurantCardComponent],
  templateUrl: './restaurant-list.component.html',
  styleUrl: './restaurant-list.component.scss'
})
export class RestaurantListComponent {
  // Inject services
  private restaurantService = inject(RestaurantService);
  
  // State với signals
  restaurants = signal<Restaurant[]>([]);
  loading = signal(false);
  error = signal<string | null>(null);
  searchTerm = signal('');
  
  // Computed values
  filteredRestaurants = computed(() => {
    const term = this.searchTerm().toLowerCase();
    return this.restaurants().filter(r => 
      r.name.toLowerCase().includes(term)
    );
  });
  
  constructor() {
    this.loadRestaurants();
  }
  
  private loadRestaurants(): void {
    this.loading.set(true);
    this.error.set(null);
    
    this.restaurantService.getAll()
      .pipe(takeUntilDestroyed())
      .subscribe({
        next: (data) => {
          this.restaurants.set(data);
          this.loading.set(false);
        },
        error: (err) => {
          this.error.set('Failed to load restaurants');
          this.loading.set(false);
        }
      });
  }
  
  onSearch(term: string): void {
    this.searchTerm.set(term);
  }
}
```

## 3. Service Pattern

```typescript
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';

@Injectable({ providedIn: 'root' })
export class RestaurantService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.apiUrl}/api/restaurants`;
  
  getAll(): Observable<Restaurant[]> {
    return this.http.get<Restaurant[]>(this.baseUrl);
  }
  
  getById(id: string): Observable<Restaurant> {
    return this.http.get<Restaurant>(`${this.baseUrl}/${id}`);
  }
  
  create(data: CreateRestaurantRequest): Observable<Restaurant> {
    return this.http.post<Restaurant>(this.baseUrl, data);
  }
  
  update(id: string, data: UpdateRestaurantRequest): Observable<Restaurant> {
    return this.http.put<Restaurant>(`${this.baseUrl}/${id}`, data);
  }
  
  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
```

## 4. Smart/Dumb Component Pattern

```typescript
// Smart Component (Container) - handles logic
@Component({
  selector: 'app-order-page',
  standalone: true,
  imports: [OrderListComponent, OrderFilterComponent],
  template: `
    <app-order-filter (filterChange)="onFilterChange($event)" />
    <app-order-list 
      [orders]="filteredOrders()" 
      [loading]="loading()"
      (orderClick)="onOrderClick($event)"
    />
  `
})
export class OrderPageComponent {
  // All logic here
}

// Dumb Component (Presentational) - only displays
@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    @if (loading()) {
      <app-loading />
    } @else {
      @for (order of orders(); track order.id) {
        <app-order-card [order]="order" (click)="orderClick.emit(order)" />
      }
    }
  `
})
export class OrderListComponent {
  orders = input.required<Order[]>();
  loading = input(false);
  orderClick = output<Order>();
}
```

## 5. Forms (Reactive)

```typescript
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  template: `
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <input formControlName="email" />
      @if (form.get('email')?.errors?.['required']) {
        <span class="error">Email is required</span>
      }
      <button type="submit" [disabled]="form.invalid || submitting()">
        Submit
      </button>
    </form>
  `
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  submitting = signal(false);
  
  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });
  
  onSubmit(): void {
    if (this.form.valid) {
      this.submitting.set(true);
      // submit logic
    }
  }
}
```

## 6. HTTP Interceptors

```typescript
// auth.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();
  
  if (token) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }
  
  return next(req);
};

// error.interceptor.ts
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        inject(AuthService).logout();
        inject(Router).navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};

// app.config.ts
export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor, errorInterceptor]))
  ]
};
```

## 7. Routing (Lazy Loading)

```typescript
// app.routes.ts
export const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { 
        path: 'home', 
        loadComponent: () => import('./features/home/home.component')
          .then(m => m.HomeComponent)
      },
      {
        path: 'restaurants',
        loadChildren: () => import('./features/restaurant/restaurant.routes')
          .then(m => m.RESTAURANT_ROUTES)
      }
    ]
  },
  {
    path: 'auth',
    loadChildren: () => import('./features/auth/auth.routes')
      .then(m => m.AUTH_ROUTES)
  }
];
```

## 8. Common Mistakes to Avoid

- ❌ Không subscribe trong subscribe → ✅ Dùng switchMap, mergeMap
- ❌ Không quên unsubscribe → ✅ Dùng takeUntilDestroyed()
- ❌ Không dùng any → ✅ Define proper types
- ❌ Không để logic trong template → ✅ Dùng computed signals
- ❌ Không import whole modules → ✅ Import specific components
```

- [ ] **Step 5: Write db-standards.md**

```markdown
# Database Standards - PostgreSQL

## 1. Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Table | snake_case, plural | `users`, `order_items` |
| Column | snake_case | `created_at`, `user_id` |
| Primary Key | `id` | `id BIGSERIAL PRIMARY KEY` |
| Foreign Key | `<table>_id` | `user_id`, `order_id` |
| Index | `idx_<table>_<columns>` | `idx_users_email` |
| Constraint | `<type>_<table>_<column>` | `uq_users_email` |

## 2. Table Template

```sql
CREATE TABLE table_name (
    id BIGSERIAL PRIMARY KEY,
    -- business columns
    name VARCHAR(200) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    -- audit columns
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT,
    updated_by BIGINT
);

-- Indexes
CREATE INDEX idx_table_name_status ON table_name(status);

-- Comments
COMMENT ON TABLE table_name IS 'Description of table';
COMMENT ON COLUMN table_name.name IS 'Description of column';
```

## 3. Data Types

| Use Case | Type | Example |
|----------|------|---------|
| Primary Key | BIGSERIAL | `id BIGSERIAL PRIMARY KEY` |
| UUID | UUID | `uuid UUID DEFAULT gen_random_uuid()` |
| Short text | VARCHAR(n) | `name VARCHAR(200)` |
| Long text | TEXT | `description TEXT` |
| Integer | INTEGER | `quantity INTEGER` |
| Decimal/Money | DECIMAL(p,s) | `price DECIMAL(10,2)` |
| Boolean | BOOLEAN | `is_active BOOLEAN DEFAULT TRUE` |
| Timestamp | TIMESTAMP | `created_at TIMESTAMP` |
| Date | DATE | `birth_date DATE` |
| JSON | JSONB | `metadata JSONB` |
| Enum | VARCHAR | `status VARCHAR(20)` |

## 4. Foreign Keys

```sql
-- Define foreign key
ALTER TABLE orders
ADD CONSTRAINT fk_orders_customer
FOREIGN KEY (customer_id) REFERENCES users(id);

-- With cascade
ALTER TABLE order_items
ADD CONSTRAINT fk_order_items_order
FOREIGN KEY (order_id) REFERENCES orders(id)
ON DELETE CASCADE;
```

## 5. Indexes

```sql
-- Single column
CREATE INDEX idx_users_email ON users(email);

-- Composite
CREATE INDEX idx_orders_customer_status ON orders(customer_id, status);

-- Partial
CREATE INDEX idx_orders_pending ON orders(created_at) 
WHERE status = 'PENDING';

-- Unique
CREATE UNIQUE INDEX uq_users_email ON users(email);
```

## 6. Flyway Migrations

```
resources/db/migration/
├── V1__create_users_table.sql
├── V2__create_restaurants_table.sql
├── V3__create_orders_table.sql
└── V4__add_index_orders_status.sql
```

**Naming:** `V{version}__{description}.sql`
- Version: sequential number (1, 2, 3...)
- Description: snake_case, what it does
- Double underscore between version and description

```sql
-- V1__create_users_table.sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(200),
    role VARCHAR(20) DEFAULT 'CUSTOMER',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
```

## 7. Query Best Practices

```sql
-- ✅ Select specific columns
SELECT id, name, email FROM users WHERE status = 'ACTIVE';

-- ❌ Avoid SELECT *
SELECT * FROM users;

-- ✅ Use LIMIT for large tables
SELECT * FROM orders ORDER BY created_at DESC LIMIT 100;

-- ✅ Use EXISTS instead of COUNT for checking
SELECT EXISTS(SELECT 1 FROM users WHERE email = 'test@test.com');

-- ✅ Use prepared statements (handled by JPA)
-- ❌ Never concatenate user input into SQL
```

## 8. Common Mistakes to Avoid

- ❌ Không dùng `TEXT` cho tất cả strings → ✅ Dùng `VARCHAR(n)` với limit hợp lý
- ❌ Không thiếu indexes cho FK và frequently queried columns
- ❌ Không dùng `FLOAT` cho tiền → ✅ Dùng `DECIMAL(10,2)`
- ❌ Không để nullable FK không cần thiết
- ❌ Không quên `created_at`, `updated_at` columns
```

- [ ] **Step 6: Write git-standards.md**

```markdown
# Git Standards - Conventional Commits

## 1. Commit Message Format

```
<type>(<scope>): <description>

[optional body]

[optional footer(s)]
```

### Type (bắt buộc)
| Type | Mô tả |
|------|-------|
| `feat` | Tính năng mới |
| `fix` | Sửa bug |
| `docs` | Chỉ thay đổi documentation |
| `style` | Formatting, không thay đổi logic |
| `refactor` | Refactoring code |
| `test` | Thêm/sửa tests |
| `chore` | Maintenance, build, CI |
| `perf` | Performance improvements |

### Scope (khuyến khích)
| Scope | Mô tả |
|-------|-------|
| `user-service` | User service |
| `restaurant-service` | Restaurant service |
| `order-service` | Order service |
| `payment-service` | Payment service |
| `delivery-service` | Delivery service |
| `notification-service` | Notification service |
| `gateway` | API Gateway |
| `web-app` | Angular frontend |
| `common` | Shared library |
| `infra` | Infrastructure (docker, CI) |
| `api` | API documentation |

### Description
- Viết bằng tiếng Anh
- Lowercase, không có dấu chấm cuối
- Imperative mood: "add" không phải "added" hay "adds"
- Tối đa 72 ký tự

## 2. Examples

```bash
# Feature
feat(user-service): add JWT refresh endpoint
feat(web-app): implement restaurant search

# Fix
fix(order-service): handle null cart items
fix(gateway): correct rate limiting config

# Docs
docs(api): update authentication flow
docs(readme): add setup instructions

# Refactor
refactor(common): extract shared DTOs to common module
refactor(user-service): simplify password validation

# Test
test(order-service): add unit tests for OrderService
test(web-app): add e2e tests for checkout flow

# Chore
chore(deps): upgrade Spring Boot to 3.3.1
chore(docker): optimize Dockerfile for caching
```

## 3. Breaking Changes

```bash
feat(api)!: change response format for all endpoints

BREAKING CHANGE: Response wrapper changed from 
{ data: ... } to { result: ..., meta: ... }
```

## 4. Branch Naming

```
<type>/<issue-number>-<short-description>
```

| Type | Dùng khi |
|------|----------|
| `feature/` | Tính năng mới |
| `fix/` | Sửa bug |
| `hotfix/` | Fix urgent trên production |
| `refactor/` | Refactoring |
| `docs/` | Documentation |

**Examples:**
```
feature/123-user-registration
fix/456-cart-null-pointer
hotfix/789-payment-timeout
refactor/101-extract-common-dto
```

## 5. PR Title

Giống commit message format:
```
feat(user-service): add user registration flow
```

## 6. Git Workflow (GitHub Flow)

```bash
# 1. Update main
git checkout main
git pull origin main

# 2. Create feature branch
git checkout -b feature/123-new-feature

# 3. Work and commit
git add .
git commit -m "feat(scope): description"

# 4. Push
git push -u origin feature/123-new-feature

# 5. Create PR
gh pr create --title "feat(scope): description" --body "..."

# 6. After review, merge
gh pr merge --squash --delete-branch

# 7. Cleanup local
git checkout main
git pull origin main
git branch -d feature/123-new-feature
```

## 7. Common Mistakes to Avoid

- ❌ `git commit -m "fix"` → ✅ Mô tả rõ fix gì
- ❌ `git commit -m "WIP"` → ✅ Squash trước khi PR
- ❌ Commit quá lớn → ✅ Chia nhỏ thành logical units
- ❌ Force push to main → ✅ Chỉ force push branch của mình
- ❌ Merge không qua PR → ✅ Luôn tạo PR để review
```

- [ ] **Step 7: Commit**

```bash
git add .claude/skills/coding-standards/
git commit -m "feat(claude): add coding-standards skill (BE, FE, DB, Git)"
```

---

### Task 5: GitHub Workflow Skill

**Files:**
- Create: `.claude/skills/github-workflow/SKILL.md`

**Interfaces:**
- Consumes: Git standards, GitHub CLI
- Produces: GitHub Issues + PR automation

- [ ] **Step 1: Create directory**

```bash
mkdir -p .claude/skills/github-workflow
```

- [ ] **Step 2: Write SKILL.md**

```markdown
---
name: github-workflow
description: >
  Quy trình làm việc với GitHub Issues và Pull Requests cho QuickBite.
  Trigger: "tạo issue", "tạo PR", "link issue", "close issue"
---

# GitHub Workflow

Quy trình làm việc với GitHub cho team QuickBite.

## Prerequisites

```bash
# Cài đặt GitHub CLI
# Windows: winget install GitHub.cli
# Mac: brew install gh

# Login
gh auth login
```

## 1. GitHub Issues

### Tạo Issue
```bash
gh issue create \
  --title "feat: User registration" \
  --body "## Description
Implement user registration flow.

## Acceptance Criteria
- [ ] POST /api/auth/register works
- [ ] Email validation
- [ ] Password hashing

## Technical Notes
- Use BCrypt for password
- Send welcome email"
```

### List Issues
```bash
# All open issues
gh issue list

# Filter by label
gh issue list --label "bug"
gh issue list --label "feature"

# Assigned to me
gh issue list --assignee @me
```

### View Issue
```bash
gh issue view 123
gh issue view 123 --web  # Open in browser
```

## 2. Branches

### Create Branch from Issue
```bash
# Convention: feature/<issue>-<short-name>
git checkout main
git pull origin main
git checkout -b feature/123-user-registration
git push -u origin feature/123-user-registration
```

### Link Branch to Issue
Branch sẽ tự động link khi tên chứa issue number.

## 3. Pull Requests

### Create PR
```bash
gh pr create \
  --title "feat(user-service): add user registration" \
  --body "## Summary
Implement user registration endpoint.

## Changes
- Add UserController with register endpoint
- Add UserService with validation
- Add BCrypt password hashing

## Testing
- [ ] Unit tests pass
- [ ] Manual testing done

## Screenshots
N/A

Closes #123"
```

### PR Template

Tạo file `.github/PULL_REQUEST_TEMPLATE.md`:
```markdown
## Summary
<!-- Mô tả ngắn gọn changes -->

## Changes
<!-- Liệt kê changes -->
- 

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing done

## Screenshots (nếu có UI changes)
<!-- Screenshots -->

## Related Issues
<!-- Closes #xxx -->
```

### View PR
```bash
gh pr view
gh pr view 456 --web
```

### Review PR
```bash
# List PRs cần review
gh pr list --search "review-requested:@me"

# Checkout PR locally để test
gh pr checkout 456

# Approve
gh pr review --approve

# Request changes
gh pr review --request-changes --body "Please fix..."
```

### Merge PR
```bash
# Squash merge (recommended)
gh pr merge --squash --delete-branch

# Merge commit
gh pr merge --merge --delete-branch

# Rebase
gh pr merge --rebase --delete-branch
```

## 4. Labels

Recommended labels:
| Label | Color | Description |
|-------|-------|-------------|
| `bug` | #d73a4a | Something isn't working |
| `feature` | #0075ca | New feature request |
| `enhancement` | #a2eeef | Improvement to existing feature |
| `documentation` | #0075ca | Documentation updates |
| `backend` | #5319e7 | Backend related |
| `frontend` | #1d76db | Frontend related |
| `priority:high` | #b60205 | High priority |
| `priority:low` | #c5def5 | Low priority |

## 5. Automation với GitHub Actions

```yaml
# .github/workflows/pr-checks.yml
name: PR Checks

on:
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean verify
```

## 6. Quick Reference

```bash
# Start new task
gh issue view 123                    # Xem issue
git checkout -b feature/123-desc     # Tạo branch
# ... code ...
gh pr create                         # Tạo PR
gh pr merge --squash                 # Merge

# Common operations
gh issue list                        # List issues
gh pr list                           # List PRs
gh pr status                         # PR status của repo
gh pr checks                         # CI checks của PR hiện tại
```
```

- [ ] **Step 3: Commit**

```bash
git add .claude/skills/github-workflow/
git commit -m "feat(claude): add github-workflow skill"
```

---

### Task 6: Agents

**Files:**
- Create: `.claude/agents/code-reviewer.md`
- Create: `.claude/agents/test-writer.md`
- Create: `.claude/agents/doc-writer.md`

**Interfaces:**
- Consumes: Coding standards, source code
- Produces: Review reports, tests, documentation

- [ ] **Step 1: Create directory**

```bash
mkdir -p .claude/agents
```

- [ ] **Step 2: Write code-reviewer.md**

```markdown
---
name: code-reviewer
description: >
  Review code changes cho QuickBite về bugs, security, convention.
  Trigger: /review command, "review code", "kiểm tra code"
tools: Read, Grep, Glob, Bash
---

# Code Reviewer - QuickBite

Review code đã thay đổi về bugs, security, và coding standards.

## Quy trình

1. Xác định scope: `git diff --name-only` hoặc path được chỉ định
2. Phân loại files: Backend (.java) / Frontend (.ts, .html, .scss)
3. Đọc coding standards tương ứng
4. Review từng file theo checklist
5. Xuất báo cáo theo format chuẩn

## Checklist - Backend (Java/Spring)

### Tính đúng đắn
- [ ] Logic đúng với requirements
- [ ] Null-safety: Optional, null checks
- [ ] Edge cases được handle
- [ ] Exception handling phù hợp

### Security
- [ ] Không hardcode secrets, passwords, API keys
- [ ] Input validation (DTOs với @Valid)
- [ ] SQL injection prevention (parameterized queries)
- [ ] Authentication/Authorization đúng

### Convention
- [ ] Đúng layered architecture (Controller → Service → Repository)
- [ ] Service có interface
- [ ] DTO cho request/response (không expose Entity)
- [ ] Naming conventions đúng
- [ ] Logging có nhưng không log sensitive data

### Performance
- [ ] Không N+1 queries
- [ ] Pagination cho list APIs
- [ ] Không unnecessary loops
- [ ] Transaction scope hợp lý

## Checklist - Frontend (Angular)

### Tính đúng đắn
- [ ] Logic đúng với requirements
- [ ] Error handling cho HTTP calls
- [ ] Loading states
- [ ] Empty states

### Convention
- [ ] Standalone components
- [ ] Signals cho state management
- [ ] takeUntilDestroyed cho subscriptions
- [ ] Smart/Dumb component pattern
- [ ] Proper typing (không any)

### Performance
- [ ] OnPush change detection (khi cần)
- [ ] Lazy loading cho routes
- [ ] trackBy cho *ngFor / @for

## Output Format

```markdown
## Tóm tắt
<1-3 câu đánh giá tổng quan>

## 🔴 Blocker (phải sửa trước khi merge)
- `file:line` — <vấn đề> → <đề xuất sửa>

## 🟠 Nên sửa
- `file:line` — <vấn đề> → <đề xuất sửa>

## 🟡 Gợi ý
- `file:line` — <suggestion>

## ✅ Điểm tốt
- <những gì làm tốt>
```

## Rules

- **Chỉ đọc, không tự sửa code** trừ khi được yêu cầu
- **Không bịa file/line** - chỉ report những gì thực sự thấy
- **Ưu tiên theo severity** - Blocker > Nên sửa > Gợi ý
- **Actionable feedback** - mỗi issue phải có đề xuất cụ thể
```

- [ ] **Step 3: Write test-writer.md**

```markdown
---
name: test-writer
description: >
  Generate unit tests và integration tests cho QuickBite.
  Trigger: "viết test", "tạo test", "generate test"
tools: Read, Write, Edit, Bash
---

# Test Writer - QuickBite

Generate tests cho code mới hoặc code chưa có tests.

## Backend Tests (JUnit 5 + Mockito)

### Unit Test Template - Service

```java
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    @DisplayName("getById - should return user when exists")
    void getById_WhenUserExists_ShouldReturnUser() {
        // Given
        Long userId = 1L;
        User user = User.builder().id(userId).email("test@test.com").build();
        UserResponse expected = UserResponse.builder().id(userId).email("test@test.com").build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(expected);
        
        // When
        UserResponse result = userService.getById(userId);
        
        // Then
        assertThat(result).isEqualTo(expected);
        verify(userRepository).findById(userId);
    }
    
    @Test
    @DisplayName("getById - should throw when user not found")
    void getById_WhenUserNotFound_ShouldThrow() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // When/Then
        assertThatThrownBy(() -> userService.getById(userId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("User");
    }
}
```

### Integration Test Template - Controller

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }
    
    @Test
    @DisplayName("POST /api/users - should create user")
    void createUser_ShouldReturnCreated() throws Exception {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .email("test@test.com")
            .password("password123")
            .build();
        
        // When/Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value("test@test.com"));
    }
}
```

## Frontend Tests (Jasmine)

### Component Test Template

```typescript
describe('RestaurantListComponent', () => {
  let component: RestaurantListComponent;
  let fixture: ComponentFixture<RestaurantListComponent>;
  let restaurantService: jasmine.SpyObj<RestaurantService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('RestaurantService', ['getAll']);
    
    await TestBed.configureTestingModule({
      imports: [RestaurantListComponent],
      providers: [
        { provide: RestaurantService, useValue: spy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RestaurantListComponent);
    component = fixture.componentInstance;
    restaurantService = TestBed.inject(RestaurantService) as jasmine.SpyObj<RestaurantService>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load restaurants on init', () => {
    const mockRestaurants = [{ id: '1', name: 'Restaurant 1' }];
    restaurantService.getAll.and.returnValue(of(mockRestaurants));
    
    fixture.detectChanges();
    
    expect(component.restaurants()).toEqual(mockRestaurants);
    expect(component.loading()).toBeFalse();
  });

  it('should handle error when loading fails', () => {
    restaurantService.getAll.and.returnValue(throwError(() => new Error('Failed')));
    
    fixture.detectChanges();
    
    expect(component.error()).toBeTruthy();
    expect(component.loading()).toBeFalse();
  });
});
```

### Service Test Template

```typescript
describe('RestaurantService', () => {
  let service: RestaurantService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RestaurantService]
    });
    
    service = TestBed.inject(RestaurantService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch all restaurants', () => {
    const mockData = [{ id: '1', name: 'Test' }];
    
    service.getAll().subscribe(data => {
      expect(data).toEqual(mockData);
    });
    
    const req = httpMock.expectOne(`${environment.apiUrl}/api/restaurants`);
    expect(req.request.method).toBe('GET');
    req.flush(mockData);
  });
});
```

## Test Coverage Guidelines

- **Happy path:** Luôn có
- **Edge cases:** null, empty, boundary values
- **Error cases:** Exceptions, error responses
- **Target:** >= 80% coverage cho code mới

## Commands

```bash
# Backend
mvn test                           # Run all tests
mvn test -Dtest=UserServiceTest    # Run specific test
mvn test jacoco:report             # With coverage

# Frontend
ng test --watch=false              # Run all tests
ng test --include=**/user/*        # Run specific tests
ng test --code-coverage            # With coverage
```
```

- [ ] **Step 4: Write doc-writer.md**

```markdown
---
name: doc-writer
description: >
  Generate và update documentation cho QuickBite.
  Trigger: "viết docs", "update docs", "tạo API docs"
tools: Read, Write, Edit
---

# Doc Writer - QuickBite

Generate và update documentation.

## 1. API Documentation

### Endpoint Documentation Template

```markdown
## POST /api/users

Create a new user account.

### Request

**Headers:**
| Header | Required | Description |
|--------|----------|-------------|
| Content-Type | Yes | application/json |

**Body:**
```json
{
  "email": "string (required, valid email)",
  "password": "string (required, min 8 chars)",
  "fullName": "string (optional)"
}
```

### Response

**Success (201 Created):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "fullName": "John Doe",
  "role": "CUSTOMER",
  "createdAt": "2024-01-01T00:00:00Z"
}
```

**Errors:**
| Status | Code | Description |
|--------|------|-------------|
| 400 | VALIDATION_ERROR | Invalid input |
| 409 | DUPLICATE_EMAIL | Email already exists |

### Example

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"password123"}'
```
```

## 2. README Updates

Khi có thay đổi lớn, update README.md:
- New service added
- New endpoint groups
- Configuration changes
- Setup steps changed

## 3. ARCHITECTURE.md Updates

Update khi:
- New service added
- Database schema changes
- Event flows changed
- Infrastructure changes

## 4. Changelog

Format: Keep a Changelog (https://keepachangelog.com)

```markdown
# Changelog

## [Unreleased]

### Added
- User registration endpoint (#123)
- JWT refresh token (#124)

### Changed
- Updated password validation rules (#125)

### Fixed
- Cart null pointer exception (#126)

## [1.0.0] - 2024-01-01

### Added
- Initial release
- User service
- Restaurant service
- Order service
```

## 5. Quick Commands

```bash
# Generate API docs từ code (nếu dùng Swagger)
# Swagger UI available at: http://localhost:8080/swagger-ui.html

# Generate từ OpenAPI spec
# openapi-generator generate -i openapi.yaml -g markdown -o docs/api
```
```

- [ ] **Step 5: Commit**

```bash
git add .claude/agents/
git commit -m "feat(claude): add agents (code-reviewer, test-writer, doc-writer)"
```

---

### Task 7: BA Task Template

**Files:**
- Create: `docs/ba/_template.md`
- Create: `docs/ba/.gitkeep`

**Interfaces:**
- Consumes: None
- Produces: Template cho task files

- [ ] **Step 1: Create directory**

```bash
mkdir -p docs/ba
```

- [ ] **Step 2: Write _template.md**

```markdown
# TASK-XXX: [Task Name]

## Mô tả
<!-- Mô tả ngắn gọn task cần làm -->

## Acceptance Criteria
<!-- Điều kiện để task được coi là hoàn thành -->
- [ ] Criterion 1
- [ ] Criterion 2
- [ ] Criterion 3

## Technical Notes
<!-- Ghi chú kỹ thuật, constraints, dependencies -->
- Note 1
- Note 2

## UI/UX (nếu có)
<!-- Link Figma, wireframe, hoặc mô tả UI -->

## API Changes (nếu có)
<!-- Mô tả API mới hoặc thay đổi -->

### Endpoint: [METHOD] /api/xxx
**Request:**
```json
{
  "field": "value"
}
```

**Response:**
```json
{
  "field": "value"
}
```

## Database Changes (nếu có)
<!-- Mô tả schema changes -->

## Related Issues
<!-- Link GitHub Issues nếu có -->
- #xxx

## Notes
<!-- Ghi chú thêm -->
```

- [ ] **Step 3: Create sample task**

```markdown
# TASK-001: User Registration

## Mô tả
Implement user registration endpoint cho phép khách hàng mới đăng ký tài khoản.

## Acceptance Criteria
- [ ] POST /api/auth/register hoạt động
- [ ] Validate email format và uniqueness
- [ ] Hash password với BCrypt
- [ ] Return JWT token sau khi đăng ký thành công
- [ ] Gửi welcome email (optional, có thể làm sau)

## Technical Notes
- Service: user-service
- Dùng BCryptPasswordEncoder
- JWT token có expiry 24h
- Refresh token có expiry 7 ngày

## API Changes

### Endpoint: POST /api/auth/register
**Request:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe"
}
```

**Response (201 Created):**
```json
{
  "accessToken": "eyJ...",
  "refreshToken": "eyJ...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "fullName": "John Doe",
    "role": "CUSTOMER"
  }
}
```

**Errors:**
- 400: Validation error (invalid email, weak password)
- 409: Email already exists

## Database Changes
Sử dụng existing `users` table, không cần schema change.

## Related Issues
- #1

## Notes
- Password requirements: min 8 chars
- Email sẽ được lowercase trước khi lưu
```

- [ ] **Step 4: Commit**

```bash
git add docs/ba/
git commit -m "feat(docs): add BA task template and sample task"
```

---

### Task 8: Final Integration Test

**Files:**
- None (testing only)

**Interfaces:**
- Consumes: All created files
- Produces: Verification that workflow works

- [ ] **Step 1: Verify directory structure**

```bash
# List all .claude files
find .claude -type f -name "*.md" | sort
```

Expected output:
```
.claude/CLAUDE.md
.claude/agents/code-reviewer.md
.claude/agents/doc-writer.md
.claude/agents/test-writer.md
.claude/commands/dev.md
.claude/commands/review.md
.claude/commands/ship.md
.claude/commands/start.md
.claude/skills/coding-standards/SKILL.md
.claude/skills/coding-standards/be-standards.md
.claude/skills/coding-standards/db-standards.md
.claude/skills/coding-standards/fe-standards.md
.claude/skills/coding-standards/git-standards.md
.claude/skills/dev-workflow/SKILL.md
.claude/skills/dev-workflow/references/phase-analysis.md
.claude/skills/dev-workflow/references/phase-coding.md
.claude/skills/dev-workflow/references/phase-design.md
.claude/skills/dev-workflow/references/phase-merge.md
.claude/skills/dev-workflow/references/phase-review.md
.claude/skills/dev-workflow/references/phase-test.md
.claude/skills/github-workflow/SKILL.md
```

- [ ] **Step 2: Verify docs structure**

```bash
ls -la docs/ba/
```

Expected: `_template.md` and sample task file

- [ ] **Step 3: Final commit**

```bash
git add .
git status
# If any uncommitted files, commit them
git log --oneline -5
```

- [ ] **Step 4: Summary**

Print summary of what was created:
- 1 CLAUDE.md (project context)
- 4 commands (dev, start, review, ship)
- 3 skills (dev-workflow, coding-standards, github-workflow)
- 3 agents (code-reviewer, test-writer, doc-writer)
- Task template + sample

---

## Summary

| Task | Files | Description |
|------|-------|-------------|
| 1 | 1 | CLAUDE.md project context |
| 2 | 4 | Slash commands |
| 3 | 7 | Dev workflow skill with 6 phases |
| 4 | 5 | Coding standards (BE, FE, DB, Git) |
| 5 | 1 | GitHub workflow skill |
| 6 | 3 | Agents (reviewer, test-writer, doc-writer) |
| 7 | 2 | BA task template + sample |
| 8 | 0 | Integration test |

**Total: 23 files**
