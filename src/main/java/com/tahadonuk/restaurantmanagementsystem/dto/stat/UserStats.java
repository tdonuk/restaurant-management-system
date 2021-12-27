package com.tahadonuk.restaurantmanagementsystem.dto.stat;

import lombok.Data;

@Data
public class UserStats extends Stats{
    private int userCount;
    private int employeeCount;
    private int managerCount;
    private int adminCount;

    public double getUserPercent() {
        double percent = ((double) userCount / (double) getTotalCount()) * 100;

        return percent;
    }

    public double getEmployeePercent() {
        double percent = ((double) employeeCount / (double) getTotalCount()) * 100;

        return percent;
    }

    public double getManagerPercent() {
        double percent = ((double) managerCount / (double) getTotalCount()) * 100;

        return percent;
    }

    public double getAdminPercent() {
        double percent = ((double) adminCount / (double) getTotalCount()) * 100;

        return percent;
    }
}
