package com.tahadonuk.restaurantmanagementsystem.security;

import com.tahadonuk.restaurantmanagementsystem.data.PhoneNumber;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.employee.Employee;
import com.tahadonuk.restaurantmanagementsystem.dto.Address;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitialDataLoader {
    private UserService userService;

    @Autowired
    public InitialDataLoader(UserService userService) {
        this.userService = userService;

        createAdminAccount();
    }

    private void createAdminAccount() {
        if(userService.countAll() == 0) {
            Employee admin = new Employee();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            admin.setName(new Name("admin", "admin"));
            admin.setEmail("admin@account.com");
            admin.setPassword(encoder.encode("administrator"));
            admin.setRole(UserRole.ADMIN);
            admin.getAddressList().add(new Address("example", "example"));
            admin.getPhoneNumbers().add(new PhoneNumber("example", "example"));
            admin.setJoinDate(new Date());
            admin.setSalary(1);
            admin.setDateOfBirth(new Date());

            userService.saveUser(admin);
        }
    }
}
