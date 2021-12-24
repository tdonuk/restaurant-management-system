package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.dto.*;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.ItemStats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.OrderStats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.TableStats;
import com.tahadonuk.restaurantmanagementsystem.dto.stat.UserStats;
import com.tahadonuk.restaurantmanagementsystem.service.ItemService;
import com.tahadonuk.restaurantmanagementsystem.service.OrderService;
import com.tahadonuk.restaurantmanagementsystem.service.TableService;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import com.tahadonuk.restaurantmanagementsystem.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RestController
public class HomeController {

    @Autowired
    TableService tableService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;

    @GetMapping(value = "/")
    @ResponseBody
    public ModelAndView getHomePage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user", userData);
        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        mav.getModel().put("tableList", tableService.getAll());

        //Table statistics
        TableStats tableStats = (TableStats) tableService.getStats();
        mav.getModel().put("tableStats", tableStats);
        //

        //User statistics
        UserStats userStats = (UserStats) userService.getStats();
        mav.getModel().put("userStats", userStats);
        //

        //Item statistics
        ItemStats itemStats = (ItemStats) itemService.getItemStatistics();
        mav.getModel().put("itemStats",itemStats);
        //

        //Order statistics
        OrderStats orderStats = (OrderStats) orderService.getStats();
        mav.getModel().put("orderStats", orderStats);
        //

        mav.setViewName("app/main_page");
        return mav;
    }

    @GetMapping(value = "/employees")
    @ResponseBody
    public ModelAndView getEmployeesPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user", userData);

        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        if(UserRole.valueOf(userData.getRole()).equals(UserRole.ADMIN) || UserRole.valueOf(userData.getRole()).equals(UserRole.MANAGER)) {
            mav.getModel().put("users", userService.getUsersByRole(UserRole.USER));
            mav.getModel().put("admins", userService.getUsersByRole(UserRole.ADMIN));
            mav.getModel().put("managers", userService.getUsersByRole(UserRole.MANAGER));
            mav.getModel().put("employees", userService.getUsersByRole(UserRole.EMPLOYEE));

            mav.setViewName("app/employees");

            return mav;
        }

        else {
            System.out.println("Unauthorized attempt");
            response.sendRedirect("/");
            return null;
        }

    }


    @GetMapping(value = "/tables")
    @ResponseBody
    public ModelAndView getTablesPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user", userData);
        mav.getModel().put("tableList",tableService.getAll());

        TableStats currentStats = (TableStats) tableService.getStats();

        mav.getModel().put("stats", currentStats);

        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        mav.getModel().put("tableData",new TableDTO());
        mav.setViewName("app/tables");

        return mav;
    }

    @GetMapping(value = "/items")
    @ResponseBody
    public ModelAndView getItemsPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("app/items");

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user",userData);
        mav.getModel().put("items",itemService.getAll());
        mav.getModel().put("navList", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        return mav;
    }

    @GetMapping(value = "/orders")
    @ResponseBody
    public ModelAndView getOrdersPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("app/orders");

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user",userData);

        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        if(UserRole.valueOf(userData.getRole()).equals(UserRole.ADMIN) || UserRole.valueOf(userData.getRole()).equals(UserRole.MANAGER)) {
            mav.getModel().put("orders",orderService.getAll());
            mav.getModel().put("meals",itemService.getByType(ItemType.MEAL));
            mav.getModel().put("beverages",itemService.getByType(ItemType.BEVERAGE));
            mav.getModel().put("desserts",itemService.getByType(ItemType.DESSERT));

            return mav;
        }

        return mav;
    }

    @GetMapping(value = "/login")
    @ResponseBody
    public ModelAndView getLoginPage() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("login");
        return mav;
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public ModelAndView logOutRedirect(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login?logout");
        return modelAndView;
    }

    @GetMapping(value = "/signup")
    @ResponseBody
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        modelAndView.getModel().put("userData", new UserDTO());
        return modelAndView;
    }
}
