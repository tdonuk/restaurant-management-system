package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserStatsDTO {
    private int totalCount;
    private int userCount;
    private int employeeCount;
    private int managerCount;
    private int adminCount;

    public double getUserPercent() {
        double percent = ((double) userCount / (double) totalCount) * 100;

        return percent;
    }

    public double getEmployeePercent() {
        double percent = ((double) employeeCount / (double) totalCount) * 100;

        return percent;
    }

    public double getManagerPercent() {
        double percent = ((double) managerCount / (double) totalCount) * 100;

        return percent;
    }

    public double getAdminPercent() {
        double percent = ((double) adminCount / (double) totalCount) * 100;

        return percent;
    }
}
