package com.mint.core.security;

import com.mint.common.security.AuthenticationException;

public class UserNotInWebsiteException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public UserNotInWebsiteException() {
    }

    public UserNotInWebsiteException(String s) {
        super(s);
    }
}

