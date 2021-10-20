package com.tahadonuk.restaurantmanagementsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.OrderRepository;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    ItemRepository itemRepo;

    @Autowired
    OrderRepository orderRepo;

    @PostMapping(value = "/order/create")
    public void createOrder(@RequestBody Order order) throws ParseException, JsonProcessingException {
        System.out.println("----------------------");

        orderRepo.save(order);
    }
}
