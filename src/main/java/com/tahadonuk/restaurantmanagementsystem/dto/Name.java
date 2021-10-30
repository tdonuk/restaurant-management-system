package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Name {
    String firstName;
    String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Name() {}
}
