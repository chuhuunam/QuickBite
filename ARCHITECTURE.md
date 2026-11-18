# QuickBite - Food Delivery Microservices

> Dự án học tập microservices architecture với đầy đủ patterns cho production-ready system.

---

## 1. Project Overview

**QuickBite** là hệ thống đặt đồ ăn online với kiến trúc microservices:
- Khách hàng đặt món từ nhà hàng
- Nhà hàng xác nhận và chuẩn bị
- Tài xế nhận và giao hàng
- Thanh toán online

**Mục tiêu:**
- Học và thực hành microservices patterns
- Xây dựng portfolio cho CV
- Focus: Infrastructure patterns + MVP services

**Approach:** Mono-repo (tất cả services trong 1 repository)

---

## 2. Tech Stack

| Layer | Technology | Version |
|-------|------------|---------|
| **Language** | Java | 21 |
| **Framework** | Spring Boot | 3.3.x |
| **Cloud** | Spring Cloud | 2024.0.x |
| **API Gateway** | Spring Cloud Gateway | - |
| **Service Discovery** | Netflix Eureka | - |
| **Config Server** | Spring Cloud Config | - |
| **Message Broker** | Apache Kafka | 3.x |
| **Message Queue** | RabbitMQ | 3.x |
| **Database** | PostgreSQL | 16 |
| **Caching** | Redis | 7.x |
| **Tracing** | Zipkin | - |
| **Build Tool** | Maven | 3.9.x |
| **Containerization** | Docker + Docker Compose | - |

---

## 3. System Architecture

```
                                    ┌─────────────────┐
                                    │   Config Repo   │
                                    │   (Git/Local)   │
                                    └────────┬────────┘
                                             │
┌──────────────┐                   ┌─────────▼────────┐
│   Zipkin     │                   │  Config Server   │
│   :9411      │                   │     :8888        │
└──────────────┘                   └─────────┬────────┘
       ▲                                     │
       │                           ┌─────────▼────────┐
       │                           │ Discovery Server │
       │                           │  (Eureka) :8761  │
       │                           └─────────┬────────┘
       │                                     │
       │            ┌────────────────────────┼────────────────────────┐
       │            │                        │                        │
       │   ┌────────▼────────┐    ┌──────────▼──────────┐            │
       │   │   API Gateway   │◄───│      Services       │            │
       │   │     :8080       │    │                     │            │
       │   └────────┬────────┘    │  ┌───────────────┐  │            │
       │            │             │  │ user-service  │  │            │
       │            │             │  │    :8081      │  │            │
       │            │             │  └───────────────┘  │            │
       │            │             │  ┌───────────────┐  │            │
       └────────────┼─────────────┼──│restaurant-svc │  │            │
                    │             │  │    :8082      │  │            │
                    │             │  └───────────────┘  │            │
                    │             │  ┌───────────────┐  │            │
                    │             │  │ order-service │  │            │
                    │             │  │    :8083      │  │            │
                    │             │  └───────────────┘  │            │
                    │             │  ┌───────────────┐  │            │
                    │             │  │payment-service│  │            │
                    │             │  │    :8084      │  │            │
                    │             │  └───────────────┘  │            │
                    │             │  ┌───────────────┐  │            │
                    │             │  │delivery-service│ │            │
                    │             │  │    :8085      │  │            │
                    │             │  └───────────────┘  │            │
                    │             │  ┌───────────────┐  │            │
                    │             │  │notification-svc│ │            │
                    │             │  │    :8086      │  │            │
                    │             │  └───────────────┘  │            │
                    │             └──────────┬──────────┘            │
                    │                        │                        │
                    │             ┌──────────▼──────────┐            │
                    │             │   Message Brokers   │            │
                    │             │                     │            │
                    │             │  ┌──────┐ ┌──────┐  │            │
                    │             │  │Kafka │ │Rabbit│  │            │
                    │             │  │:9092 │ │:5672 │  │            │
                    │             │  └──────┘ └──────┘  │            │
                    │             └─────────────────────┘            │
                    │                                                 │
                    │             ┌─────────────────────┐            │
                    │             │      Databases      │            │
                    │             │                     │            │
                    │             │  PostgreSQL :5432   │            │
                    │             │  Redis :6379        │            │
                    │             └─────────────────────┘            │
                    │                                                 │
                    └────────────────────────────────────────────────┘
```

