package com.mint.common.security;

import org.apache.shiro.authc.AccountException;

public class AuthenticationException extends AccountException {
    private Object extraInformation;

    public AuthenticationException() {
    }

    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException(String msg, Object extraInformation) {
        super(msg);
        this.extraInformation = extraInformation;
    }

    public Object getExtraInformation() {
        return this.extraInformation;
    }

    public void clearExtraInformation() {
        this.extraInformation = null;
    }
}

