package com.huy.cryptoblog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
    private String error;
    private Map<String , String> validationErrors;

    // regular constructor
    public ErrorResponse(String message, int status, String path, String error) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.error = error;
        this.path = path;
    }

    // constructor for validation errors
    public ErrorResponse(String message, int status, String path,String error, Map<String, String> validationErrors) {
        this(message, status, path, error);
        this.validationErrors = validationErrors;
    }
}
