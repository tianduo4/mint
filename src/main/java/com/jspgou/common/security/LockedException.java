package com.jspgou.common.security;

public class LockedException extends AccountStatusException {
    public LockedException() {
    }

    public LockedException(String msg) {
        super(msg);
    }

    public LockedException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}

