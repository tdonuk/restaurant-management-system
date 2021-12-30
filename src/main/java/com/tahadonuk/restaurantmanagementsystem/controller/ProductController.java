package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Product;
import com.tahadonuk.restaurantmanagementsystem.dto.ItemDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.ProductService;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import com.tahadonuk.restaurantmanagementsystem.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController(value = "/api/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @PostMapping(path = "/api/product/save")
    @ResponseBody
    public ResponseEntity<Object> saveItem(@RequestBody ItemDTO item) {
        try {
            ItemType type = ItemType.valueOf(item.getType().toUpperCase()); // if this isn't throws an exception, then itemType in string form is valid
            Product productEntity = productService.saveItemFromData(item);
            return ResponseEntity.ok(new StringResponse("Item: "+ productEntity.getName()+" saved successfully. ID: "+ productEntity.getProductId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/api/product")
    @ResponseBody
    public ResponseEntity<List<Product>> getByType(@RequestParam("type") String type) {
        ItemType itemType = ItemType.valueOf(type.toUpperCase());
        List<Product> items = productService.getByType(itemType);

        return ResponseEntity.ok(items);
    }

    @GetMapping(path = "/api/product/{id}/stock")
    @ResponseBody
    public Object updateItemStock(@PathVariable long id, @RequestParam(name = "amount") int amount, HttpServletRequest request) throws IOException {
        UserDTO requestingUser = UserUtils.getUserData(userService,request.getRemoteUser());
        try {
            Product updatedItem = productService.updateItemStockValue(id,amount);
            return ResponseEntity.ok(new StringResponse("Stock value updated to "+updatedItem.getStock()+" for item with ID: "+id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }
    @PostMapping(path = "/api/product/{id}/price")
    @ResponseBody
    public Object updateItemPrice(@PathVariable long id, @RequestBody double price, HttpServletRequest request) {
        UserDTO requestingUser = UserUtils.getUserData(userService,request.getRemoteUser());
        try {
            productService.updatePrice(id, price);
            return ResponseEntity.ok(new StringResponse("Item price updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/api/product/{id}/description")
    @ResponseBody
    public Object updateItemDescription(@PathVariable long id, @RequestBody String description, HttpServletRequest request) {
        UserDTO requestingUser = UserUtils.getUserData(userService,request.getRemoteUser());
        try {
            productService.updateDescription(id, description);
            return ResponseEntity.ok(new StringResponse("Item description updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/api/product/{id}")
    @ResponseBody
    public Object getById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(productService.getItemById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/api/product/{id}/details")
    @ResponseBody
    public Object getItemDetails(@PathVariable long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            mav.setViewName("app/details/product_details");
            UserDTO requestingUser = UserUtils.getUserData(userService,request.getRemoteUser());

            mav.getModel().put("user", requestingUser);

            Product productDetails = productService.getItemById(id);

            mav.getModel().put("productDetails", productDetails);

            return mav;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/api/product/create")
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

    @DeleteMapping(path = "/api/product/{id}")
    @ResponseBody
    public Object deleteItem(@PathVariable("id") long id) {
        try {
            productService.deleteItem(id);
            return ResponseEntity.ok(new StringResponse("Item "+id+" has deleted successfully."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }
}
