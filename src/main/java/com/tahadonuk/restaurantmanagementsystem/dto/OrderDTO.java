package com.tahadonuk.restaurantmanagementsystem.dto;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private List<Item> items;
    private double totalPrice;
    private long tableId;
    private Date orderDate;
}
