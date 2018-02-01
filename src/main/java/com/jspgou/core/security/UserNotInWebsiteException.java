package com.jspgou.core.security;

import com.jspgou.common.security.AuthenticationException;

public class UserNotInWebsiteException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public UserNotInWebsiteException() {
    }

    public UserNotInWebsiteException(String s) {
        super(s);
    }
}

