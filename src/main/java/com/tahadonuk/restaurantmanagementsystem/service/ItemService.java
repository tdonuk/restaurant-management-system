package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.entity.OrderItem;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import com.tahadonuk.restaurantmanagementsystem.dto.ItemDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.ItemStats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.Stats;
import com.tahadonuk.restaurantmanagementsystem.exception.ItemConflictException;
import com.tahadonuk.restaurantmanagementsystem.exception.ItemNotFoundException;
import com.tahadonuk.restaurantmanagementsystem.exception.ItemOutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepo;

    public void saveItem(Item item) throws ItemConflictException{
        if(isExists(item.getName())) {
            throw new ItemConflictException("An item with given info for '" + item.getName() + "' is already exists");
        }
        else {
            itemRepo.save(item);
        }
    }

    public List<OrderItem> getOrderItems(List<Item> items) {
        List<OrderItem> orderItems = new ArrayList<>();

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

    public void handleStocks(List<Item> items) throws ItemOutOfStockException{
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

    public Item updateItemStockValue(long itemId, int amount) throws ItemNotFoundException{
        if(isExists(itemId)) {
            itemRepo.updateStock(itemId, amount);
            return itemRepo.findById(itemId).get();
        }
        else {
            throw new ItemNotFoundException("No item found with given ID: "+itemId);
        }
    }

    public void updatePrice(long id, double price) {
        if(isExists(id)) {
            itemRepo.updatePrice(id, price);
        }
        else throw new ItemNotFoundException("No such item");
    }

    public void updateDescription(long id, String description) {
        if(isExists(id)) {
            itemRepo.updateDescription(id, description);
        }
        else throw new ItemNotFoundException("No such item");
    }

    public List<Item> getAll() {
        List items = itemRepo.findAll();
        items.sort(Comparator.comparing(Item::getItemId));
        return items;
    }

    public Item getItemById(long id) throws ItemNotFoundException{
        if(isExists(id)) {
            return itemRepo.findById(id).get();
        }
        else throw new ItemNotFoundException("No such item with id '" + id + "'");
    }

    public List<Item> getItemsByName(String name) {
        return itemRepo.findAllByName(name);
    }

    public void deleteItem(long id) {
        if(isExists(id)) {
            itemRepo.deleteById(id);
        } else {
            throw new ItemNotFoundException("No item found with given ID: "+id);
        }
    }

    public List<Item> getByType(ItemType type) {
        return itemRepo.findByItemType(type);
    }
    
    public int countByType(ItemType type) {
        return itemRepo.countByItemType(type);
    }
    
    public long countAll() {
        return itemRepo.count();
    }
    
    public int getTotalStockByType(ItemType type) {
        List<Item> items = getByType(type);
        int totalStocks = 0;
        
        for (Item item : items) {
            totalStocks = totalStocks + item.getStock();
        }
        
        return totalStocks;
    }
    
    public Stats getItemStatistics() {
        ItemStats itemStats = new ItemStats();

        itemStats.setTotalCount(countAll());

        itemStats.setBeverageCount(countByType(ItemType.BEVERAGE));
        itemStats.setTotalBeverageStocks(getTotalStockByType(ItemType.BEVERAGE));

        itemStats.setMealCount(countByType(ItemType.MEAL));
        itemStats.setTotalMealStocks(getTotalStockByType(ItemType.MEAL));

        itemStats.setDessertCount(countByType(ItemType.DESSERT));
        itemStats.setTotalDessertStocks(getTotalStockByType(ItemType.DESSERT));

        return itemStats;
    }


    //id
    public boolean isExists(long id) {
        return itemRepo.existsById(id);
    }
    //name
    public boolean isExists(String name) {
        return itemRepo.existsByName(name);
    }

    //type
    public boolean isExists(ItemType type) {
        return itemRepo.existsByItemType(type);
    }
}
