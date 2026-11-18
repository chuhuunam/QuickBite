# Database Standards - PostgreSQL

## 1. Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Table | snake_case, plural | `users`, `order_items` |
| Column | snake_case | `created_at`, `user_id` |
| Primary Key | `id` | `id BIGSERIAL PRIMARY KEY` |
| Foreign Key | `<table>_id` | `user_id`, `order_id` |
| Index | `idx_<table>_<columns>` | `idx_users_email` |
| Constraint | `<type>_<table>_<column>` | `uq_users_email` |

## 2. Table Template

```sql
CREATE TABLE table_name (
    id BIGSERIAL PRIMARY KEY,
    -- business columns
    name VARCHAR(200) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    -- audit columns
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT,
    updated_by BIGINT
);

-- Indexes
CREATE INDEX idx_table_name_status ON table_name(status);

-- Comments
COMMENT ON TABLE table_name IS 'Description of table';
COMMENT ON COLUMN table_name.name IS 'Description of column';
```

## 3. Data Types

| Use Case | Type | Example |
|----------|------|---------|
| Primary Key | BIGSERIAL | `id BIGSERIAL PRIMARY KEY` |
| UUID | UUID | `uuid UUID DEFAULT gen_random_uuid()` |
| Short text | VARCHAR(n) | `name VARCHAR(200)` |
| Long text | TEXT | `description TEXT` |
| Integer | INTEGER | `quantity INTEGER` |
| Decimal/Money | DECIMAL(p,s) | `price DECIMAL(10,2)` |
| Boolean | BOOLEAN | `is_active BOOLEAN DEFAULT TRUE` |
| Timestamp | TIMESTAMP | `created_at TIMESTAMP` |
| Date | DATE | `birth_date DATE` |
| JSON | JSONB | `metadata JSONB` |
| Enum | VARCHAR | `status VARCHAR(20)` |

## 4. Foreign Keys

```sql
-- Define foreign key
ALTER TABLE orders
ADD CONSTRAINT fk_orders_customer
FOREIGN KEY (customer_id) REFERENCES users(id);

-- With cascade
ALTER TABLE order_items
ADD CONSTRAINT fk_order_items_order
FOREIGN KEY (order_id) REFERENCES orders(id)
ON DELETE CASCADE;
```

## 5. Indexes

```sql
-- Single column
CREATE INDEX idx_users_email ON users(email);

-- Composite
CREATE INDEX idx_orders_customer_status ON orders(customer_id, status);

-- Partial
CREATE INDEX idx_orders_pending ON orders(created_at) 
WHERE status = 'PENDING';

-- Unique
CREATE UNIQUE INDEX uq_users_email ON users(email);
```

## 6. Flyway Migrations

```
resources/db/migration/
├── V1__create_users_table.sql
├── V2__create_restaurants_table.sql
├── V3__create_orders_table.sql
└── V4__add_index_orders_status.sql
```

**Naming:** `V{version}__{description}.sql`
- Version: sequential number (1, 2, 3...)
- Description: snake_case, what it does
- Double underscore between version and description

```sql
-- V1__create_users_table.sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(200),
    role VARCHAR(20) DEFAULT 'CUSTOMER',
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_status ON users(status);
```

## 7. Query Best Practices

```sql
-- ✅ Select specific columns
SELECT id, name, email FROM users WHERE status = 'ACTIVE';

-- ❌ Avoid SELECT *
SELECT * FROM users;

-- ✅ Use LIMIT for large tables
SELECT * FROM orders ORDER BY created_at DESC LIMIT 100;

-- ✅ Use EXISTS instead of COUNT for checking
SELECT EXISTS(SELECT 1 FROM users WHERE email = 'test@test.com');

-- ✅ Use prepared statements (handled by JPA)
-- ❌ Never concatenate user input into SQL
```

## 8. Common Mistakes to Avoid

- ❌ Không dùng `TEXT` cho tất cả strings → ✅ Dùng `VARCHAR(n)` với limit hợp lý
- ❌ Không thiếu indexes cho FK và frequently queried columns
- ❌ Không dùng `FLOAT` cho tiền → ✅ Dùng `DECIMAL(10,2)`
- ❌ Không để nullable FK không cần thiết
- ❌ Không quên `created_at`, `updated_at` columns
