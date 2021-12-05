package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long> {
     List<RestaurantTable> findByCapacity(int capacity);

     @Transactional
     @Modifying(clearAutomatically = true)
     @Query("update RestaurantTable table set table.status = :status where table.tableId = :tableId")
     void updateTableStatus(@Param("tableId") long tableId, @Param("status") TableStatus status);

     List<RestaurantTable> findAllByStatus(TableStatus status);

     int countByStatus(TableStatus status);
}
