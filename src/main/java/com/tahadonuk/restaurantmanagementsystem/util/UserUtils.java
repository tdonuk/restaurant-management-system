package com.tahadonuk.restaurantmanagementsystem.util;

import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;
import com.tahadonuk.restaurantmanagementsystem.data.entity.employee.Employee;

public class UserUtils {
    private UserUtils() {}

    public static UserDTO getUserData(UserService userService, String email) {
        Employee user = userService.getUserByEmail(email);

        UserDTO userData = userService.getUserFromEntity(user);

        return userData;
    }
}
