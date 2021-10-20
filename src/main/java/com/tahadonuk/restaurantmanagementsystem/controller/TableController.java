package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import com.tahadonuk.restaurantmanagementsystem.data.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableController {

    @Autowired
    TableRepository tableRepo;

    @PostMapping(path = "tables/add")
    public void addTable(@RequestBody RestaurantTable table) {
        System.out.println("-------TABLES----------");
        System.out.println(table.getTableId());
        System.out.println(table.getCapacity());
        tableRepo.save(table);
    }
}
