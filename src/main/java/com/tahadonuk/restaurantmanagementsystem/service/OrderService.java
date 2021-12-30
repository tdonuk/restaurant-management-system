package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.OrderRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ProductRepository;
import com.tahadonuk.restaurantmanagementsystem.dto.OrderDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.OrderItemDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.OrderStats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.Stats;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import com.tahadonuk.restaurantmanagementsystem.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    TableService tableService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EntityManager em;

    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    @Transactional
    public Order saveOrderFromData(OrderDTO orderData) {
        try {
            Set<Item> orderItems = new HashSet<>();
            List<OrderItemDTO> rawItems = orderData.getItems();

            Order order = new Order();
            double totalPrice = 0;

            Item item;
            for (OrderItemDTO itemDTO : rawItems) {
                item = new Item();

                item.setProduct(itemDTO.getProduct());
                item.setQuantity(itemDTO.getQuantity());
                item.setOrder(order);

                totalPrice = totalPrice + item.getProduct().getPrice();

                orderItems.add(item);
            }

            productService.handleStocks(orderItems);

            order.setTotalPrice(totalPrice);
            order.setTable(orderData.getTable());
            order.setOrderDate(LocalDateTime.ofInstant(orderData.getOrderDate().toInstant(), ZoneId.systemDefault()));
            order.setItems(orderItems);

            em.persist(order);

            saveOrder(order);

            return order;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public Order getById(long id) throws NotFoundException {
        if(orderRepo.existsById(id)) {
            return orderRepo.findById(id).get();
        }
        else throw new OrderNotFoundException("No such order with id: '" + id + "'");
    }

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public void deleteOrder(long id) throws NotFoundException {
        if(orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
        }
        else throw new OrderNotFoundException("No such order with id: '" + id + "'");
    }

    public List<Order> getOrdersByInterval(LocalDate start, LocalDate end) {
        if(end.isAfter(LocalDate.now())) {
            return new ArrayList<>();
        }
        return orderRepo.findByOrderDateBetween(LocalDateTime.of(start, LocalTime.MIDNIGHT),  LocalDateTime.of(end, LocalTime.MIDNIGHT));
    }
    public long countOrdersByInterval(LocalDate start, LocalDate end) {
        if(end.isAfter(LocalDate.now())) {
            return 0;
        }
        return orderRepo.countByOrderDateBetween(LocalDateTime.of(start, LocalTime.MIDNIGHT),  LocalDateTime.of(end,LocalTime.MIDNIGHT));
    }

    public long countOrdersFromDateUntilNow(LocalDate date) {
        if(date.isAfter(LocalDate.now())) {
            return 0;
        }
        return orderRepo.countByOrderDateBetween(LocalDateTime.of(date, LocalTime.MIDNIGHT),  LocalDateTime.now());
    }

    public Stats getStats() {
        LocalDate startOfToday = LocalDateTime.now(ZoneId.systemDefault()).toLocalDate();

        LocalDate startOfYesterday = startOfToday.minusDays(1);
        LocalDate startOfCurrentWeek = startOfToday.with(ChronoField.DAY_OF_WEEK, 1);
        LocalDate startOfCurrentMonth = startOfToday.withDayOfMonth(1);
        LocalDate startOfLastWeek = startOfToday.with(DayOfWeek.MONDAY).minusWeeks(1);
        LocalDate startOfLastMonth = startOfToday.withDayOfMonth(1).minusMonths(1);

        OrderStats orderStats = new OrderStats();

        orderStats.setTotalCount(orderRepo.count());

        orderStats.setOrderCountToday(countOrdersFromDateUntilNow(startOfToday));
        orderStats.setOrderCountYesterday(countOrdersByInterval(startOfYesterday, startOfToday));

        orderStats.setOrderCountCurrentMonth(countOrdersFromDateUntilNow(startOfCurrentMonth));
        orderStats.setOrderCountCurrentWeek(countOrdersFromDateUntilNow(startOfCurrentWeek));

        orderStats.setOrderCountLastWeek(countOrdersByInterval(startOfLastWeek, startOfCurrentWeek));
        orderStats.setOrderCountLastMonth(countOrdersByInterval(startOfLastMonth, startOfCurrentMonth));


        return orderStats;
    }

    public List<Order> getOrdersItemsContains(String name) {
        List<Item> itemsWithName = itemRepository.findAllByProduct_Name(name);

        List<Order> orders = orderRepo.findOrdersByItemsContaining(itemsWithName.get(0));

        orders.sort(Comparator.comparing(Order::getOrderDate));

        return orders;
    }

}
