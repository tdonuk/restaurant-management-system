package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.repository.OrderRepository;
import com.tahadonuk.restaurantmanagementsystem.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepo;

    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    public List<Order> getBetween(Date date1, Date date2) throws NoSuchElementException {
        if(orderRepo.existsByOrderDateBetween(date1, date2)) {
            List<Optional<Order>> orderOptionals = orderRepo.findByOrderDateBetween(date1,date2);

            return ListUtils.getListFromOptionals(orderOptionals);
        }
        else return null;
    }

    public List<Order> getByDate(Date date) {
        if(orderRepo.existsByOrderDate(date)) {
            List<Optional<Order>> orderOptionals = orderRepo.findByOrderDate(date);

            return ListUtils.getListFromOptionals(orderOptionals);
        }
        else return null;
    }

    public Order getById(long id) {
        if(orderRepo.existsById(id)) {
            return orderRepo.findById(id).get();
        }
        else return null;
    }

}
