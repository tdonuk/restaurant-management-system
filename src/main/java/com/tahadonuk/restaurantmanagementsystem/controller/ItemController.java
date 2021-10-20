package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.item.Item;
import com.tahadonuk.restaurantmanagementsystem.data.entity.item.ItemType;
import com.tahadonuk.restaurantmanagementsystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping(path = "items/add")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveItem(@RequestBody Item item) {
        itemService.saveItem(item);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "items/type/{type}")
    @ResponseBody
    public ResponseEntity<List<Item>> getByType(@PathVariable String type) {
        ItemType itemType = ItemType.valueOf(type.toUpperCase());
        List<Item> items = itemService.getByType(itemType);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(path = "items/{id}")
    @ResponseBody
    public ResponseEntity<Item> getById(@PathVariable long id) {
        return new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
    }
}