---

## 4. Project Structure (Mono-repo)

```
QuickBite/
├── pom.xml                           # Parent POM
├── ARCHITECTURE.md                   # This file
├── README.md                         # Quick start guide
├── docker-compose.yml                # Full stack
├── docker-compose.infra.yml          # Infrastructure only
│
├── docs/
│   ├── api/                          # API documentation
│   └── diagrams/                     # Architecture diagrams
│
├── config-repo/                      # Git-based config (local)
│   ├── application.yml               # Shared config
│   ├── user-service.yml
│   ├── restaurant-service.yml
│   ├── order-service.yml
│   ├── payment-service.yml
│   ├── delivery-service.yml
│   └── notification-service.yml
│
├── infrastructure/
│   ├── discovery-server/             # Eureka Server
│   │   ├── pom.xml
│   │   └── src/
│   ├── config-server/                # Spring Cloud Config
│   │   ├── pom.xml
│   │   └── src/
│   └── api-gateway/                  # Spring Cloud Gateway
│       ├── pom.xml
│       └── src/
│
├── services/
│   ├── user-service/
│   │   ├── pom.xml
│   │   └── src/
│   ├── restaurant-service/
│   │   ├── pom.xml
│   │   └── src/
│   ├── order-service/
│   │   ├── pom.xml
│   │   └── src/
│   ├── payment-service/
│   │   ├── pom.xml
│   │   └── src/
│   ├── delivery-service/
│   │   ├── pom.xml
│   │   └── src/
│   └── notification-service/
│       ├── pom.xml
│       └── src/
│
└── common/                           # Shared library
    ├── pom.xml
    └── src/
        └── main/java/com/quickbite/common/
            ├── dto/                  # Shared DTOs
            ├── event/                # Kafka/RabbitMQ events
            ├── exception/            # Common exceptions
            └── util/                 # Utilities
```

---

## 5. Services Detail

### 5.1 Infrastructure Services

#### Discovery Server (Eureka)
- **Port:** 8761
- **Purpose:** Service registry và discovery
- **Dashboard:** http://localhost:8761

#### Config Server
- **Port:** 8888
- **Purpose:** Centralized configuration
- **Config source:** Local git repo (`config-repo/`)

#### API Gateway
- **Port:** 8080
- **Purpose:** Single entry point, routing, rate limiting
- **Features:**
  - JWT validation
  - Rate limiting
  - Request/Response logging
  - Circuit breaker

### 5.2 Business Services

#### User Service (:8081)
| Entity | Fields |
|--------|--------|
| **User** | id, email, password, fullName, phone, role, status, createdAt |
| **Address** | id, userId, label, address, lat, lng, isDefault |

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/auth/register` | POST | Register new user |
| `/api/auth/login` | POST | Login, return JWT |
| `/api/auth/refresh` | POST | Refresh token |
| `/api/users/me` | GET | Get current user |
| `/api/users/me/addresses` | GET/POST | Manage addresses |

---

#### Restaurant Service (:8082)
| Entity | Fields |
|--------|--------|
| **Restaurant** | id, name, description, address, lat, lng, rating, status, ownerId |
| **Category** | id, restaurantId, name, sortOrder |
| **MenuItem** | id, categoryId, name, description, price, image, isAvailable |

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/restaurants` | GET | List restaurants (with filters) |
| `/api/restaurants/{id}` | GET | Restaurant detail + menu |
| `/api/restaurants/{id}/menu` | GET | Full menu |
| `/api/owner/restaurant` | CRUD | Owner manages their restaurant |

---

#### Order Service (:8083)
| Entity | Fields |
|--------|--------|
| **Order** | id, customerId, restaurantId, driverId, status, totalAmount, deliveryAddress, createdAt |
| **OrderItem** | id, orderId, menuItemId, quantity, price, note |
| **Cart** | id visitorId/customerId, restaurantId, items (JSON), updatedAt |

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/cart` | GET/POST/DELETE | Manage cart |
| `/api/orders` | POST | Create order from cart |
| `/api/orders/{id}` | GET | Order detail |
| `/api/orders/{id}/status` | PATCH | Update status (internal) |

**Order Status Flow:**
```
PENDING → CONFIRMED → PREPARING → READY → PICKED_UP → DELIVERED
                ↓                              ↓
            CANCELLED                      CANCELLED
