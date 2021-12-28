package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findReceiptByOrderId(long orderId);

    @Query("select receipt from Receipt receipt join fetch receipt.items as item where item.name = :itemName")
    List<Receipt> getOrdersByItemsContains(@Param("itemName") String itemId);

}
