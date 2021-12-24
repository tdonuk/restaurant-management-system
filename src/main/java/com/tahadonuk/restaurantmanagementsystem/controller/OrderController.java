package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Receipt;
import com.tahadonuk.restaurantmanagementsystem.dto.DateInterval;
import com.tahadonuk.restaurantmanagementsystem.dto.OrderDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.ItemService;
import com.tahadonuk.restaurantmanagementsystem.service.OrderService;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import com.tahadonuk.restaurantmanagementsystem.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ItemService itemService;

    @PostMapping(value = "api/order/save")
    @ResponseBody
    public ResponseEntity<Object> createOrder(@RequestBody OrderDTO orderData) {
        try {
            Order order = new Order();
            
            itemService.handleStocks(orderData.getItems());

            Receipt receipt = new Receipt(itemService.getOrderItems(orderData.getItems()));
            receipt.setTotalPrice(orderData.getTotalPrice());

            order.setReceipt(receipt);

            order.setTotalPrice(orderData.getTotalPrice());
            order.setTableId(orderData.getTableId());
            order.setOrderDate(orderData.getOrderDate());

            orderService.saveOrder(order);

            return ResponseEntity.ok(new StringResponse("Order has successfully created. Order Id: "+ order.getOrderId() + "."));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
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

    @GetMapping(value = "api/order/{id}/details")
    @ResponseBody
    public Object getOrderDetail(@PathVariable long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            UserDTO requestingUserData = UserUtils.getUserData(userService, request.getRemoteUser());

            Order orderDetails = orderService.getById(id);

            mav.getModel().put("orderDetails", orderDetails);
            mav.getModel().put("user",requestingUserData);
            mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

            mav.setViewName("app/details/order_details");
            return mav;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }


    @DeleteMapping(value = "api/order/{id}")
    @ResponseBody
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") long id) {
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
