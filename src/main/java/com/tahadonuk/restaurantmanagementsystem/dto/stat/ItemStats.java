package com.tahadonuk.restaurantmanagementsystem.dto.stat;

import lombok.Data;

@Data
public class ItemStats extends Stats{
    private int beverageCount;
    private int totalBeverageStocks;

    private int mealCount;
    private int totalMealStocks;

    private int dessertCount;
    private int totalDessertStocks;

    public double getBeveragePercent() {
        double percent = ((double) beverageCount / (double) getTotalCount()) * 100;

        return percent;
    }
    public double getMealPercent() {
        double percent = ((double) mealCount / (double) getTotalCount()) * 100;

        return percent;
    }
    public double getDessertPercent() {
        double percent = ((double) dessertCount / (double) getTotalCount()) * 100;

        return percent;
    }

    public double getTotalStocks() {
        int stocks = totalBeverageStocks + totalDessertStocks + totalMealStocks;
        return stocks;
    }
}
