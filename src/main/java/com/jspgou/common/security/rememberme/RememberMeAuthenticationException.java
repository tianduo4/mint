package com.jspgou.common.security.rememberme;

import com.jspgou.common.security.AuthenticationException;

public class RememberMeAuthenticationException extends AuthenticationException {
    public RememberMeAuthenticationException() {
    }

    public RememberMeAuthenticationException(String msg) {
        super(msg);
    }
}

