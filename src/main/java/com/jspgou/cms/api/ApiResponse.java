package com.jspgou.cms.api;

public class ApiResponse {
    private String body;
    private String message;
    private int code;

    public ApiResponse(String body, String message, int code) {
        this.body = body;
        this.message = message;
        this.code = code;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String toString() {
        return "{\"body\":" + this.body + ", \"message\":" + this.message + ", \"code\":" + this.code + "}";
    }

    public String sourceToString() {
        return "{\"source\":" + this.body + ", \"message\":" + this.message + ", \"code\":" + this.code + "}";
    }
}

