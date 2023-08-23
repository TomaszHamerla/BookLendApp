package com.example.booklendapp.exception;

public class InvalidUserDataException extends RuntimeException{
    public InvalidUserDataException(String message) {
        super(message);
    }
}
