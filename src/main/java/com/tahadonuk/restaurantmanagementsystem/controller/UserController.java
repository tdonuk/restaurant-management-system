package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

     @PostMapping(path = "api/user/save")
     @ResponseBody
    public Object saveEmployee(@RequestBody UserDTO emp) {
         try {
             AppUser result = userService.saveUser(emp);
             return ResponseEntity.ok().body(new StringResponse("Employee saved successfully. ID: " +result.getUserId()));
         } catch (Exception e) {
             return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
         }
    }

    @GetMapping(path = "api/user/{id}/details")
    @ResponseBody
    public Object getUserDetails(@PathVariable long id, HttpServletRequest request) {
         ModelAndView mav = new ModelAndView();
        try {
            AppUser user = userService.getUserById(id);
            AppUser requestingUser = userService.getUserByEmail(request.getRemoteUser());
            UserDTO requestingUserData = userService.getUserFromEntity(requestingUser);

            mav.getModel().put("userDetails", user);
            mav.getModel().put("user",requestingUserData);
            mav.getModel().put("navList", Arrays.asList("Tables", "Employees", "Orders", "Items"));

            mav.setViewName("app/details/user_details");

            return mav;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }


    @PostMapping(path = "api/user")
    @ResponseBody
    public Object getEmployeeByNumber(@RequestParam("email") String email) {
        try {
            return ResponseEntity.ok().body(userService.getUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @DeleteMapping(path = "api/user/{id}/delete")
    @ResponseBody
    public Object deleteUser(@PathVariable("id") long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new StringResponse("User with ID: "+id+" has deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/user/role")
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
            return new ModelAndView("redirect:/login");
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/signup", "error", e.getMessage());
        }
    }
}
