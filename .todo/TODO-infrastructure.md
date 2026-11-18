# TODO: Infrastructure Setup

## Task Info
- **Source:** ARCHITECTURE.md (Phase 1)
- **Branch:** master (direct push)
- **Services:** discovery-server, docker
- **Started:** 2026-11-18
- **Completed:** 2026-11-18

## Phase Progress
- [x] Phase 1: Phân tích ✅
- [x] Phase 2: Thiết kế ✅
- [x] Phase 3: Coding ✅
- [x] Phase 4: Review ✅
- [x] Phase 5: Test ✅
- [x] Phase 6: Merge ✅ DONE

---

## Phase 1: Phân tích
**Scope:**
- Discovery Server (Eureka) trên port 8761
- Docker Compose cho infrastructure (Postgres, Redis, Kafka, RabbitMQ, Zipkin)
- Docker Compose cho full stack
- Dockerfiles cho các services
- User-service đăng ký với Eureka

**Files cần tạo:**
- infrastructure/discovery-server/ (module mới)
- docker-compose.yml, docker-compose.infra.yml
- Dockerfile cho mỗi service
- init-databases.sql

---

## Phase 2: Thiết kế

**Discovery Server:**
- Port: 8761
- Dashboard: http://localhost:8761
- Standalone mode (không cluster)

**Docker Services:**
| Service | Image | Port |
|---------|-------|------|
| postgres | postgres:16-alpine | 5432 |
| redis | redis:7-alpine | 6379 |
| kafka | confluentinc/cp-kafka:7.5.0 | 9092 |
| rabbitmq | rabbitmq:3-management | 5672, 15672 |
| zipkin | openzipkin/zipkin | 9411 |
| discovery-server | custom | 8761 |
| user-service | custom | 8081 |

**Profiles:**
- application.yml - Local (H2, Eureka localhost)
- application-docker.yml - Docker (PostgreSQL, Eureka container)
- application-test.yml - Test (H2, Eureka disabled)

---

## Phase 3: Coding
**Commits:**
- `b4f0fc6` feat: add Discovery Server (Eureka) and Docker configuration

**Files tạo:** 14 files
```
infrastructure/discovery-server/
├── pom.xml
├── Dockerfile
└── src/main/
    ├── java/.../DiscoveryServerApplication.java
    └── resources/application.yml

services/user-service/
├── Dockerfile
└── src/main/resources/application-docker.yml

docker-compose.yml
docker-compose.infra.yml
init-databases.sql
.dockerignore
```

**Files sửa:** 4 files
- pom.xml (parent) - thêm module
- services/user-service/pom.xml - thêm Eureka client
- services/user-service/application.yml - thêm Eureka config
- services/user-service/application-test.yml - disable Eureka

---

## Phase 4: Review
**Issues found:** 0
**Fixed:** 0

---

## Phase 5: Test
**Manual test:**
```bash
# Build
mvn clean package -DskipTests

# Start infrastructure
docker-compose -f docker-compose.infra.yml up -d

# Check Eureka
curl http://localhost:8761/actuator/health
```

---

## Phase 6: Merge
**Pushed to:** https://github.com/chuhuunam/QuickBite
**Final commit:** `b4f0fc6`

---

## Summary
| Metric | Value |
|--------|-------|
| Files created | 14 |
| Files modified | 4 |
| Commits | 1 |
| New modules | 1 (discovery-server) |
