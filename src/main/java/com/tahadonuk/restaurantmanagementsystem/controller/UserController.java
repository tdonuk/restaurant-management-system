package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.Email;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

     @PostMapping(path = "api/employee/save")
     @ResponseBody
    public ResponseEntity<Object> saveEmployee(@RequestBody UserDTO emp) {
         try {
             AppUser result = userService.saveUser(emp);
             return ResponseEntity.ok().body(new StringResponse("Employee saved successfully. ID: " +result.getUserId()));
         } catch (Exception e) {
             return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
         }
    }

    @GetMapping(path = "api/employee/{id}")
    @ResponseBody
    public ResponseEntity<Object> getEmployeeById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }


    @PostMapping(path = "api/employee")
    @ResponseBody
    public ResponseEntity<Object> getEmployeeByNumber(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok().body(userService.getUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/employee/role")
    @ResponseBody
    public ResponseEntity<List<AppUser>> getEmployeesByRole(@RequestParam("role") String role) {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());

        return ResponseEntity.ok(userService.getUsersByRole(userRole));
    }

    @PostMapping(path = "/register")
    @ResponseBody
    public ModelAndView registerUser(@ModelAttribute("appUser") UserDTO user, HttpServletRequest request) {
        try {
            AppUser appUser = userService.saveUser(user);
            request.logout();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:signup", "error", e.getMessage());
        }
    }
}
