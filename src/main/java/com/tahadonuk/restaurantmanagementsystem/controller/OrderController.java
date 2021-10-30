package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.dto.DateInterval;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import com.tahadonuk.restaurantmanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "api/orders/create")
    @ResponseBody
    public ResponseEntity<HttpStatus> createOrder(@RequestBody Order order) {
        orderService.saveOrder(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "api/orders/{id}")
    @ResponseBody
    public ResponseEntity<Order> getById(@PathVariable long id) {
        ResponseEntity<Order> response;
        try {
            response = new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping(value = "api/orders/delete")
    @ResponseBody
    public ResponseEntity<Order> deleteOrder(@RequestParam("id") long id) {
        ResponseEntity<Order> response;
        try {
            orderService.deleteOrder(id);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        return response;
    }

    @GetMapping(value = "api/orders/stats")
    @ResponseBody
    public ResponseEntity<Integer> getStats() {
        // TODO: do the basic stats, assign it to a custom object and return it
        return null;
    }

    @PostMapping(value = "api/orders/interval")
    @ResponseBody
    public ResponseEntity<List<Order>> findByDateInterval(@RequestBody DateInterval dates) {
        return new ResponseEntity<>(orderService.getBetween(dates.getStartDate(), dates.getFinishDate()), HttpStatus.OK);
    }
}
