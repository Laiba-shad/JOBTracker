package com.jobtrackr.server.dto.response;

public class AuthResponse<T> {
    private int status;
    private String message;
    private T data;

    public AuthResponse() { }

    public AuthResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // getters and setters
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}