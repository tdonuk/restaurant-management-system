package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.Address;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
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
            UserDTO requestingUserData = userService.getUserFromEntity(userService.getUserByEmail(request.getRemoteUser()));

            mav.getModel().put("userDetails", user);
            mav.getModel().put("user",requestingUserData);

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

    @DeleteMapping(path = "api/user/{id}")
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
    public ResponseEntity<List<AppUser>> getUsersByRole(@RequestParam("role") String role) {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());

        return ResponseEntity.ok(userService.getUsersByRole(userRole));
    }

    @GetMapping(path = "api/user/all")
    @ResponseBody
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
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

    @PostMapping(path = "/api/user/{id}/role")
    @ResponseBody
    public Object assignUserRole(@PathVariable long id, @RequestBody String role) {
        try {
            UserRole parsedRole = UserRole.valueOf(role.toUpperCase().replaceAll(" ","_"));
            userService.assignRole(id, parsedRole);
            return ResponseEntity.ok(new StringResponse("Role assigned successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user/{id}/name")
    @ResponseBody
    public Object changeUserName(@PathVariable long id, @RequestBody Name name) {
        try {
            userService.changeName(id, name);
            return ResponseEntity.ok(new StringResponse("Name changed successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user/{id}/email")
    @ResponseBody
    public Object changeEmail(@PathVariable long id, @RequestBody String email, HttpServletRequest request) {
        try {
            request.logout();
            userService.changeEmail(id, email);
            return ResponseEntity.ok(new StringResponse("Email changed successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user/{id}/phone")
    @ResponseBody
    public Object changePhoneNumber(@PathVariable long id, @RequestBody String phone) {
        try {
            userService.changePhoneNumber(id, phone);
            return ResponseEntity.ok(new StringResponse("Phone number changed successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user/{id}/salary")
    @ResponseBody
    public Object changeSalary(@PathVariable long id, @RequestBody double salary) {
        try {
            userService.updateSalary(id, salary);
            return ResponseEntity.ok(new StringResponse("Salary has updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user/{id}/password")
    @ResponseBody
    public Object changePassword(@PathVariable long id, @RequestBody String password, HttpServletRequest request) {
        try {
            request.logout();
            userService.changePassword(id, password);
            return ResponseEntity.ok(new StringResponse("Password has updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user/{id}/address")
    @ResponseBody
    public Object changeAddress(@PathVariable long id, @RequestBody Address address) {
        try {
            userService.changeAddress(id, address);
            return ResponseEntity.ok(new StringResponse("Address has updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }
}
