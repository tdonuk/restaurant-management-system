package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.sun.istack.NotNull;
import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "items")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private long itemId;

    @NotNull
    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "ITEM_DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "ITEM_PRICE")
    private double price;

    @Column(name = "ITEM_TYPE")
    @NotNull
    private ItemType itemType;
}
