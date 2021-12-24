package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.data.repository.OrderRepository;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.OrderStats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.Stats;
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
        orderRepo.save(order);
    }

    public List<Order> getBetween(Date date1, Date date2) throws NoSuchElementException {

        return orderRepo.findByOrderDateBetween(date1,date2);
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

    public List<Order> getOrdersFromDateUntilNow(Date date) {
        Date now = new Date();
        if(date.after(now)) {
            return new ArrayList<>();
        }
        return orderRepo.findByOrderDateBetween(date,  now);
    }

    public long countOrdersFromDateUntilNow(Date date) {
        Date now = new Date();
        if(date.after(now)) {
            return 0;
        }
        return orderRepo.countByOrderDateBetween(date,  now);
    }

    public Stats getStats() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        Date startOfToday = calendar.getTime();

        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-1);
        Date startOfYesterday = calendar.getTime();

        calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR)-1);
        calendar.set(Calendar.DAY_OF_WEEK,calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
        Date startOfCurrentWeek = calendar.getTime();

        calendar.set(Calendar.WEEK_OF_YEAR,calendar.get(Calendar.WEEK_OF_YEAR)-1);
        Date startOfTheLastWeek = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date startOfCurrentMonth = calendar.getTime();

        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
        Date startOfLastMonth = calendar.getTime();

        OrderStats orderStats = new OrderStats();

        orderStats.setTotalCount(orderRepo.count());
        orderStats.setOrderCountToday(countOrdersFromDateUntilNow(startOfToday));
        orderStats.setOrderCountYesterday(orderRepo.countByOrderDateBetween(startOfYesterday, startOfToday));
        orderStats.setOrderCountLastWeek(orderRepo.countByOrderDateBetween(startOfTheLastWeek, startOfCurrentWeek));
        orderStats.setOrderCountCurrentWeek(countOrdersFromDateUntilNow(startOfCurrentWeek));
        orderStats.setOrderCountLastMonth(orderRepo.countByOrderDateBetween(startOfLastMonth, startOfCurrentMonth));
        orderStats.setOrderCountCurrentMonth(countOrdersFromDateUntilNow(startOfCurrentMonth));

        return orderStats;
    }

    public int countByItem(final Item item) {
        return 1;
    }

}
