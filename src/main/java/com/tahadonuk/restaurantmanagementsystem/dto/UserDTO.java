package com.tahadonuk.restaurantmanagementsystem.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;

    private String email;
    private String phoneName;
    private String phoneNumber;
    private String password;

    private String addressLine;
    private String details;

    private String joinDate;
    private String dateOfBirth;
    private String lastLoginDate;
    private String lastLogoutDate;

    private String role;
}
