# TASK-INFRA-002: API Gateway

## 1. Tổng quan
API Gateway là điểm vào duy nhất cho tất cả requests từ client. Gateway sẽ routing requests đến đúng service, xác thực JWT, rate limiting, và logging.

## 2. User Stories

### US-INFRA-002.1: Single Entry Point
> **As a** frontend developer
> **I want to** chỉ cần gọi một địa chỉ duy nhất
> **So that** tôi không cần biết chi tiết từng service

**Luồng:**
1. Client gọi `http://gateway:8080/api/users/me`
2. Gateway route đến `user-service:8081/api/users/me`
3. Response trả về qua Gateway

### US-INFRA-002.2: JWT Authentication
> **As a** security engineer
> **I want to** Gateway xác thực JWT trước khi route
> **So that** các service không cần tự validate JWT

**Luồng:**
1. Client gửi request với `Authorization: Bearer <token>`
2. Gateway validate JWT (signature, expiration)
3. Nếu valid → route đến service
4. Nếu invalid → return 401 Unauthorized

**Luồng thay thế:**
- Public endpoints (register, login) không cần JWT

### US-INFRA-002.3: Rate Limiting
> **As a** DevOps engineer
> **I want to** giới hạn số request từ mỗi client
> **So that** hệ thống không bị quá tải

**Luồng:**
1. Client gửi nhiều request liên tục
2. Gateway đếm số request theo IP/user
3. Vượt giới hạn → return 429 Too Many Requests

### US-INFRA-002.4: Request Logging
> **As a** DevOps engineer
> **I want to** log tất cả requests qua Gateway
> **So that** tôi có thể debug và monitor

**Luồng:**
1. Request đến Gateway
2. Log: timestamp, method, path, client IP, response status, duration
3. Forward đến Zipkin để tracing

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Public endpoints | /api/auth/** không cần JWT |
| BR-02 | Rate limit | 100 requests/minute per IP |
| BR-03 | Timeout | 30 seconds timeout cho downstream services |
| BR-04 | Circuit breaker | Mở circuit sau 5 failures liên tiếp |
| BR-05 | CORS | Allow frontend origins |

## 4. Technical Scope

### Routing Rules
```
/api/auth/**        → user-service (public)
/api/users/**       → user-service (authenticated)
/api/restaurants/** → restaurant-service
/api/orders/**      → order-service
/api/payments/**    → payment-service
/api/deliveries/**  → delivery-service
```

### JWT Filter
```
1. Trích xuất token từ Authorization header
2. Validate signature với public key
3. Check expiration
4. Trích xuất userId, role từ claims
5. Add X-User-Id, X-User-Role headers cho downstream
```

### Rate Limiting
```yaml
# Redis-based rate limiting
rate-limit:
  default: 100 requests/minute
  per-endpoint:
    /api/auth/login: 10 requests/minute (prevent brute force)
    /api/auth/register: 5 requests/minute
```

## 5. Wireframe

### Error Responses
```json
// 401 Unauthorized
{
  "code": "UNAUTHORIZED",
  "message": "Invalid or expired token"
}

// 429 Too Many Requests
{
  "code": "RATE_LIMIT_EXCEEDED",
  "message": "Too many requests. Please try again later."
}

// 503 Service Unavailable
{
  "code": "SERVICE_UNAVAILABLE",
  "message": "Service temporarily unavailable"
}
```

## 6. Acceptance Criteria
- [ ] Gateway chạy trên port 8080
- [ ] Route đúng đến các services
- [ ] Public endpoints không cần JWT
- [ ] Protected endpoints validate JWT
- [ ] Rate limiting hoạt động
- [ ] Request logging đầy đủ
- [ ] Circuit breaker khi service down
- [ ] CORS configured cho frontend

## 7. Liên kết
- **Trước:** Config Server
- **Sau:** Restaurant Service (Phase 3)
