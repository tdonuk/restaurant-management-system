package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.TableService;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    @Autowired
    TableService tableService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    @ResponseBody
    public ModelAndView getHomePage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String currentUserEmail = request.getRemoteUser();
        AppUser user = userService.getUserByEmail(currentUserEmail);

        mav.getModel().put("user", user.getName().getFirstName());

        mav.setViewName("main_page");
        return mav;
    }

    @GetMapping(value = "/tables")
    @ResponseBody
    public ModelAndView getTablesPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();


        String currentUserEmail = request.getRemoteUser();
        AppUser user = userService.getUserByEmail(currentUserEmail);

        mav.getModel().put("user", user.getName().getFirstName());
        mav.getModel().put("tableList",tableService.getAll());
        mav.getModel().put("totalCount",tableService.getAll().size());
        mav.getModel().put("availableCount", tableService.countByStatus(TableStatus.AVAILABLE));
        mav.getModel().put("fullCount", tableService.countByStatus(TableStatus.FULL));
        mav.getModel().put("outserviceCount", tableService.countByStatus(TableStatus.OUT_OF_SERVICE));

        mav.setViewName("tables");

        return mav;
    }

    @GetMapping(value = "/menu")
    @ResponseBody
    public ModelAndView getMenuPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu_page");
        return modelAndView;
    }

    @GetMapping(value = "/login")
    @ResponseBody
    public ModelAndView getLoginPage() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("login");
        return mav;
    }

    @GetMapping(value = "/signup")
    @ResponseBody
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        modelAndView.getModel().put("appUser", new UserDTO());
        return modelAndView;
    }
}
