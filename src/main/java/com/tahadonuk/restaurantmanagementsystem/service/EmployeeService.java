package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Employee;
import com.tahadonuk.restaurantmanagementsystem.data.EmployeeRole;
import com.tahadonuk.restaurantmanagementsystem.dto.Email;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
import com.tahadonuk.restaurantmanagementsystem.data.repository.EmployeeRepository;
import com.tahadonuk.restaurantmanagementsystem.exception.ConflictException;
import com.tahadonuk.restaurantmanagementsystem.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository empRepo;

    public void saveEmployee(Employee emp) throws Exception{
        if(isExists(emp.getEmail())) {
            throw new ConflictException("An employee with email [ " + emp.getEmail().getEmail() + " ] is already exists.");
        } else {
            empRepo.save(emp);
        }
    }

    public void deleteEmployee(long id) throws Exception{
        if(isExist(id)) {
            empRepo.deleteById(id);
        }
        else throw new NotFoundException("There is no such employee with given ID.");
    }

    public Employee getEmployeeById(long id) throws Exception{
        if(isExist(id)) {
            return empRepo.findById(id).get();
        }
        else throw new NotFoundException("There is no such employee with given ID");
    }

    public List<Employee> getEmployeesByName(Name name) throws Exception{
        if(empRepo.existsByName(name)) {
            List<Employee> employees = empRepo.findEmployeesByName(name);

            return employees;
        }
        else throw new NotFoundException("There is no employee with name [ " + name.getFirstName() + " " + name.getLastName() + " ].");
    }

    public Employee getEmployeeByNumber(String phoneNumber) throws Exception{
        if(empRepo.existsByPhoneNumber(phoneNumber)) {
            Optional<Employee> emp = empRepo.findEmployeeByPhoneNumber(phoneNumber);
            return emp.get();
        }
        else throw new NotFoundException("There is no employee with given phone number.");
    }

    public List<Employee> getEmployeesByRole(EmployeeRole role) {
        List<Employee> employees = empRepo.findEmployeesByRole(role);
        return employees;
    }

    public Employee getEmployeeByEmail(Email email) throws Exception{
        if(isExists(email)) {
            return empRepo.findEmployeeByEmail(email).get();
        }
        else throw new NotFoundException("There is no such employee with email [ " + email.getEmail() + " ].");
    }

    public boolean isExist(long id) {
        if(empRepo.existsById(id)) {
            return true;
        }
        else return false;
    }

    public boolean isExists(Email email) {
        if(empRepo.existsByEmail(email)) {
            return true;
        }
        else return false;
    }
}
