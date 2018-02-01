package com.jspgou.common.security;

public class DisabledException extends AccountStatusException {
    public DisabledException() {
    }

    public DisabledException(String msg) {
        super(msg);
    }

    public DisabledException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}

