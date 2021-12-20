package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableStatsDTO {
    private int fullCount;
    private int availableCount;
    private int outOfServiceCount;
    private int totalCount;

    public double getAvailablePercent() {
        double percent = ( (double) this.availableCount /  (double) this.totalCount) * 100;

        return percent;
    }

    public double getOutOfServicePercent() {
        double percent = ( (double) this.outOfServiceCount /  (double) this.totalCount) * 100;

        return percent;
    }

    public double getFullPercent() {
        double percent = ( (double) this.fullCount /  (double) this.totalCount) * 100;

        return percent;
    }
}
