package com.jspgou.common.security;

public class AccountExpiredException extends AccountStatusException {
    public AccountExpiredException() {
    }

    public AccountExpiredException(String msg) {
        super(msg);
    }

    public AccountExpiredException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}

