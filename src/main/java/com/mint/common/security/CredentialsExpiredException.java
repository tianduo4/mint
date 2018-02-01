package com.mint.common.security;

public class CredentialsExpiredException extends AccountStatusException {
    public CredentialsExpiredException() {
    }

    public CredentialsExpiredException(String msg) {
        super(msg);
    }

    public CredentialsExpiredException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}

