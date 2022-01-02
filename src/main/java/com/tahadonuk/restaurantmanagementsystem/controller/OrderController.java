package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Product;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.dto.DateInterval;
import com.tahadonuk.restaurantmanagementsystem.dto.OrderDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.ProductService;
import com.tahadonuk.restaurantmanagementsystem.service.OrderService;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import com.tahadonuk.restaurantmanagementsystem.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;


    @PostMapping(value = "api/order/save")
    @ResponseBody
    public ResponseEntity<Object> createOrder(@RequestBody OrderDTO orderData) {
        try {

            Order savedOrder = orderService.saveOrderFromData(orderData);

            return ResponseEntity.ok(new StringResponse("Order has successfully created. Order Id: "+ savedOrder.getOrderId() + "."));
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

            mav.setViewName("app/details/order_details");
            return mav;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(value = "api/order/{id}/items")
    @ResponseBody
    public Object addItemToOrder(@PathVariable long id, @RequestBody List<Product> items, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            Order orderDetails = orderService.getById(id);

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

    @GetMapping(value = "api/order/stats")
    @ResponseBody
    public ResponseEntity<Object> getStats() {
        try {
            // TODO
            return null;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(value = "api/order/interval")
    public ModelAndView findByDateInterval(@RequestBody DateInterval dates, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("app/orders");

        mav.getModel().put("orders",orderService.getOrdersByInterval(dates.getStartDate(), dates.getEndDate()));
        mav.getModel().put("user",userService.getUserFromEntity(userService.getUserByEmail(request.getRemoteUser())));

        return mav;
    }
}
