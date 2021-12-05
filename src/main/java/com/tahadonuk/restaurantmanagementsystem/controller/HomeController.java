package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @Autowired
    TableService tableService;

    @GetMapping(value = "/")
    @ResponseBody
    public ModelAndView getHomePage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_page.html");
        return modelAndView;
    }

    @GetMapping(value = "/tables")
    @ResponseBody
    public ModelAndView getTablesPage(Model model) {

        model.addAttribute("tableList",tableService.getAll());
        model.addAttribute("totalCount",tableService.getAll().size());
        model.addAttribute("availableCount", tableService.countByStatus(TableStatus.AVAILABLE));
        model.addAttribute("fullCount", tableService.countByStatus(TableStatus.FULL));
        model.addAttribute("outserviceCount", tableService.countByStatus(TableStatus.OUT_OF_SERVICE));


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tables.html");
        return modelAndView;
    }

    @GetMapping(value = "/menu")
    @ResponseBody
    public ModelAndView getMenuPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu_page.html");
        return modelAndView;
    }

    @GetMapping(value = "/login")
    @ResponseBody
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("*.html");
        return modelAndView;
    }
}
