package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long> {
     List<RestaurantTable> findByCapacity(int capacity);

     @Modifying(clearAutomatically = true)
     @Query("update RestaurantTable table set table.status = :status where table.tableId = :status")
     void updateTableStatus(@Param("tableId") long tableId, @Param("status") TableStatus status);
}
