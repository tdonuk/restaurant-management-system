package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.TableStats;
import com.tahadonuk.restaurantmanagementsystem.service.ItemService;
import com.tahadonuk.restaurantmanagementsystem.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FragmentController { // this is used to updating a part of page content by ajax queries
    @Autowired
    TableService tableService;
    @Autowired
    ItemService itemService;

    @GetMapping(value = "/fragment/modal/{name}")
    @ResponseBody
    public ModelAndView getModalFragment(@PathVariable String name) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("fragments/modals :: "+name); // name: table adding modal, or employee saving modal etc..
        mav.getModel().putIfAbsent("tableList",tableService.getAll());
        return mav;
    }

    @GetMapping(value = "/fragment/content/{name}")
    @ResponseBody
    public ModelAndView getContentFragment(@PathVariable String name) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("fragments/contents :: "+name); // name: tables, orderItems, etc..

        switch(name) {
            case "tables":
                mav.getModel().putIfAbsent("tableList", tableService.getAll());

                TableStats currentStats = (TableStats) tableService.getStats();

                mav.getModel().put("stats", currentStats);
                break;
            case "orderItems":
                mav.getModel().putIfAbsent("meals", itemService.getByType(ItemType.MEAL));
                mav.getModel().putIfAbsent("beverages", itemService.getByType(ItemType.BEVERAGE));
                mav.getModel().putIfAbsent("desserts", itemService.getByType(ItemType.DESSERT));
                break;
            default: break;
        }

        return mav;
    }

}
