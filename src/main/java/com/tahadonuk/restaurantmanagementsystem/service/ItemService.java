package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.entity.OrderItem;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.dto.ItemDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepo;

    public void saveItem(Item item) throws Exception{
        if(isExists(item.getName())) {
            throw new ItemConflictException("An item with given info for '" + item.getName() + "' is already exists");
        }
        else {
            itemRepo.save(item);
        }
    }

    public Set<OrderItem> getOrderItems(List<Item> items) {
        Set<OrderItem> orderItems = new HashSet<>();

        OrderItem orderItem;
        for(Item item : items) {
            orderItem = new OrderItem();
            orderItem.setName(item.getName());
            orderItem.setPrice(item.getPrice());
            orderItem.setItemId(item.getItemId());

            orderItems.add(orderItem);
        }

        return orderItems;
    }

    public void handleStocks(List<Item> items) throws Exception{
        for (Item item : items) {
            int countOfCurrentItem = items.stream().parallel().filter(e -> e.getItemId() == item.getItemId()).collect(Collectors.toList()).size();
            if(countOfCurrentItem > item.getStock()) {
                throw new ItemOutOfStockException(item.getName()+" is out of stocks");
            }
            else {
                updateItemStockValue(item.getItemId(), -1);
            }
        }
    }

    public Item saveItemFromData(ItemDTO item) {
        Item itemEntity = new Item();

        itemEntity.setName(item.getName());
        itemEntity.setDescription(item.getDescription());
        itemEntity.setPrice(item.getPrice());
        itemEntity.setItemType(ItemType.valueOf(item.getType().toUpperCase()));
        itemEntity.setStock(item.getStock());

        itemRepo.save(itemEntity);

        return itemEntity;
    }

    public Item updateItemStockValue(long itemId, int amount) {
        if(isExists(itemId)) {
            itemRepo.updateStock(itemId, amount);
            return itemRepo.findById(itemId).get();
        }
        else {
            throw new ItemNotFoundException("No item found with given ID: "+itemId);
        }
    }

    public List<Item> getAll() {
        return itemRepo.findAll();
    }

    public Item getItemById(long id) {
        if(isExists(id)) {
            return itemRepo.findById(id).get();
        }
        else throw new ItemNotFoundException("No such item with id '" + id + "'");
    }

    public List<Item> getItemsByName(String name) {
        List<Item> items = itemRepo.findAllByName(name);

        return items;
    }

    public void deleteItem(long id) {
        if(isExists(id)) {
            itemRepo.deleteById(id);
        } else {
            throw new ItemNotFoundException("No item found with given ID: "+id);
        }
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
