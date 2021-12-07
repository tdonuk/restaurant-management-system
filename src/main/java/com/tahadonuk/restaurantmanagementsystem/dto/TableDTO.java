package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;

@Data
public class TableDTO {
    private long tableId;

    private int capacity;

    private String status;
}
