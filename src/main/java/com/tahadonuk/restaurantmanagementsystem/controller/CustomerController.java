package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Customer;
import com.tahadonuk.restaurantmanagementsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping(path = "customer/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) {
        return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
    }

    @GetMapping(path = "customer/delete/{id}")
    @ResponseBody
    public ResponseEntity<Customer> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
