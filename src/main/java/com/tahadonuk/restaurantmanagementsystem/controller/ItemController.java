package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping(path = "api/item/save")
    @ResponseBody
    public ResponseEntity<Object> saveItem(@RequestBody Item item) {
        try {
            itemService.saveItem(item);
            return ResponseEntity.ok(new StringResponse("Item "+item.getItemId()+" saved successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/item")
    @ResponseBody
    public ResponseEntity<List<Item>> getByType(@RequestParam("type") String type, HttpServletRequest request) {
        ItemType itemType = ItemType.valueOf(type.toUpperCase());
        List<Item> items = itemService.getByType(itemType);

        return ResponseEntity.ok(items);
    }

    @GetMapping(path = "api/item/{id}")
    @ResponseBody
    public ResponseEntity<Object> getById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(itemService.getItemById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }
}
