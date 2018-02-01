package com.jspgou.common.security;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException() {
    }

    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}

