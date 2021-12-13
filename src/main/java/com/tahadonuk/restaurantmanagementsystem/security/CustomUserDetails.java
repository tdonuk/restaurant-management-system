package com.tahadonuk.restaurantmanagementsystem.security;

import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private AppUser user;

    public CustomUserDetails(AppUser user) {
        this.user = user;
    }

    public AppUser getUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        if(user.getRole() == UserRole.USER) list.add(new SimpleGrantedAuthority("USER"));
        if(user.getRole() == UserRole.EMPLOYEE) list.add(new SimpleGrantedAuthority("EMPLOYEE"));
        if(user.getRole() == UserRole.MANAGER) list.add(new SimpleGrantedAuthority("MANAGER"));
        if(user.getRole() == UserRole.ADMIN) list.add(new SimpleGrantedAuthority("ADMIN"));

        return list;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
