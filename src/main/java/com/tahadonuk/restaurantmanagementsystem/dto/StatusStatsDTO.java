package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusStatsDTO {
    private int fullCount;
    private int availableCount;
    private int outOfServiceCount;
    private int totalCount;
}
