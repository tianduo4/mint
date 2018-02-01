package com.mint.common.security.rememberme;

public class CookieTheftException extends RememberMeAuthenticationException {
    public CookieTheftException(String message) {
        super(message);
    }
}

