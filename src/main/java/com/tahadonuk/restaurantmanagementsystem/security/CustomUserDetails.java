package com.tahadonuk.restaurantmanagementsystem.security;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private AppUser emp;

    public CustomUserDetails(AppUser emp) {
        this.emp = emp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return emp.getPassword();
    }

    @Override
    public String getUsername() {
        return emp.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
