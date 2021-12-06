package com.tahadonuk.restaurantmanagementsystem.exception;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
