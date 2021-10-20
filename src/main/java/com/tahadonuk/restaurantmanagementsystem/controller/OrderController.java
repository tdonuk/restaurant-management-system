package com.tahadonuk.restaurantmanagementsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.OrderRepository;
import com.tahadonuk.restaurantmanagementsystem.service.OrderService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/orders/create")
    @ResponseBody
    public ResponseEntity<HttpStatus> createOrder(@RequestBody Order order) {
        orderService.saveOrder(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
