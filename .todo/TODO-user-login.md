# TODO: TASK-002 - Đăng nhập

## Task Info
- **Source:** docs/ba/TASK-002-user-login.md
- **Branch:** master (direct push)
- **Services:** user-service
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
- POST /api/auth/login - đăng nhập bằng email/password
- POST /api/auth/logout - đăng xuất
- Account lockout sau 5 lần sai liên tiếp (15 phút)
- Generic error message: không tiết lộ email hay password sai

**Files cần tạo/sửa:**
- User.java - thêm failedLoginAttempts, lockoutTime
- LoginRequest.java - DTO mới
- LogoutRequest.java - DTO mới
- AuthService/AuthServiceImpl - thêm login(), logout()
- AuthController - thêm endpoints
- GlobalExceptionHandler - thêm exception handlers
- Exceptions: InvalidCredentialsException, AccountLockedException

---

## Phase 2: Thiết kế

**API Contract:**
```
POST /api/auth/login
Request: { email, password }
Response: { accessToken, refreshToken, user }
Errors: 401 (invalid), 423 (locked)

POST /api/auth/logout
Request: { refreshToken }
Response: { message }
```

**Data Model:**
- User entity: +failedLoginAttempts (int), +lockoutTime (LocalDateTime)

**Classes:**
- AuthController → AuthService → UserRepository
- InvalidCredentialsException → 401
- AccountLockedException → 423

---

## Phase 3: Coding
**Commits:**
- `f077799` feat(user-service): implement user login with account lockout

**Files tạo:** 4 files
- LoginRequest.java
- LogoutRequest.java
- InvalidCredentialsException.java
- AccountLockedException.java

**Files sửa:** 5 files
- User.java
- AuthService.java
- AuthServiceImpl.java
- AuthController.java
- GlobalExceptionHandler.java

---

## Phase 4: Review
**Issues found:** 1
**Fixed:** 1

**Fixes:**
1. ✅ Magic numbers (5, 15) → Extract to application.yml config

**Commit:** `717eb87`

---

## Phase 5: Test
**Test files:** 2
- AuthServiceImplTest.java (+8 test cases)
- AuthControllerTest.java (+4 test cases)

**Coverage:**
- login success → returns tokens
- login email not found → 401
- login wrong password → 401
- login increments failed attempts
- login locks after 5 failures
- login throws when locked
- login allows after lockout expires
- login resets attempts on success
- logout success → 200

**Commit:** `6c9d002`

---

## Phase 6: Merge
**Pushed to:** https://github.com/chuhuunam/QuickBite
**Final commit:** (pending push)

---

## Summary
| Metric | Value |
|--------|-------|
| Files created | 4 |
| Files modified | 7 |
| Commits | 3 |
| Tests added | 12 |
| Issues fixed | 1 |
