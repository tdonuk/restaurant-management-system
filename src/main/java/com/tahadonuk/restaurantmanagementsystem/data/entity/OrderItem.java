package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private long orderItemId;

    @Column(name = "ITEM_ID")
    private long itemId;

    @NotNull
    @Column(name = "ORDER_ITEM_NAME")
    private String name;

    @NotNull
    @Column(name = "ORDER_ITEM_PRICE")
    private double price;
}
