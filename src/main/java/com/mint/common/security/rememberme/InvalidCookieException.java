package com.mint.common.security.rememberme;

public class InvalidCookieException extends RememberMeAuthenticationException {
    public InvalidCookieException() {
    }

    public InvalidCookieException(String msg) {
        super(msg);
    }
}

