package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.dto.Email;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Employee;
import com.tahadonuk.restaurantmanagementsystem.data.EmployeeRole;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
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

    Optional<Employee> findEmployeeByEmail_Email(String email);
    boolean existsByEmail_Email(String email);

    List<Employee> findEmployeesByRole(EmployeeRole role);
    boolean existsByRole(EmployeeRole role);

    int countByAddress_Street(String street);
}
