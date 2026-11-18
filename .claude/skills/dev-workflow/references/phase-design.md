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