```

---

#### Payment Service (:8084)
| Entity | Fields |
|--------|--------|
| **Payment** | id, orderId, amount, method, status, transactionId, createdAt |
| **Wallet** | id, userId, balance, updatedAt |
| **WalletTransaction** | id, walletId, type, amount, description, createdAt |

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/payments` | POST | Process payment |
| `/api/payments/{id}` | GET | Payment status |
| `/api/wallet` | GET | Get wallet balance |
| `/api/wallet/topup` | POST | Add money to wallet |

---

#### Delivery Service (:8085)
| Entity | Fields |
|--------|--------|
| **Driver** | id, userId, vehicleType, licensePlate, status, currentLat, currentLng |
| **Delivery** | id, orderId, driverId, status, pickupTime, deliveryTime |
| **LocationHistory** | id, driverId, lat, lng, timestamp |

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/drivers/register` | POST | Register as driver |
| `/api/drivers/location` | PUT | Update location |
| `/api/drivers/status` | PUT | Toggle online/offline |
| `/api/deliveries/{id}` | GET | Delivery tracking |
| `/api/deliveries/{id}/accept` | POST | Driver accepts delivery |

---

#### Notification Service (:8086)
| Entity | Fields |
|--------|--------|
| **NotificationTemplate** | id, type, channel, subject, body |
| **NotificationLog** | id, userId, type, channel, status, sentAt |
| **UserPreference** | id, userId, emailEnabled, pushEnabled, smsEnabled |

**Channels:** EMAIL, PUSH, SMS
**Consumes events from:** Kafka (order events), RabbitMQ (transactional notifications)

---

## 6. Event-Driven Communication

### 6.1 Kafka Topics (Domain Events)
```
quickbite.orders          → ORDER_CREATED, ORDER_CONFIRMED, ORDER_READY, ORDER_DELIVERED, ORDER_CANCELLED
quickbite.payments        → PAYMENT_INITIATED, PAYMENT_COMPLETED, PAYMENT_FAILED
quickbite.deliveries      → DRIVER_ASSIGNED, DRIVER_ARRIVED, DELIVERY_COMPLETED
```

### 6.2 RabbitMQ Queues (Transactional)
```
notification.email        → Send email notifications
notification.push         → Send push notifications
notification.sms          → Send SMS notifications
```

### 6.3 Event Flow Example: Place Order
```
1. Customer places order
   └─► order-service creates ORDER (status: PENDING)
       └─► Kafka: ORDER_CREATED

2. payment-service consumes ORDER_CREATED
   └─► Process payment
       └─► Kafka: PAYMENT_COMPLETED / PAYMENT_FAILED

3. order-service consumes PAYMENT_COMPLETED
   └─► Update order status to CONFIRMED
       └─► Kafka: ORDER_CONFIRMED

4. restaurant-service consumes ORDER_CONFIRMED
   └─► Notify restaurant (RabbitMQ → notification-service)

5. Restaurant marks order READY
   └─► Kafka: ORDER_READY

6. delivery-service consumes ORDER_READY
   └─► Find available driver
       └─► Kafka: DRIVER_ASSIGNED

7. notification-service consumes all events
   └─► Send appropriate notifications via RabbitMQ
```

---

## 7. Database Schema (Per Service)

### user_db
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(200),
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'CUSTOMER',  -- CUSTOMER, RESTAURANT_OWNER, DRIVER, ADMIN
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    label VARCHAR(50),
    address TEXT NOT NULL,
    lat DECIMAL(10, 8),
    lng DECIMAL(11, 8),
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token TEXT UNIQUE NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE
);
```

### restaurant_db
```sql
CREATE TABLE restaurants (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    address TEXT,
    lat DECIMAL(10, 8),
    lng DECIMAL(11, 8),
    rating DECIMAL(2, 1) DEFAULT 0,
    status VARCHAR(20) DEFAULT 'PENDING',  -- PENDING, ACTIVE, SUSPENDED
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    restaurant_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    sort_order INT DEFAULT 0
);

CREATE TABLE menu_items (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    image_url TEXT,
    is_available BOOLEAN DEFAULT TRUE
);
```

