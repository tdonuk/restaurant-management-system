package com.tahadonuk.restaurantmanagementsystem.controller;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Employee;
import com.tahadonuk.restaurantmanagementsystem.data.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository empRepo;

     @PostMapping(path = "employee/add")
    public void addEmployee(Employee emp) {
         System.out.println("--------EMPLOYEE---------");
         System.out.println(emp.getName().getFirstName());
         System.out.println(emp.getEmail());
         System.out.println(emp.getRole());
         System.out.println(emp.getSalary());
         System.out.println(emp.getDateOfBirth());

         //empRepo.save(emp);
    }
}
