package com.example.jobtracker.exception;

/** Thrown when a request conflicts with existing data, such as a duplicate email; results in HTTP 409. */
public class ConflictException extends RuntimeException{

    public ConflictException (String message) {
        super(message);
    }
}