### order_db
```sql
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    driver_id BIGINT,
    status VARCHAR(20) DEFAULT 'PENDING',
    subtotal DECIMAL(10, 2),
    delivery_fee DECIMAL(10, 2),
    total_amount DECIMAL(10, 2),
    delivery_address TEXT,
    delivery_lat DECIMAL(10, 8),
    delivery_lng DECIMAL(11, 8),
    note TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    menu_item_name VARCHAR(200),
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2),
    note TEXT
);

CREATE TABLE carts (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT,
    session_id VARCHAR(100),
    restaurant_id BIGINT,
    items JSONB,
    updated_at TIMESTAMP DEFAULT NOW()
);
```

### payment_db
```sql
CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    method VARCHAR(20),  -- WALLET, CARD, COD
    status VARCHAR(20) DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE wallets (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    balance DECIMAL(12, 2) DEFAULT 0,
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE wallet_transactions (
    id BIGSERIAL PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    type VARCHAR(20),  -- TOPUP, PAYMENT, REFUND
    amount DECIMAL(10, 2),
    description TEXT,
    created_at TIMESTAMP DEFAULT NOW()
);
```

### delivery_db
```sql
CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    vehicle_type VARCHAR(20),
    license_plate VARCHAR(20),
    status VARCHAR(20) DEFAULT 'OFFLINE',  -- ONLINE, OFFLINE, BUSY
    current_lat DECIMAL(10, 8),
    current_lng DECIMAL(11, 8),
    rating DECIMAL(2, 1) DEFAULT 5.0,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE deliveries (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    driver_id BIGINT,
    status VARCHAR(20) DEFAULT 'PENDING',  -- PENDING, ASSIGNED, PICKED_UP, DELIVERED
    assigned_at TIMESTAMP,
    picked_up_at TIMESTAMP,
    delivered_at TIMESTAMP
);

CREATE TABLE location_history (
    id BIGSERIAL PRIMARY KEY,
    driver_id BIGINT NOT NULL,
    lat DECIMAL(10, 8),
    lng DECIMAL(11, 8),
    recorded_at TIMESTAMP DEFAULT NOW()
);
```

### notification_db
```sql
CREATE TABLE notification_templates (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,  -- ORDER_CONFIRMED, DRIVER_ASSIGNED, etc.
    channel VARCHAR(20) NOT NULL,  -- EMAIL, PUSH, SMS
    subject VARCHAR(200),
    body TEXT NOT NULL
);

CREATE TABLE notification_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    type VARCHAR(50),
    channel VARCHAR(20),
    status VARCHAR(20),  -- SENT, FAILED
    error_message TEXT,
    sent_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    email_enabled BOOLEAN DEFAULT TRUE,
    push_enabled BOOLEAN DEFAULT TRUE,
    sms_enabled BOOLEAN DEFAULT FALSE
);
```

---

## 8. Infrastructure Configuration

### 8.1 docker-compose.infra.yml
```yaml
version: '3.8'

services:
  # PostgreSQL
  postgres:
    image: postgres:16-alpine
    environment:
      POSTGRES_USER: quickbite
      POSTGRES_PASSWORD: quickbite123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init-databases.sql:/docker-entrypoint-initdb.d/init.sql

  # Redis
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  # Kafka + Zookeeper
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  # RabbitMQ
  rabbitmq:
    image: rabbitmq:3-management-alpine
    ports:
      - "5672:5672"
      - "15672:15672"  # Management UI
    environment:
      RABBITMQ_DEFAULT_USER: quickbite
      RABBITMQ_DEFAULT_PASS: quickbite123

  # Zipkin
  zipkin:
    image: openzipkin/zipkin
    ports:
      - "9411:9411"

volumes:
  postgres_data:
```

### 8.2 init-databases.sql
```sql
CREATE DATABASE user_db;
CREATE DATABASE restaurant_db;
CREATE DATABASE order_db;
CREATE DATABASE payment_db;
CREATE DATABASE delivery_db;
CREATE DATABASE notification_db;
```

---

## 9. Development Phases

