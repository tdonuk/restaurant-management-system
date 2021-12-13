package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.exception.TableNotFoundException;
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

    @PostMapping(path = "api/table/save")
    @ResponseBody
    public ResponseEntity<Object> addTable(@RequestBody RestaurantTable table) {
        if(table.getTableId() <= 0) return ResponseEntity.badRequest().body("Given ID value is not valid.");

        try{
            table.setStatus(TableStatus.AVAILABLE);

            tableService.addTable(table);
            return ResponseEntity.ok().body(new StringResponse("Table " + table.getTableId() + " has created successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/table/{id}")
    @ResponseBody
    public ResponseEntity<Object> getById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(tableService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/table")
    @ResponseBody
    public ResponseEntity<List<RestaurantTable>> getTables() {
        return new ResponseEntity<>(tableService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "api/table/cap")
    @ResponseBody
    public ResponseEntity<List<RestaurantTable>> getByCapacity(@RequestParam("capacity") int capacity) {
        return new ResponseEntity<>(tableService.getByCapacity(capacity), HttpStatus.OK);
    }

    @PostMapping(path = "api/table/{id}/status")
    @ResponseBody
    public ResponseEntity<Object> setStatus(@RequestBody String statusString, @PathVariable long id) {
        TableStatus status = TableStatus.valueOf(statusString.toUpperCase().replaceAll(" ", "_"));

        try {
            tableService.updateTableStatus(status, id);
            return ResponseEntity.ok(new StringResponse("Table status has updated successfully"));
        } catch (TableNotFoundException e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }
}
