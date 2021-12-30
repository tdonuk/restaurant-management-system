package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByItemType(ItemType type);
    boolean existsByItemType(ItemType type);

    List<Product> findAllByName(String  name);
    boolean existsByName(String name);

    int countByItemType(ItemType itemType);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Product item set item.stock = item.stock + :amount where item.productId = :itemId")
    void updateStock(@Param("itemId") long itemId, @Param("amount") int amount); // amount : stock amount to update. it can be negative

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Product item set item.price = :newPrice where item.productId = :itemId")
    void updatePrice(@Param("itemId") long itemId, @Param("newPrice") double newPrice);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Product item set item.description = :description where item.productId = :itemId")
    void updateDescription(@Param("itemId") long itemId, @Param("description") String description);
}
