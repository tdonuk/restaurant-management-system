package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import com.tahadonuk.restaurantmanagementsystem.data.repository.TableRepository;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.Stats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.TableStats;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import com.tahadonuk.restaurantmanagementsystem.exception.TableConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TableService {

    @Autowired
    TableRepository tableRepo;

    public void addTable(RestaurantTable table) {
        if(isExists(table.getTableId())) {
            throw new TableConflictException("A table is already exists with given id: '" + table.getTableId() + "'");
        }
        else {
            tableRepo.save(table);
        }
    }

    public List<RestaurantTable> getAll() {
        List<RestaurantTable> list = tableRepo.findAll();
        list.sort(Comparator.comparingLong(RestaurantTable::getTableId));

        return list;
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

    public List<RestaurantTable> getAllByStatus(TableStatus status) {
        return tableRepo.findAllByStatus(status);
    }

    public int countByStatus(TableStatus status) {
        return tableRepo.countByStatus(status);
    }
    
    public Stats getStats() {
        TableStats tableStats = new TableStats();

        tableStats.setTotalCount(tableRepo.count());
        tableStats.setFullCount(countByStatus(TableStatus.FULL));
        tableStats.setAvailableCount(countByStatus(TableStatus.AVAILABLE));
        tableStats.setOutOfServiceCount(countByStatus(TableStatus.OUT_OF_SERVICE));

        return tableStats;
    }

    public boolean isExists(long id) {
        if(tableRepo.existsById(id)) {
            return true;
        }
        else return false;
    }
}
