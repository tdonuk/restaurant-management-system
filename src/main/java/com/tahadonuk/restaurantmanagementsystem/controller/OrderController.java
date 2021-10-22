package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.entity.item.Item;
import com.tahadonuk.restaurantmanagementsystem.dto.intervalDatesHolder;
import com.tahadonuk.restaurantmanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

    @GetMapping(value = "/orders/{id}")
    @ResponseBody
    public ResponseEntity<Order> getById(@PathVariable long id) {
        return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/orders/{id}/delete")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/orders/count")
    @ResponseBody
    public ResponseEntity<Integer> countByItem(@RequestBody Item item) {
        return new ResponseEntity<>(orderService.count(item), HttpStatus.OK);
    }

    @PostMapping(value = "/orders/interval")
    @ResponseBody
    public ResponseEntity<List<Order>> findByDateInterval(@RequestBody intervalDatesHolder dates) {
        return new ResponseEntity<>(orderService.getBetween(dates.getStartDate(), dates.getFinishDate()), HttpStatus.OK);
    }
}
