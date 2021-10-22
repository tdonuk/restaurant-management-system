package com.tahadonuk.restaurantmanagementsystem.data.entity;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.customer.Customer;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private long reservationId;

    @JoinColumn(name = "CUSTOMER_ID")
    @OneToOne(fetch = FetchType.EAGER)
    private Customer customer; // customer who created the reservation request

    @Column(name = "REQUEST_DATE")
    private Date proccessDate; // request date

    @Column(name = "RESERVATION_DATE")
    private Date reservationDate; // reservation date

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "TABLE_ID"),
    })
    private RestaurantTable table;
}
