package com.mint.common.security.rememberme;

import com.mint.common.security.AuthenticationException;

public class RememberMeAuthenticationException extends AuthenticationException {
    public RememberMeAuthenticationException() {
    }

    public RememberMeAuthenticationException(String msg) {
        super(msg);
    }
}

