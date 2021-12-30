package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class DateInterval {
    private LocalDate startDate;
    private LocalDate endDate;

    public DateInterval() {}

    public DateInterval(LocalDate startDate, LocalDate finishDate) {
        this.startDate = startDate;
        this.endDate = finishDate;
     }
}
