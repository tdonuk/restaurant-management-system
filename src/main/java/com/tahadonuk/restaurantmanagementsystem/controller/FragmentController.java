package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.dto.TableStatsDTO;
import com.tahadonuk.restaurantmanagementsystem.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FragmentController { // this is used to updating a part of page content by ajax queries
    @Autowired
    TableService tableService;

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
        mav.setViewName("fragments/contents :: "+name); // name: tables, or employees etc..

        switch(name) {
            case "tables":
                mav.getModel().putIfAbsent("tableList", tableService.getAll());

                TableStatsDTO currentStats = new TableStatsDTO(tableService.countByStatus(TableStatus.FULL), tableService.countByStatus(TableStatus.AVAILABLE)
                        ,tableService.countByStatus(TableStatus.OUT_OF_SERVICE), tableService.getAll().size());

                mav.getModel().put("stats", currentStats);
                break;
            case "employees":
                //TODO
                break;
            case "orders":
                //TODO: put the required data to the model
                break;
            case "items":
                //TODO: put the required data to the model
                break;
            default: break;
        }

        return mav;
    }

}
