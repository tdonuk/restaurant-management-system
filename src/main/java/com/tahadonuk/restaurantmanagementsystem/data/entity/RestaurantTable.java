package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.sun.istack.NotNull;
import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tables")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
