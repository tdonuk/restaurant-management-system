package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.dto.DateInterval;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import com.tahadonuk.restaurantmanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "api/order/create")
    @ResponseBody
    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
        orderService.saveOrder(order);

        return ResponseEntity.ok(new StringResponse("Order has successfully created. Order Id: "+ order.getOrderId() + "."));
    }

    @GetMapping(value = "api/order/{id}")
    @ResponseBody
    public ResponseEntity<Object> getById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(orderService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @DeleteMapping(value = "api/order")
    @ResponseBody
    public ResponseEntity<Object> deleteOrder(@RequestParam("id") long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok(new StringResponse("Order "+id+" has deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(value = "api/orders/stats")
    @ResponseBody
    public ResponseEntity<Object> getStats() {
        try {
            // TODO
            return null;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(value = "api/orders/interval")
    @ResponseBody
    public ResponseEntity<List<Order>> findByDateInterval(@RequestBody DateInterval dates) {
        return ResponseEntity.ok(orderService.getBetween(dates.getStartDate(), dates.getFinishDate()));
    }
}
