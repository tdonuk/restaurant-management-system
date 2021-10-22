package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Email;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.employee.Employee;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.employee.EmployeeRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByEmail(Email email);
    boolean existsByEmail(Email email);

    List<Employee> findEmployeesByName(Name name);
    boolean existsByName(Name name);

    Optional<Employee> findEmployeeByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    List<Employee> findEmployeesByRole(EmployeeRole role);
    boolean existsByRole(EmployeeRole role);
}
