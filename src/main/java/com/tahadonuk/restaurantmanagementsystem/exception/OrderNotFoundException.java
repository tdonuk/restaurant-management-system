package com.tahadonuk.restaurantmanagementsystem.exception;

public class OrderNotFoundException extends NotFoundException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
