package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Table(name = "orders")
@Data
@Entity
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private long orderId;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
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
