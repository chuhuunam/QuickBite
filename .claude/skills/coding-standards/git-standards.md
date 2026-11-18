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