### Phase 1: Infrastructure Setup (Day 1)
- [ ] Parent POM setup
- [ ] Discovery Server (Eureka)
- [ ] Config Server + config-repo
- [ ] API Gateway (basic routing)
- [ ] docker-compose.infra.yml
- [ ] Test: All infrastructure services running

### Phase 2: User Service (Day 2)
- [ ] User entity + repository
- [ ] Auth endpoints (register, login, refresh)
- [ ] JWT implementation
- [ ] Address management
- [ ] API Gateway integration
- [ ] Test: Can register/login via Gateway

### Phase 3: Restaurant Service (Day 2-3)
- [ ] Restaurant, Category, MenuItem entities
- [ ] CRUD endpoints
- [ ] Search/filter restaurants
- [ ] Test: Can browse restaurants via Gateway

### Phase 4: Order Service (Day 3-4)
- [ ] Cart management
- [ ] Order creation
- [ ] Order status management
- [ ] Kafka producer (ORDER events)
- [ ] Test: Can place order, events published

### Phase 5: Payment Service (Day 4)
- [ ] Payment entity
- [ ] Wallet implementation
- [ ] Kafka consumer (ORDER_CREATED)
- [ ] Kafka producer (PAYMENT events)
- [ ] Test: Order → Payment flow works

### Phase 6: Delivery Service (Day 5)
- [ ] Driver entity
- [ ] Kafka consumer (ORDER_READY)
- [ ] Driver assignment logic
- [ ] Location tracking
- [ ] Test: Order → Delivery assignment works

### Phase 7: Notification Service (Day 5-6)
- [ ] RabbitMQ consumers
- [ ] Email/Push templates
- [ ] Kafka consumer (all events)
- [ ] Test: Notifications sent on events

### Phase 8: Polish & DevOps (Day 6-7)
- [ ] Distributed tracing (Zipkin)
- [ ] Circuit breaker (Resilience4j)
- [ ] Rate limiting
- [ ] Full docker-compose.yml
- [ ] README with setup instructions

---

## 10. Key Patterns Checklist

| Pattern | Service/Component | Status |
|---------|------------------|--------|
| API Gateway | api-gateway | [ ] |
| Service Discovery | discovery-server | [ ] |
| Centralized Config | config-server | [ ] |
| Database per Service | All services | [ ] |
| Event-Driven (Kafka) | order, payment, delivery | [ ] |
| Message Queue (RabbitMQ) | notification | [ ] |
| Circuit Breaker | api-gateway, Feign clients | [ ] |
| Distributed Tracing | All services | [ ] |
| JWT Authentication | user-service, gateway | [ ] |
| CQRS (optional) | order-service | [ ] |
| Saga Pattern | order flow | [ ] |

---

## 11. CV Summary

```
QuickBite - Food Delivery Platform (Microservices Architecture)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tech Stack:
• Java 21, Spring Boot 3.3, Spring Cloud 2024
• PostgreSQL, Redis, Apache Kafka, RabbitMQ
• Docker, Docker Compose

Architecture & Patterns:
• Designed microservices architecture with 6 business services
• Implemented API Gateway with Spring Cloud Gateway (routing, rate limiting, JWT validation)
• Service Discovery with Netflix Eureka
• Centralized configuration with Spring Cloud Config
• Event-driven architecture using Apache Kafka for domain events
• RabbitMQ for transactional messaging (notifications)
• Database-per-service pattern with PostgreSQL
• Distributed tracing with Zipkin
• Circuit breaker pattern with Resilience4j
• Containerized with Docker Compose

Services:
• User Service - Authentication, JWT, user management
• Restaurant Service - Restaurant/menu catalog
• Order Service - Order lifecycle, cart management
• Payment Service - Payment processing, wallet
• Delivery Service - Driver management, real-time tracking
• Notification Service - Multi-channel notifications (email, push, SMS)
```

---

## 12. References

- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Microservices Patterns by Chris Richardson](https://microservices.io/patterns/)
- [Event-Driven Architecture](https://martinfowler.com/articles/201701-event-driven.html)
- [12-Factor App](https://12factor.net/)

---

**Status:** APPROVED  
**Created:** 2024-11-18  
**Approach:** Mono-repo, Infrastructure-first, MVP services
