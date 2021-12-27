package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "orders")
@Data
@Entity
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private long orderId;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

    @Column(name = "TABLE_ID")
    @NotNull
    private long tableId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RECEIPT_ID")
    private Receipt receipt;
}
