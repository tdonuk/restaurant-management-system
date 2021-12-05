package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Employee;
import com.tahadonuk.restaurantmanagementsystem.data.EmployeeRole;
import com.tahadonuk.restaurantmanagementsystem.dto.Email;
import com.tahadonuk.restaurantmanagementsystem.dto.StringResponse;
import com.tahadonuk.restaurantmanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService empService;

     @PostMapping(path = "api/employee/save")
     @ResponseBody
    public ResponseEntity<Object> saveEmployee(@RequestBody Employee emp) {
         try {
             empService.saveEmployee(emp);
             return ResponseEntity.ok().body(new StringResponse("Employee saved successfully. ID: " +emp.getEmployeeId()));
         } catch (Exception e) {
             return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
         }
    }

    @GetMapping(path = "api/employee/{id}")
    @ResponseBody
    public ResponseEntity<Object> getEmployeeById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(empService.getEmployeeById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }


    @PostMapping(path = "api/employee/")
    @ResponseBody
    public ResponseEntity<Object> getEmployeeByNumber(@RequestParam("email") Email email) {
        try {
            return ResponseEntity.ok().body(empService.getEmployeeByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new StringResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "api/employee/role")
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeesByRole(@RequestParam("role") String role) {
        EmployeeRole empRole = EmployeeRole.valueOf(role.toUpperCase());

        return ResponseEntity.ok(empService.getEmployeesByRole(empRole));
    }
}
