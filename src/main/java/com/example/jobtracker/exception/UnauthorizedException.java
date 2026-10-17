package com.example.jobtracker.exception;

/** Thrown when authentication fails, such as wrong credentials; results in HTTP 401. */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
