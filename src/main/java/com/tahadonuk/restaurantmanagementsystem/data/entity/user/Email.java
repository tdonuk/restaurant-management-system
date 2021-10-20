package com.tahadonuk.restaurantmanagementsystem.data.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Email {
    private String email;

    public Email() {}

    public Email(String email) {
        this.email = email;
    }
}
