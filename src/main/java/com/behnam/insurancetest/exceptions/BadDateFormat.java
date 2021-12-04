package com.behnam.insurancetest.exceptions;

public class BadDateFormat extends RuntimeException{
    public BadDateFormat(String message){
        super(message);
    }
}
