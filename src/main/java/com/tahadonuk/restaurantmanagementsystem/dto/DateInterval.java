package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;
import org.thymeleaf.util.DateUtils;

import java.time.LocalDate;
import java.time.Period;
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
