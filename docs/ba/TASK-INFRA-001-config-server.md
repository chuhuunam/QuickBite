# TASK-INFRA-001: Config Server

## 1. Tổng quan
Hệ thống cần một nơi quản lý cấu hình tập trung cho tất cả microservices. Thay vì mỗi service tự quản lý config riêng, Config Server sẽ cung cấp config cho toàn bộ hệ thống.

## 2. User Stories

### US-INFRA-001.1: Centralized Configuration
> **As a** DevOps engineer
> **I want to** quản lý config tập trung một chỗ
> **So that** tôi có thể thay đổi config mà không cần rebuild/restart từng service

**Luồng:**
1. Config được lưu trong thư mục `config-repo/`
2. Config Server đọc và phục vụ config
3. Các service khởi động và lấy config từ Config Server
4. Khi config thay đổi, service có thể refresh mà không cần restart

### US-INFRA-001.2: Environment-specific Config
> **As a** developer
> **I want to** có config riêng cho từng môi trường (dev, staging, prod)
> **So that** ứng dụng hoạt động đúng với từng môi trường

**Luồng:**
1. Config chia theo profile: `application-dev.yml`, `application-prod.yml`
2. Service chỉ định profile khi khởi động
3. Config Server trả về config phù hợp

### US-INFRA-001.3: Sensitive Config Protection
> **As a** security engineer
> **I want to** bảo vệ các config nhạy cảm (passwords, secrets)
> **So that** thông tin không bị lộ

**Luồng:**
1. Sensitive config sử dụng environment variables
2. Hoặc encrypt với Spring Cloud Config encryption
3. Không commit secrets vào git

## 3. Business Rules

| # | Rule | Mô tả |
|---|------|-------|
| BR-01 | Config priority | Environment var > profile config > default config |
| BR-02 | Service naming | Mỗi service có file config riêng: `{service-name}.yml` |
| BR-03 | Shared config | Config chung đặt trong `application.yml` |
| BR-04 | Health check | Config Server phải healthy trước khi services khởi động |

## 4. Technical Scope

### Config Repository Structure
```
config-repo/
├── application.yml           # Shared config (tất cả services)
├── user-service.yml          # User service specific
├── restaurant-service.yml    
├── order-service.yml
├── payment-service.yml
├── delivery-service.yml
└── notification-service.yml
```

### Shared Config (application.yml)
```yaml
# Eureka
eureka:
  client:
    service-url:
      defaultZone: http://discovery-server:8761/eureka

# Actuator
management:
  endpoints:
    web:
      exposure:
        include: health,info,refresh

# Logging
logging:
  level:
    com.quickbite: INFO
```

### Service-specific Config
```yaml
# user-service.yml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/user_db

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000
```

## 5. Acceptance Criteria
- [ ] Config Server chạy trên port 8888
- [ ] Các service lấy được config từ Config Server
- [ ] Config thay đổi có thể refresh mà không restart
- [ ] Sensitive config không commit vào git
- [ ] Health endpoint: /actuator/health

## 6. Liên kết
- **Trước:** Discovery Server (Eureka)
- **Sau:** API Gateway
