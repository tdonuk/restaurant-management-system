package com.tahadonuk.restaurantmanagementsystem.data.entity;

import lombok.Data;

import javax.persistence.*;


@Embeddable
@Data
public class OrderItem {
    private long itemId;
    private String name;
    private double price;
}
