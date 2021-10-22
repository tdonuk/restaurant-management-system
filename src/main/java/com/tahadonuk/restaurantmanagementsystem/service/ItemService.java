package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.item.Item;
import com.tahadonuk.restaurantmanagementsystem.data.entity.item.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepo;

    public void saveItem(Item item) {
        itemRepo.save(item);
    }

    public Item getItemById(long id) {
        return itemRepo.findById(id).orElse(null);
    }

    public List<Item> findByName(String name) throws NoSuchElementException {
        if(itemRepo.existsByNameContains(name)) {
            List<Item> items = itemRepo.findAllByNameContains(name);

            return items;
        }
        else return null;
    }

    public List<Item> getByType(ItemType type) throws NoSuchElementException {
        if(itemRepo.existsByItemType(type)) {
            List<Item> items = itemRepo.findByItemType(type);

            return items;
        }
        else return null;
    }
}
