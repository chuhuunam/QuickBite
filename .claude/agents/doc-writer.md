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
