package com.tahadonuk.restaurantmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemDTO {
    private String name;
    private String description;
    private String type;
    private double price;
    private int stock;
    @JsonProperty
    private boolean isStockRequired;
}
