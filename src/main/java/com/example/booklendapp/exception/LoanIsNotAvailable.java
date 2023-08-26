package com.example.booklendapp.exception;

public class LoanIsNotAvailable extends RuntimeException{
    public LoanIsNotAvailable(String message) {
        super(message);
    }
}
