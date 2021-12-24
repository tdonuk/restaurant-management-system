package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemType(ItemType type);
    boolean existsByItemType(ItemType type);

    List<Item> findAllByName(String  name);
    boolean existsByName(String name);

    int countByItemType(ItemType itemType);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Item item set item.stock = item.stock + :amount where item.itemId = :itemId")
    void updateStock(@Param("itemId") long itemId, @Param("amount") int amount); // amount : stock amount to update. it can be negative

    boolean existsByNameAndDescription(String name, String description);
}
