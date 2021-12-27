package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.dto.ItemDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.ItemService;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import com.tahadonuk.restaurantmanagementsystem.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
public class ItemController {

    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;

    @PostMapping(path = "api/item/save")
    @ResponseBody
    public ResponseEntity<Object> saveItem(@RequestBody ItemDTO item) {
        try {
            ItemType type = ItemType.valueOf(item.getType().toUpperCase()); // if this isn't throws an exception, then itemType in string form is valid
            Item itemEntity = itemService.saveItemFromData(item);
            return ResponseEntity.ok(new StringResponse("Item: "+itemEntity.getName()+" saved successfully. ID: "+itemEntity.getItemId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/item")
    @ResponseBody
    public ResponseEntity<List<Item>> getByType(@RequestParam("type") String type) {
        ItemType itemType = ItemType.valueOf(type.toUpperCase());
        List<Item> items = itemService.getByType(itemType);

        return ResponseEntity.ok(items);
    }

    @GetMapping(path = "api/item/{id}/stock")
    @ResponseBody
    public Object updateItemStock(@PathVariable long id, @RequestParam(name = "amount") int amount, HttpServletRequest request) throws IOException {
        UserDTO requestingUser = UserUtils.getUserData(userService,request.getRemoteUser());
        try {
            Item updatedItem = itemService.updateItemStockValue(id,amount);
            return ResponseEntity.ok(new StringResponse("Stock value updated to "+updatedItem.getStock()+" for item with ID: "+id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }
    @PostMapping(path = "api/item/{id}/price")
    @ResponseBody
    public Object updateItemPrice(@PathVariable long id, @RequestBody double price, HttpServletRequest request) {
        UserDTO requestingUser = UserUtils.getUserData(userService,request.getRemoteUser());
        try {
            itemService.updatePrice(id, price);
            return ResponseEntity.ok(new StringResponse("Item price updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "api/item/{id}/description")
    @ResponseBody
    public Object updateItemDescription(@PathVariable long id, @RequestBody String description, HttpServletRequest request) {
        UserDTO requestingUser = UserUtils.getUserData(userService,request.getRemoteUser());
        try {
            itemService.updateDescription(id, description);
            return ResponseEntity.ok(new StringResponse("Item description updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/item/{id}")
    @ResponseBody
    public Object getById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(itemService.getItemById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/item/{id}/details")
    @ResponseBody
    public Object getItemDetails(@PathVariable long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            mav.setViewName("app/details/item_details");
            UserDTO requestingUser = UserUtils.getUserData(userService,request.getRemoteUser());

            mav.getModel().put("user", requestingUser);

            Item itemDetails = itemService.getItemById(id);

            mav.getModel().put("itemDetails", itemDetails);

            return mav;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/item/create")
    @ResponseBody
    public Object getCreateItemPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            mav.setViewName("app/create/create_item");

            return mav;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @DeleteMapping(path = "api/item/{id}")
    @ResponseBody
    public Object deleteItem(@PathVariable("id") long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.ok(new StringResponse("Item "+id+" has deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }
}
