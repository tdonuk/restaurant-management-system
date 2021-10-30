package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Email {
    private String email;

    public Email(String email) {
        this.email = email;
    }

    public Email() {}

}
