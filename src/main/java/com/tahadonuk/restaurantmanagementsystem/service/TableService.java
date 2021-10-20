package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import com.tahadonuk.restaurantmanagementsystem.data.repository.TableRepository;
import com.tahadonuk.restaurantmanagementsystem.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    @Autowired
    TableRepository tableRepo;

    public void addTable(RestaurantTable table) {
        tableRepo.save(table);
    }

    public List<RestaurantTable> getAll() {
        return tableRepo.findAll();
    }

    public List<RestaurantTable> getByCapacity(int capacity) {
        return ListUtils.getListFromOptionals(tableRepo.findByCapacity(capacity));
    }
}
