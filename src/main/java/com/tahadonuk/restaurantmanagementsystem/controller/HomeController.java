package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.StatusStatsDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.TableDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.TableService;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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

        UserDTO userData = userService.getUserFromEntity(user);

        mav.getModel().put("user", userData);
        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        mav.setViewName("app/main_page");
        return mav;
    }

    @GetMapping(value = "/employees")
    @ResponseBody
    public ModelAndView getEmployeesPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String currentUserEmail = request.getRemoteUser();
        AppUser user = userService.getUserByEmail(currentUserEmail);

        UserDTO userData = userService.getUserFromEntity(user);

        mav.getModel().put("user", userData);
        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        mav.setViewName("app/employees");
        return mav;
    }


    @GetMapping(value = "/tables")
    @ResponseBody
    public ModelAndView getTablesPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();


        String currentUserEmail = request.getRemoteUser();
        AppUser user = userService.getUserByEmail(currentUserEmail);

        //Do not transfer the actual user entity to the model. instead, use a data transfer object to transfer only required data
        UserDTO userData = userService.getUserFromEntity(user);

        mav.getModel().put("user", userData);
        mav.getModel().put("tableList",tableService.getAll());

        StatusStatsDTO currentStats = new StatusStatsDTO(tableService.countByStatus(TableStatus.FULL), tableService.countByStatus(TableStatus.AVAILABLE)
                                                ,tableService.countByStatus(TableStatus.OUT_OF_SERVICE), tableService.getAll().size());

        mav.getModel().put("stats", currentStats);

        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        mav.getModel().put("tableData",new TableDTO());
        mav.setViewName("app/tables");

        return mav;
    }

    @GetMapping(value = "/menu")
    @ResponseBody
    public ModelAndView getMenuPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("app/items");
        return modelAndView;
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
