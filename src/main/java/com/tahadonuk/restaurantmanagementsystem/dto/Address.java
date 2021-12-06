package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    private String street;
    private String apartment;

    public Address(String street, String apartment) {
        this.street = street;
        this.apartment = apartment;
    }

    public Address() {

    }
}
