package com.tahadonuk.restaurantmanagementsystem.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber {
    private String title;
    private String number;
}
