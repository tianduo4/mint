package com.mint.common.security;

public class UserNotAcitveException extends AccountStatusException {
    public UserNotAcitveException() {
    }

    public UserNotAcitveException(String s) {
        super(s);
    }

    public UserNotAcitveException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}

