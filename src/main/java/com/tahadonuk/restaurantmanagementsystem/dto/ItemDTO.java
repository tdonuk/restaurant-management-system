package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
public class ItemDTO {
    private String name;
    private String description;
    private String type;
    private double price;
    private int stock;
}
