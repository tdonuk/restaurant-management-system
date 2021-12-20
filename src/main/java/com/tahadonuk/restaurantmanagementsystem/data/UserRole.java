package com.tahadonuk.restaurantmanagementsystem.data;


import org.springframework.security.core.GrantedAuthority;

public enum UserRole {
    ADMIN("Admin"), MANAGER("Manager"), EMPLOYEE("Employee"), USER("User");

    String roleText;

    UserRole(String roleText) {
        this.roleText = roleText;
    }

    public String getRoleText() {
        return this.roleText;
    }
}
