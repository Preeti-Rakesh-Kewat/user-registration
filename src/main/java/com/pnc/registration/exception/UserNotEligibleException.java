package com.pnc.registration.exception;

public class UserNotEligibleException extends RuntimeException{
    public UserNotEligibleException(String message){
        super(message);
    }
}
