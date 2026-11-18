# Backend Standards - Java 21 / Spring Boot 3.3

## 1. Project Structure

```
services/<service-name>/src/main/java/com/quickbite/<service>/
├── controller/          # REST controllers
├── service/             # Business logic (interface + impl)
├── repository/          # JPA repositories
├── entity/              # JPA entities
├── dto/                 # Request/Response DTOs
├── mapper/              # MapStruct mappers
├── exception/           # Custom exceptions
├── config/              # Configuration classes
└── event/               # Kafka/RabbitMQ events
```

## 2. Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Class | PascalCase | `UserService`, `OrderController` |
| Method | camelCase | `findById`, `createOrder` |
| Variable | camelCase | `userId`, `orderItems` |
| Constant | UPPER_SNAKE | `MAX_RETRY_COUNT` |
| Package | lowercase | `com.quickbite.user` |

## 3. Controller Pattern

```java
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management APIs")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        log.info("Getting user by id: {}", id);
        return ResponseEntity.ok(userService.getById(id));
    }
    
    @PostMapping
    @Operation(summary = "Create new user")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        log.info("Creating user with email: {}", request.getEmail());
        UserResponse response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

## 4. Service Pattern

```java
// Interface (bắt buộc)
public interface UserService {
    UserResponse getById(Long id);
    UserResponse create(CreateUserRequest request);
}

// Implementation
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return userMapper.toResponse(user);
    }
    
    @Override
    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        User user = userMapper.toEntity(request);
        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
```

## 5. Entity Pattern

```java
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.CUSTOMER;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

## 6. DTO Pattern

```java
// Request DTO
@Data
@Builder
public class CreateUserRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    private String fullName;
}

// Response DTO
@Data
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private UserRole role;
    private LocalDateTime createdAt;
}
```

## 7. Exception Handling

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.of("NOT_FOUND", ex.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .toList();
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of("VALIDATION_ERROR", errors));
    }
}
```

## 8. Logging

```java
// Dùng @Slf4j annotation
@Slf4j
public class UserService {
    
    public void process() {
        log.info("Processing started");           // INFO cho flow chính
        log.debug("Debug details: {}", data);     // DEBUG cho chi tiết
        log.warn("Warning: {}", warning);         // WARN cho cảnh báo
        log.error("Error occurred", exception);   // ERROR cho lỗi
    }
}

// KHÔNG log sensitive data
log.info("User login: {}", email);  // OK
log.info("Password: {}", password); // KHÔNG ĐƯỢC
```

## 9. Kafka Events

```java
// Event class
@Data
@Builder
public class OrderCreatedEvent {
    private String eventId;
    private String orderId;
    private Long customerId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}

// Producer
@Service
@RequiredArgsConstructor
public class OrderEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publishOrderCreated(Order order) {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
            .eventId(UUID.randomUUID().toString())
            .orderId(order.getId())
            .customerId(order.getCustomerId())
            .totalAmount(order.getTotalAmount())
            .createdAt(LocalDateTime.now())
            .build();
        kafkaTemplate.send("quickbite.orders", event);
    }
}
```

## 10. Common Mistakes to Avoid

- ❌ Không dùng `@Autowired` field injection → ✅ Dùng constructor injection
- ❌ Không catch generic `Exception` → ✅ Catch specific exceptions
- ❌ Không return entity trực tiếp → ✅ Return DTO
- ❌ Không hardcode values → ✅ Dùng `@Value` hoặc config
- ❌ Không dùng `@Transactional` ở controller → ✅ Dùng ở service
