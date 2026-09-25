package com.example._4ecommerceapi.exception;

import java.time.LocalDateTime;
import java.util.List;

// Standard error shape returned for EVERY error in the API
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int            status;
    private String         error;
    private String         message;
    private List<String> details;  // for validation errors (multiple messages)

    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(int status, String error, String message, List<String> details) {
        this(status, error, message);
        this.details = details;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int           getStatus()    { return status; }
    public String        getError()     { return error; }
    public String        getMessage()   { return message; }
    public List<String>  getDetails()   { return details; }
}