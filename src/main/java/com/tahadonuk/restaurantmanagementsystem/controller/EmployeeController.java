package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Employee;
import com.tahadonuk.restaurantmanagementsystem.data.EmployeeRole;
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

     @PostMapping(path = "api/employees/save")
     @ResponseBody
    public String saveEmployee(@RequestBody Employee emp) {
         try {
             empService.saveEmployee(emp);
             return "Employee saved successfully";
         } catch (Exception e) {
             return "Employee is not saved with reason: " + e.getMessage();
         }
    }

    @GetMapping(path = "api/employees/{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
         ResponseEntity<Employee> response;
        try {
            response = new ResponseEntity<>(empService.getEmployeeById(id), HttpStatus.OK);
            return response;
        } catch (Exception e) {
            System.out.println("ERROR \t" + e.getMessage());
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        }
    }


    @PostMapping(path = "api/employees")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeByNumber(@RequestParam("number") String phoneNumber) {
         ResponseEntity<Employee> response;
        try {
            response = new ResponseEntity<>(empService.getEmployeeByNumber(phoneNumber), HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping(path = "api/employees")
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeesByRole(@RequestParam("role") String role) {
        EmployeeRole empRole = EmployeeRole.valueOf(role.toUpperCase());

        return new ResponseEntity<>(empService.getEmployeesByRole(empRole), HttpStatus.OK);
    }
}
