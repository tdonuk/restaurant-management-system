package com.tahadonuk.restaurantmanagementsystem.dto.stat;

import lombok.Data;

@Data
public class OrderStats extends Stats{
    private long orderCountToday;
    private long orderCountYesterday;
    private long orderCountCurrentWeek;
    private long orderCountLastWeek;
    private long orderCountLastMonth;
    private long orderCountCurrentMonth;
    private double cashToday;
    private double cashYesterday;
    private double cashCurrentWeek;
    private double cashCurrentMonth;
    private double cashLastWeek;
    private double cashLastMonth;

    public String getTodayPercentToYesterday() {
        if(orderCountYesterday == 0) return "+0%";

        double percent = ((double) (orderCountToday - orderCountYesterday) /  (double) orderCountYesterday) * 100;
        if(percent >= 0) {
            return "+" + String.format("%.2f",percent) + "%";
        } else {
            return String.format("%.2f", percent) + "%";
        }
    }

    public String getCurrentWeekRatioToCurrentMonth() {
        double percent = ((double) (orderCountCurrentWeek) /  (double) orderCountCurrentMonth) * 100;

        return String.format("%.2f", percent) + "%";
    }

    public String getLastWeekRatioToCurrentMonth() {
        double percent = ((double) (orderCountLastWeek) /  (double) orderCountCurrentMonth) * 100;

        return String.format("%.2f"+"%", percent);
    }

    public String getCurrentWeekRatioToLastWeek() {
        if(orderCountLastWeek == 0) return "+0%";

        double percent = ((double) (orderCountCurrentWeek - orderCountLastWeek) /  (double) orderCountLastWeek) * 100;
        if(percent >= 0) {
            return "+" + String.format("%.2f",percent) + "%";
        } else {
            return String.format("%.2f", percent) + "%";
        }
    }

    public String getCurrentMonthRatioToLastMonth() {
        if(orderCountLastMonth == 0) return "+0%";

        double percent = ((double) (orderCountCurrentMonth - orderCountLastMonth) /  (double) orderCountLastMonth) * 100;
        if(percent >= 0) {
            return "+" + String.format("%.2f",percent) + "%";
        } else {
            return String.format("%.2f", percent) + "%";
        }
    }
}
