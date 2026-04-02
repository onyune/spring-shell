package com.nhnacademy.springshell.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("id or password not correct");
    }
}
