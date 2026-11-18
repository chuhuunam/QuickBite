---
name: coding-standards
description: >
  Quy chuẩn code cho QuickBite (BE: Java 21/Spring Boot 3.3; FE: Angular 19; DB: PostgreSQL; Git: Conventional Commits).
  Trigger: "viết code", "tạo API", "tạo component", "code theo chuẩn", "review code", "commit"
---

# Coding Standards - Index

Đọc đúng file trước khi code:

| Domain | File | Dùng khi |
|--------|------|----------|
| **Backend** | `be-standards.md` | Code Java/Spring Boot |
| **Frontend** | `fe-standards.md` | Code Angular/TypeScript |
| **Database** | `db-standards.md` | Schema, migrations, queries |
| **Git** | `git-standards.md` | Commits, branches, PRs |

## Quick Reference

### BE - Controller
```java
@RestController
@RequestMapping("/api/xxx")
@RequiredArgsConstructor
public class XxxController {
    private final XxxService service;
    
    @GetMapping("/{id}")
    public ResponseEntity<XxxResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
```

### FE - Component
```typescript
@Component({
  selector: 'app-xxx',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './xxx.component.html'
})
export class XxxComponent {
  private service = inject(XxxService);
  data = signal<Xxx | null>(null);
  loading = signal(false);
}
```

### Git Commit
```
feat(user-service): add login endpoint
fix(order-service): handle null cart
docs(api): update authentication flow
```
