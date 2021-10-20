package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long> {
     List<Optional<RestaurantTable>> findByCapacity(int capacity);
}