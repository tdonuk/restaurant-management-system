package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import com.tahadonuk.restaurantmanagementsystem.data.repository.TableRepository;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
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
        return tableRepo.findByCapacity(capacity);
    }

    public RestaurantTable getById(long id) throws NotFoundException {
        if(isExists(id)) {
            return tableRepo.findById(id).get();
        }
        else throw new NotFoundException("No such table with given ID");
    }

    public void updateTableStatus(TableStatus status, long tableId) throws NotFoundException {
        if(isExists(tableId)) {
            tableRepo.updateTableStatus(tableId, status);
        }
        else throw new NotFoundException("No such table with given ID");
    }

    public boolean isExists(long id) {
        if(tableRepo.existsById(id)) {
            return true;
        }
        else return false;
    }
}
