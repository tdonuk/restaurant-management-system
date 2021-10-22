package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.employee.Employee;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.employee.EmployeeRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Name;
import com.tahadonuk.restaurantmanagementsystem.data.repository.EmployeeRepository;
import com.tahadonuk.restaurantmanagementsystem.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository empRepo;

    public void saveEmployee(Employee emp) {
        empRepo.save(emp);
    }

    public void deleteEmployee(long id) {
        empRepo.deleteById(id);
    }

    public Employee getEmployeeById(long id) {
        if(empRepo.existsById(id)) {
            Optional<Employee> emp = empRepo.findById(id);
            return emp.get();
        }
        else return null;
    }

    public List<Employee> getEmployeesByName(Name name) {
        if(empRepo.existsByName(name)) {
            List<Optional<Employee>> employeeOptionals = empRepo.findEmployeesByName(name);

            return ListUtils.getListFromOptionals(employeeOptionals);
        }
        else return null;
    }

    public Employee getEmployeeByNumber(String phoneNumber) {
        if(empRepo.existsByPhoneNumber(phoneNumber)) {
            Optional<Employee> emp = empRepo.findEmployeeByPhoneNumber(phoneNumber);
            return emp.get();
        }
        else return null;
    }

    public List<Employee> getEmployeesByRole(EmployeeRole role) {
        if(empRepo.existsByRole(role)) {
            List<Optional<Employee>> employeeOptionals = empRepo.findEmployeesByRole(role);

            return ListUtils.getListFromOptionals(employeeOptionals);
        }
        else return null;
    }
}
