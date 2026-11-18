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
