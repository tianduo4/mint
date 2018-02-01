package com.mint.common.security;

public class AccountStatusException extends AuthenticationException {
    public AccountStatusException() {
    }

    public AccountStatusException(String msg) {
        super(msg);
    }

    public AccountStatusException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}

