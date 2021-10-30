package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Customer;
import com.tahadonuk.restaurantmanagementsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping(path = "api/customer/{id}")
    @ResponseBody
    public ResponseEntity<Customer> getById(@PathVariable long id) {
        ResponseEntity<Customer> response;
        try {
            response = new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping(path = "api/customer/delete")
    @ResponseBody
    public ResponseEntity<Customer> deleteCustomer(@RequestParam("id") long id) {
        ResponseEntity<Customer> response;
        try {
            customerService.deleteCustomer(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
