package com.quickbite.user.exception;

import java.time.LocalDateTime;

public class AccountLockedException extends RuntimeException {

    private final LocalDateTime lockoutTime;

    public AccountLockedException(LocalDateTime lockoutTime) {
        super("Tài khoản đã bị khóa tạm thời. Vui lòng thử lại sau.");
        this.lockoutTime = lockoutTime;
    }

    public LocalDateTime getLockoutTime() {
        return lockoutTime;
    }
}
