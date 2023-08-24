package com.example.booklendapp.exception;

public class BookIsNotAvailableException extends RuntimeException{
    public BookIsNotAvailableException(String message) {
        super(message);
    }
}
