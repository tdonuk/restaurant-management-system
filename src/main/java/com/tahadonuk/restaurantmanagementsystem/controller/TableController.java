package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import com.tahadonuk.restaurantmanagementsystem.data.repository.TableRepository;
import com.tahadonuk.restaurantmanagementsystem.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TableController {

    @Autowired
    TableService tableService;

    @PostMapping(path = "tables/add")
    @ResponseBody
    public ResponseEntity<HttpStatus> addTable(@RequestBody RestaurantTable table) {
        tableService.addTable(table);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "tables")
    @ResponseBody
    public ResponseEntity<List<RestaurantTable>> getTables() {
        return new ResponseEntity<>(tableService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "tables/bycap/{capacity}")
    @ResponseBody
    public ResponseEntity<List<RestaurantTable>> getByCapacity(@PathVariable int capacity) {
        return new ResponseEntity<>(tableService.getByCapacity(capacity), HttpStatus.OK);
    }
}
