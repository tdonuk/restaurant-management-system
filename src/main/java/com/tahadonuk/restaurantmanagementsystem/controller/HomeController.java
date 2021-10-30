package com.tahadonuk.restaurantmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
    @GetMapping(path = "api/")
    @ResponseBody
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main_page.html");
        return modelAndView;
    }

    @GetMapping(path = "api/tables")
    @ResponseBody
    public ModelAndView getTablesPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("*.html");
        return modelAndView;
    }

    @GetMapping(path = "api/menu")
    @ResponseBody
    public ModelAndView getMenuPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("*.html");
        return modelAndView;
    }

    @GetMapping(path = "/login")
    @ResponseBody
    public ModelAndView getLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("*.html");
        return modelAndView;
    }
}
