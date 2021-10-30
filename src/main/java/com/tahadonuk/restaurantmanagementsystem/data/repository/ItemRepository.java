package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByItemType(ItemType type);
    boolean existsByItemType(ItemType type);

    List<Item> findAllByName(String  name);
    boolean existsByName(String name);

    boolean existsByNameAndDescription(String name, String description);
}
