package com.nhnacademy.springshell.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() {
        super("city is not found");
    }
}
