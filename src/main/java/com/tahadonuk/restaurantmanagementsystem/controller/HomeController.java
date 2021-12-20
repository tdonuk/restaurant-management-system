package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.TableStatus;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.TableStatsDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.TableDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.dto.UserStatsDTO;
import com.tahadonuk.restaurantmanagementsystem.service.TableService;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
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

    @GetMapping(value = "/")
    @ResponseBody
    public ModelAndView getHomePage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String currentUserEmail = request.getRemoteUser();
        AppUser user = userService.getUserByEmail(currentUserEmail);

        UserDTO userData = userService.getUserFromEntity(user);

        mav.getModel().put("user", userData);
        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        mav.getModel().put("tableList", tableService.getAll());

        TableStatsDTO tableStats = new TableStatsDTO(tableService.countByStatus(TableStatus.FULL), tableService.countByStatus(TableStatus.AVAILABLE)
                ,tableService.countByStatus(TableStatus.OUT_OF_SERVICE), tableService.getAll().size());
        mav.getModel().put("tableStats", tableStats);

        UserStatsDTO userStats = new UserStatsDTO(userService.getAll().size(),userService.countUsersByRole(UserRole.USER),userService.countUsersByRole(UserRole.EMPLOYEE)
                ,userService.countUsersByRole(UserRole.MANAGER), userService.countUsersByRole(UserRole.ADMIN));
        mav.getModel().put("userStats", userStats);

        mav.setViewName("app/main_page");
        return mav;
    }

    @GetMapping(value = "/employees")
    @ResponseBody
    public ModelAndView getEmployeesPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();

        String currentUserEmail = request.getRemoteUser();
        AppUser user = userService.getUserByEmail(currentUserEmail);

        UserDTO userData = userService.getUserFromEntity(user);

        mav.getModel().put("user", userData);

        mav.getModel().put("navlist", Arrays.asList("Tables", "Employees", "Orders", "Items"));

        if(user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.MANAGER)) {
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
        AppUser user = userService.getUserByEmail(currentUserEmail);

        //Do not transfer the actual user entity to the model. instead, use a data transfer object to transfer only required data
        UserDTO userData = userService.getUserFromEntity(user);

        mav.getModel().put("user", userData);
        mav.getModel().put("tableList",tableService.getAll());

        TableStatsDTO currentStats = new TableStatsDTO(tableService.countByStatus(TableStatus.FULL), tableService.countByStatus(TableStatus.AVAILABLE)
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
