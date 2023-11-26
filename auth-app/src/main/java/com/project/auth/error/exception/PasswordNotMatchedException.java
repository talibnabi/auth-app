package com.project.auth.error.exception;

public class PasswordNotMatchedException extends RuntimeException {

    public PasswordNotMatchedException(String message) {
        super(message);
    }

}
