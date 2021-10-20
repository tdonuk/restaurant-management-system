package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.sun.istack.NotNull;
import com.tahadonuk.restaurantmanagementsystem.data.entity.item.Item;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "orders")
@Data
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private long orderId;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "item_entity", joinColumns = @JoinColumn(name = "ITEM_ID"))
    private Set<Item> items;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

    @NotNull
    @JoinColumn(name = "TABLE_ID")
    @OneToOne(fetch = FetchType.EAGER)
    private RestaurantTable table;
}
