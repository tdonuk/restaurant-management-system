package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.ItemType;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Item;
import com.tahadonuk.restaurantmanagementsystem.data.entity.Order;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.TableDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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

    @GetMapping(value = "/me")
    @ResponseBody
    public ModelAndView getProfilePage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        AppUser requestingUser = userService.getUserByEmail(request.getRemoteUser());
        UserDTO requestingUserData = userService.getUserFromEntity(requestingUser);

        mav.getModel().put("userDetails", requestingUser);
        mav.getModel().put("user",requestingUserData);

        mav.setViewName("app/profile");

        return mav;
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public ModelAndView getEmployeesPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user", userData);

        if(UserRole.valueOf(userData.getRole()).equals(UserRole.ADMIN) || UserRole.valueOf(userData.getRole()).equals(UserRole.MANAGER)) {
            mav.getModel().put("users", userService.getAll());

            mav.setViewName("app/users");
        }
        else {
            mav.setViewName("app/unauthorized");
        }
        return mav;
    }

    @GetMapping(value = "/unauthorized")
    @ResponseBody
    public ModelAndView getUnauthorizedPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("app/unauthorized");

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user",userData);

        return mav;
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

        mav.getModel().put("tableData",new TableDTO());
        mav.setViewName("app/tables");

        return mav;
    }

    @GetMapping(value = "/items")
    @ResponseBody
    public ModelAndView getItemsPage(@RequestParam(required = false, name = "sort") String sort, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("app/items");

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        List<Item> items = itemService.getAll();

        if(sort != null) {
            switch (sort) {
                case "id":
                    items.sort(Comparator.comparing(Item::getItemId));
                    break;
                case "name":
                    items.sort(Comparator.comparing(Item::getName));
                    break;
                case "type":
                    items.sort(Comparator.comparing(Item::getItemType));
                    break;
                case "stock":
                    items.sort(Comparator.comparing(Item::getStock));
                    break;
                case "price":
                    items.sort(Comparator.comparing(Item::getPrice));
                    break;
                default:
                    items.sort(Comparator.comparing(Item::getItemId));
                    break;
            }
        }

        mav.getModel().put("user", userData);
        mav.getModel().put("items", items);

        return mav;
    }

    @GetMapping(value = "/orders")
    @ResponseBody
    public ModelAndView getOrdersPageSorted(@RequestParam(required = false, name = "sort") String sort, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("app/orders");

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user",userData);
        mav.getModel().put("items", itemService.getAll());

        List<Order> orders = orderService.getAll();

        if(sort != null) {
            switch (sort) {
                case "id":
                    orders.sort(Comparator.comparing(Order::getOrderId));
                    break;
                case "table":
                    orders.sort(Comparator.comparing(Order::getTableId));
                    break;
                case "date":
                    orders.sort(Comparator.comparing(Order::getOrderDate));
                    break;
                case "price":
                    orders.sort(Comparator.comparing(Order::getTotalPrice));
                    break;
                default:
                    orders.sort(Comparator.comparing(Order::getOrderId));
                    break;
            }
        }

        mav.getModel().put("orders", orders);

        return mav;
    }

    @GetMapping(value = "/orders/filter/interval")
    @ResponseBody
    public ModelAndView getOrdersPageFiltered(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end, HttpServletRequest request) throws ParseException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("app/orders");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user",userData);

        List<Order> orders = orderService.getOrdersByInterval(startDate, endDate);

        mav.getModel().put("orders", orders);

        return mav;
    }

    @GetMapping(value = "/orders/filter/item")
    @ResponseBody
    public ModelAndView getOrdersPageFiltered(@RequestParam(name = "name") String name,HttpServletRequest request) throws ParseException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("app/orders");

        String currentUserEmail = request.getRemoteUser();
        UserDTO userData = UserUtils.getUserData(userService,currentUserEmail);

        mav.getModel().put("user",userData);

        List<Order> orders = orderService.getOrdersItemsContains(name);

        mav.getModel().put("orders", orders);
        mav.getModel().put("items", itemService.getAll());

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
