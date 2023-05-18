package com.springframework.jmeteowebapp.exception;

public class CityAlreadyAddedException extends RuntimeException{
    public CityAlreadyAddedException(String message) {
        super(message);
    }
}
