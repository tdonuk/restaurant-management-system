package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.sun.istack.NotNull;
import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tables")
@Data
public class RestaurantTable implements Serializable {
    @NotNull
    @Id
    @Column(name = "TABLE_ID")
    private long tableId;

    @Column(name = "TABLE_CAPACITY")
    private int capacity;

    @Column(name = "TABLE_STATUS")
    private TableStatus status;
}
