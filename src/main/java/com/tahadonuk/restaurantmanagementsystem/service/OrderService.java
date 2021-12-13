package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.OrderRepository;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import com.tahadonuk.restaurantmanagementsystem.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepo;

    @Autowired
    ItemRepository itemRepository;

    public void saveOrder(Order order) {
        double price = 0;

        for (Item item : order.getItems()) {
            item = itemRepository.getById(item.getItemId());
            price += item.getPrice();
        }

        order.setTotalPrice(price);

        orderRepo.save(order);
    }

    public List<Order> getBetween(Date date1, Date date2) throws NoSuchElementException {

        return orderRepo.findByOrderDateBetween(date1,date2);
    }

    public List<Order> getByDate(Date date) {
        if(orderRepo.existsByOrderDate(date)) {

            return orderRepo.findByOrderDate(date);
        }
        else return null;
    }

    public Order getById(long id) throws NotFoundException {
        if(orderRepo.existsById(id)) {
            return orderRepo.findById(id).get();
        }
        else throw new OrderNotFoundException("No such order with id: '" + id + "'");
    }

    public void deleteOrder(long id) throws NotFoundException {
        if(orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
        }
        else throw new OrderNotFoundException("No such order with id: '" + id + "'");
    }

    public int countByItem(final Item item) {
        return orderRepo.countAllByItemsIsContaining(item);
    }

}
