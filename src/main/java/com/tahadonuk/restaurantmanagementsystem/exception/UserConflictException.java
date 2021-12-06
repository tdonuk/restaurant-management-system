package com.tahadonuk.restaurantmanagementsystem.exception;

public class UserConflictException extends ConflictException{
    public UserConflictException(String message) {
        super(message);
    }
}
