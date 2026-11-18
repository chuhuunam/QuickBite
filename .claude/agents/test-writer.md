---
name: test-writer
description: >
  Generate unit tests và integration tests cho QuickBite.
  Trigger: "viết test", "tạo test", "generate test"
tools: Read, Write, Edit, Bash
---

# Test Writer - QuickBite

Generate tests cho code mới hoặc code chưa có tests.

## Backend Tests (JUnit 5 + Mockito)

### Unit Test Template - Service

```java
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    @DisplayName("getById - should return user when exists")
    void getById_WhenUserExists_ShouldReturnUser() {
        // Given
        Long userId = 1L;
        User user = User.builder().id(userId).email("test@test.com").build();
        UserResponse expected = UserResponse.builder().id(userId).email("test@test.com").build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(expected);
        
        // When
        UserResponse result = userService.getById(userId);
        
        // Then
        assertThat(result).isEqualTo(expected);
        verify(userRepository).findById(userId);
    }
    
    @Test
    @DisplayName("getById - should throw when user not found")
    void getById_WhenUserNotFound_ShouldThrow() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // When/Then
        assertThatThrownBy(() -> userService.getById(userId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("User");
    }
}
```

### Integration Test Template - Controller

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }
    
    @Test
    @DisplayName("POST /api/users - should create user")
    void createUser_ShouldReturnCreated() throws Exception {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .email("test@test.com")
            .password("password123")
            .build();
        
        // When/Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value("test@test.com"));
    }
}
```

## Frontend Tests (Jasmine)

### Component Test Template

```typescript
describe('RestaurantListComponent', () => {
  let component: RestaurantListComponent;
  let fixture: ComponentFixture<RestaurantListComponent>;
  let restaurantService: jasmine.SpyObj<RestaurantService>;

  beforeEach(async () => {
    const spy = jasmine.createSpyObj('RestaurantService', ['getAll']);
    
    await TestBed.configureTestingModule({
      imports: [RestaurantListComponent],
      providers: [
        { provide: RestaurantService, useValue: spy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RestaurantListComponent);
    component = fixture.componentInstance;
    restaurantService = TestBed.inject(RestaurantService) as jasmine.SpyObj<RestaurantService>;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load restaurants on init', () => {
    const mockRestaurants = [{ id: '1', name: 'Restaurant 1' }];
    restaurantService.getAll.and.returnValue(of(mockRestaurants));
    
    fixture.detectChanges();
    
    expect(component.restaurants()).toEqual(mockRestaurants);
    expect(component.loading()).toBeFalse();
  });

  it('should handle error when loading fails', () => {
    restaurantService.getAll.and.returnValue(throwError(() => new Error('Failed')));
    
    fixture.detectChanges();
    
    expect(component.error()).toBeTruthy();
    expect(component.loading()).toBeFalse();
  });
});
```

### Service Test Template

```typescript
describe('RestaurantService', () => {
  let service: RestaurantService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RestaurantService]
    });
    
    service = TestBed.inject(RestaurantService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch all restaurants', () => {
    const mockData = [{ id: '1', name: 'Test' }];
    
    service.getAll().subscribe(data => {
      expect(data).toEqual(mockData);
    });
    
    const req = httpMock.expectOne(`${environment.apiUrl}/api/restaurants`);
    expect(req.request.method).toBe('GET');
    req.flush(mockData);
  });
});
```

## Test Coverage Guidelines

- **Happy path:** Luôn có
- **Edge cases:** null, empty, boundary values
- **Error cases:** Exceptions, error responses
- **Target:** >= 80% coverage cho code mới

## Commands

```bash
# Backend
mvn test                           # Run all tests
mvn test -Dtest=UserServiceTest    # Run specific test
mvn test jacoco:report             # With coverage

# Frontend
ng test --watch=false              # Run all tests
ng test --include=**/user/*        # Run specific tests
ng test --code-coverage            # With coverage
```
