# QuickBite - Food Delivery Microservices

A microservices-based food delivery platform built with Spring Boot and Spring Cloud.

## Quick Start

### Prerequisites
- Java 21
- Maven 3.9+
- Docker & Docker Compose

### 1. Start Infrastructure
```bash
docker-compose -f docker-compose.infra.yml up -d
```

This starts: PostgreSQL, Redis, Kafka, RabbitMQ, Zipkin

### 2. Start Services (in order)
```bash
# Terminal 1: Discovery Server
cd infrastructure/discovery-server && mvn spring-boot:run

# Terminal 2: Config Server
cd infrastructure/config-server && mvn spring-boot:run

# Terminal 3: API Gateway
cd infrastructure/api-gateway && mvn spring-boot:run

# Terminal 4+: Business Services
cd services/user-service && mvn spring-boot:run
cd services/restaurant-service && mvn spring-boot:run
# ... etc
```

### 3. Access Points
| Service | URL |
|---------|-----|
| API Gateway | http://localhost:8080 |
| Eureka Dashboard | http://localhost:8761 |
| RabbitMQ Management | http://localhost:15672 |
| Zipkin | http://localhost:9411 |

## Architecture

See [ARCHITECTURE.md](ARCHITECTURE.md) for full documentation.

```
Client → API Gateway → Services → Kafka/RabbitMQ → Services
              ↓
         Eureka (Discovery)
              ↓
         Config Server
```

## Services

| Service | Port | Description |
|---------|------|-------------|
| discovery-server | 8761 | Eureka service registry |
| config-server | 8888 | Centralized configuration |
| api-gateway | 8080 | API Gateway |
| user-service | 8081 | Auth & user management |
| restaurant-service | 8082 | Restaurant & menu |
| order-service | 8083 | Order & cart |
| payment-service | 8084 | Payment & wallet |
| delivery-service | 8085 | Delivery & tracking |
| notification-service | 8086 | Notifications |

## Tech Stack

- Java 21, Spring Boot 3.3, Spring Cloud 2024
- PostgreSQL, Redis, Kafka, RabbitMQ
- Docker, Zipkin
