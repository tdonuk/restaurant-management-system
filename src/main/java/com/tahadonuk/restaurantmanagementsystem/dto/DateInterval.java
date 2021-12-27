package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DateInterval {
    private Date startDate;
    private Date endDate;

    public DateInterval() {}

    public DateInterval(Date startDate, Date finishDate) {
        this.startDate = startDate;
        this.endDate = finishDate;
     }
}
