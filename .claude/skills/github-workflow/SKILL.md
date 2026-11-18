---
name: github-workflow
description: >
  Quy trình làm việc với GitHub Issues và Pull Requests cho QuickBite.
  Trigger: "tạo issue", "tạo PR", "link issue", "close issue"
---

# GitHub Workflow

Quy trình làm việc với GitHub cho team QuickBite.

## Prerequisites

```bash
# Cài đặt GitHub CLI
# Windows: winget install GitHub.cli
# Mac: brew install gh

# Login
gh auth login
```

## 1. GitHub Issues

### Tạo Issue
```bash
gh issue create \
  --title "feat: User registration" \
  --body "## Description
Implement user registration flow.

## Acceptance Criteria
- [ ] POST /api/auth/register works
- [ ] Email validation
- [ ] Password hashing

## Technical Notes
- Use BCrypt for password
- Send welcome email"
```

### List Issues
```bash
# All open issues
gh issue list

# Filter by label
gh issue list --label "bug"
gh issue list --label "feature"

# Assigned to me
gh issue list --assignee @me
```

### View Issue
```bash
gh issue view 123
gh issue view 123 --web  # Open in browser
```

## 2. Branches

### Create Branch from Issue
```bash
# Convention: feature/<issue>-<short-name>
git checkout main
git pull origin main
git checkout -b feature/123-user-registration
git push -u origin feature/123-user-registration
```

### Link Branch to Issue
Branch sẽ tự động link khi tên chứa issue number.

## 3. Pull Requests

### Create PR
```bash
gh pr create \
  --title "feat(user-service): add user registration" \
  --body "## Summary
Implement user registration endpoint.

## Changes
- Add UserController with register endpoint
- Add UserService with validation
- Add BCrypt password hashing

## Testing
- [ ] Unit tests pass
- [ ] Manual testing done

## Screenshots
N/A

Closes #123"
```

### PR Template

Tạo file `.github/PULL_REQUEST_TEMPLATE.md`:
```markdown
## Summary
<!-- Mô tả ngắn gọn changes -->

## Changes
<!-- Liệt kê changes -->
- 

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing done

## Screenshots (nếu có UI changes)
<!-- Screenshots -->

## Related Issues
<!-- Closes #xxx -->
```

### View PR
```bash
gh pr view
gh pr view 456 --web
```

### Review PR
```bash
# List PRs cần review
gh pr list --search "review-requested:@me"

# Checkout PR locally để test
gh pr checkout 456

# Approve
gh pr review --approve

# Request changes
gh pr review --request-changes --body "Please fix..."
```

### Merge PR
```bash
# Squash merge (recommended)
gh pr merge --squash --delete-branch

# Merge commit
gh pr merge --merge --delete-branch

# Rebase
gh pr merge --rebase --delete-branch
```

## 4. Labels

Recommended labels:
| Label | Color | Description |
|-------|-------|-------------|
| `bug` | #d73a4a | Something isn't working |
| `feature` | #0075ca | New feature request |
| `enhancement` | #a2eeef | Improvement to existing feature |
| `documentation` | #0075ca | Documentation updates |
| `backend` | #5319e7 | Backend related |
| `frontend` | #1d76db | Frontend related |
| `priority:high` | #b60205 | High priority |
| `priority:low` | #c5def5 | Low priority |

## 5. Automation với GitHub Actions

```yaml
# .github/workflows/pr-checks.yml
name: PR Checks

on:
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean verify
```

## 6. Quick Reference

```bash
# Start new task
gh issue view 123                    # Xem issue
git checkout -b feature/123-desc     # Tạo branch
# ... code ...
gh pr create                         # Tạo PR
gh pr merge --squash                 # Merge

# Common operations
gh issue list                        # List issues
gh pr list                           # List PRs
gh pr status                         # PR status của repo
gh pr checks                         # CI checks của PR hiện tại
```
