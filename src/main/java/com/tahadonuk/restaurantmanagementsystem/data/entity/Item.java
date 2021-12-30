package com.tahadonuk.restaurantmanagementsystem.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private long itemId;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    @Column(name = "QUANTITY")
    private int quantity;
}
