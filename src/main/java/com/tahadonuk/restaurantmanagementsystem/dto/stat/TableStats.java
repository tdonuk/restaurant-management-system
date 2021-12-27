package com.tahadonuk.restaurantmanagementsystem.dto.stat;

import lombok.Data;

@Data
public class TableStats extends Stats{
    private int fullCount;
    private int availableCount;
    private int outOfServiceCount;

    public double getAvailablePercent() {
        double percent = ( (double) this.availableCount /  (double) getTotalCount()) * 100;

        return percent;
    }

    public double getOutOfServicePercent() {
        double percent = ( (double) this.outOfServiceCount /  (double) getTotalCount()) * 100;

        return percent;
    }

    public double getFullPercent() {
        double percent = ( (double) this.fullCount /  (double) getTotalCount()) * 100;

        return percent;
    }
}
