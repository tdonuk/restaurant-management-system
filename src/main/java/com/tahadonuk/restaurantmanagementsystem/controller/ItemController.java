package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.item.Item;
import com.tahadonuk.restaurantmanagementsystem.data.entity.item.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController(value = "/item")
public class ItemController {

    @Autowired
    ItemRepository itemRepo;

    /*
    @GetMapping(path = "item/beverage/{abc}")
    public void saveBeverage(@PathVariable String abc) {

    }
    */

    @PostMapping(path = "item/add")
    public void saveItem(@RequestBody Item item) {
        System.out.println("------------ITEMS------------");
        System.out.println(item.getName());
        System.out.println(item.getDescription());
        System.out.println(item.getPrice());
        System.out.println(item.getItemType());

        itemRepo.save(item);
    }

    @GetMapping(path = "items/{type}")
    public String saveItem(@PathVariable ItemType type) {
        Set<Item> items = itemRepo.findAll().stream().collect(Collectors.toSet());

        return items.toString();
    }
}
