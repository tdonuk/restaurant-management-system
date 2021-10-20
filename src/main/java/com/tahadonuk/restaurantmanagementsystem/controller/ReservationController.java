package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Reservation;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Customer;
import com.tahadonuk.restaurantmanagementsystem.service.CustomerService;
import com.tahadonuk.restaurantmanagementsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReservationController {

    @Autowired
    ReservationService resService;

    @Autowired
    CustomerService customerService;

    @PostMapping(path = "reservations/create")
    @ResponseBody
    public ResponseEntity<HttpStatus> createReservation(@RequestBody Reservation reservation) {
        Customer customer = reservation.getCustomer();

        customerService.saveCustomer(customer);
        resService.saveReservation(reservation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "reservations/delete/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable long id) {
        resService.deleteReservation(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "reservations/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> getById(@PathVariable long id) {
        return new ResponseEntity<>(resService.getById(id), HttpStatus.OK);
    }

}
