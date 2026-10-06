package com.example.jobtracker.exception;

/** Thrown when a request is invalid in a way bean validation cannot express; results in HTTP 400. */
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }
}
