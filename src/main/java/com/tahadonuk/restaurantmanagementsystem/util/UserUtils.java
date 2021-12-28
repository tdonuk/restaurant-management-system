package com.tahadonuk.restaurantmanagementsystem.util;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.UserDTO;
import com.tahadonuk.restaurantmanagementsystem.service.UserService;

public class UserUtils {
    private UserUtils() {}

    public static UserDTO getUserData(UserService userService, String email) {
        AppUser user = userService.getUserByEmail(email);

        UserDTO userData = userService.getUserFromEntity(user);

        return userData;
    }
}
