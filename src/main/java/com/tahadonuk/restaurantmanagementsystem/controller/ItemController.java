package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping(path = "api/items/save")
    @ResponseBody
    public String saveItem(@RequestBody Item item) {
        try {
            itemService.saveItem(item);
            return "Item saved successfully";
        } catch (Exception e) {
            return "Item is not saved with reason: " + e.getMessage();
        }
    }

    @GetMapping(path = "api/items")
    @ResponseBody
    public ResponseEntity<List<Item>> getByType(@RequestParam("type") String type) {
        ItemType itemType = ItemType.valueOf(type.toUpperCase());
        List<Item> items = itemService.getByType(itemType);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin","*");

        return new ResponseEntity<>(items, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(path = "api/items/{id}")
    @ResponseBody
    public ResponseEntity<Item> getById(@PathVariable long id) {
        ResponseEntity<Item> response;
        try {
            response = new ResponseEntity<>(itemService.getItemById(id), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            System.out.println("ERROR\t" + e.getMessage());
        }
        return response;
    }
}
