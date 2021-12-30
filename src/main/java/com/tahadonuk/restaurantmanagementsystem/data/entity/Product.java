package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private long productId;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String description;

    @Column(name = "STOCK")
    private int stock = 0;

    @Column(name = "PRODUCT_PRICE")
    private double price;

    @Column(name = "PRODUCT_TYPE")
    private ItemType itemType;

    @Column(name = "STOCK_REQUIRED")
    private boolean isStockRequired = false;
}
