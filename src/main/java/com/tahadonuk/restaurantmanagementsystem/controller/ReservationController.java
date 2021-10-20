package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Reservation;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Customer;
import com.tahadonuk.restaurantmanagementsystem.data.repository.CustomerRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    @Autowired
    ReservationRepository resRepo;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping(path = "reservation/create")
    public void createReservation(@RequestBody Reservation reservation) {
        Customer customer = reservation.getCustomer();

        customerRepository.save(customer);
        resRepo.save(reservation);

    }
}
