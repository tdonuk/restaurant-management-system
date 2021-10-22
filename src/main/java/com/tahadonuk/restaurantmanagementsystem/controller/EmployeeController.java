package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.employee.Employee;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.employee.EmployeeRole;
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

     @PostMapping(path = "employees/add")
     @ResponseBody
    public ResponseEntity<HttpStatus> saveEmployee(@RequestBody Employee emp) {
         empService.saveEmployee(emp);

         return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "employees/{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
         return new ResponseEntity<>(empService.getEmployeeById(id), HttpStatus.OK);
    }


    @GetMapping(path = "employees/byPhone/{phoneNumber}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeByNumber(@PathVariable String phoneNumber) {
        return new ResponseEntity<>(empService.getEmployeeByNumber(phoneNumber), HttpStatus.OK);
    }

    @GetMapping(path = "employees/role/{role}")
    @ResponseBody
    public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable String role) {
        EmployeeRole empRole = EmployeeRole.valueOf(role.toUpperCase());

        return new ResponseEntity<>(empService.getEmployeesByRole(empRole), HttpStatus.OK);
    }
}
