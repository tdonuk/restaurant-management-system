package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.OrderRepository;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import com.tahadonuk.restaurantmanagementsystem.util.ListUtils;
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
        List<Optional<Order>> orderOptionals = orderRepo.findByOrderDateBetween(date1,date2);

        return ListUtils.getListFromOptionals(orderOptionals);
    }

    public List<Order> getByDate(Date date) {
        if(orderRepo.existsByOrderDate(date)) {
            List<Optional<Order>> orderOptionals = orderRepo.findByOrderDate(date);

            return ListUtils.getListFromOptionals(orderOptionals);
        }
        else return null;
    }

    public Order getById(long id) throws NotFoundException {
        if(orderRepo.existsById(id)) {
            return orderRepo.findById(id).get();
        }
        else throw new NotFoundException("No such order with given id");
    }

    public void deleteOrder(long id) throws NotFoundException {
        if(orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
        }
        else throw new NotFoundException("No such order with given id");
    }

    public int count(final Item items) {
        return orderRepo.countAllByItemsIsContaining(items);
    }

}
