package com.tahadonuk.restaurantmanagementsystem.data.entity.user;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Name {
    String firstName;
    String lastName;
}
