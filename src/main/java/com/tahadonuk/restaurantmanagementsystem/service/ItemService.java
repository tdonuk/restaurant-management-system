package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.exception.ConflictException;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepo;

    public void saveItem(Item item) throws Exception{
        if(isExists(item.getName(), item.getDescription())) {
            throw new ConflictException("An item with given name and description [ " + item.getName() + ", " + item.getDescription() + " ] is already exists");
        }
        else {
            itemRepo.save(item);
        }
    }

    public Item getItemById(long id) throws Exception{
        if(isExists(id)) {
            return itemRepo.findById(id).get();
        }
        else throw new NotFoundException("No such item with given id");
    }

    public List<Item> getItemsByName(String name) {
        List<Item> items = itemRepo.findAllByName(name);

        return items;
    }

    public List<Item> getByType(ItemType type) {
        List<Item> items = itemRepo.findByItemType(type);

        return items;
    }


    //id
    public boolean isExists(long id) {
        if (itemRepo.existsById(id)) return true;
        else return false;
    }
    //name
    public boolean isExists(String name) {
        if(itemRepo.existsByName(name)) {
            return true;
        }
        else return false;
    }
    //name and description (no duplicate allowed)
    public boolean isExists(String name, String description) {
        if (itemRepo.existsByNameAndDescription(name, description)) return true;
        else return false;
    }

    //type
    public boolean isExists(ItemType type) {
        if(itemRepo.existsByItemType(type)) {
            return true;
        }
        else return false;
    }
}
