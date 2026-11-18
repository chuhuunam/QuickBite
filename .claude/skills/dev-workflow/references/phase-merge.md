# Phase 6: Merge

## Input
- Code đã test pass
- Branch sẵn sàng để merge

## Output
- PR created và merged
- Branch deleted
- Task completed

## Actions

### 1. Tạo PR
```bash
# Tạo PR với GitHub CLI
gh pr create \
  --title "feat(scope): description" \
  --body "## Summary
- Change 1
- Change 2

## Testing
- [ ] Unit tests pass
- [ ] Manual testing done

Closes #<issue-number>"
```

### 2. PR Description Template
```markdown
## Summary
<Mô tả ngắn gọn changes>

## Changes
- <Change 1>
- <Change 2>

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing done

## Screenshots (nếu có UI changes)
<screenshots>

## Related Issues
Closes #xxx
```

### 3. Request Review
- Tag team members để review
- Trả lời comments nếu có
- Fix requested changes

### 4. Merge
```bash
# Sau khi approved
gh pr merge --squash --delete-branch
```

### 5. Cleanup
- Delete local branch: `git branch -d <branch>`
- Update TODO.md: đánh dấu hoàn thành
- Move TODO file to `.todo/done/` (optional)

## Done!
Task hoàn thành. Thông báo cho user và hỏi có task tiếp theo không.
