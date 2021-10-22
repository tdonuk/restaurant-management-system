package com.tahadonuk.restaurantmanagementsystem.data.entity.user.employee;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    private String street;
    private String address;
}
