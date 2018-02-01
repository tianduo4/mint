package com.mint.common.security;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException() {
    }

    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}

