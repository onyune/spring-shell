package com.nhnacademy.springshell.exception;

public class SectorNotFoundException extends RuntimeException {
    public SectorNotFoundException() {
        super("sector is not found");
    }
}
