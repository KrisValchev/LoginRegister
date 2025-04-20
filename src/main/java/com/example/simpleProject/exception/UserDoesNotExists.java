package com.example.simpleProject.exception;

public class UserDoesNotExists extends RuntimeException {
    public UserDoesNotExists(String message) {
        super(message);
    }
}
