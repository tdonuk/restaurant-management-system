package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Data
public class intervalDatesHolder {
    private Date startDate;
    private Date finishDate;
    private LocalDate start;
    private LocalDate end;
    private Period period;

     public intervalDatesHolder(Date startDate, Date finishDate) {
         this.startDate = startDate;
         this.finishDate = finishDate;

         start = LocalDate.of(startDate.getYear(), startDate.getMonth(),startDate.getDate());
         end = LocalDate.of(finishDate.getYear(), finishDate.getMonth(),finishDate.getDate());

         period = Period.between(start, end);
     }


}
