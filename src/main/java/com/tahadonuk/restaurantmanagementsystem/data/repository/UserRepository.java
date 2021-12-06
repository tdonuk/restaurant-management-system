package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.dto.Email;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findAppUserByEmail(String email);
    boolean existsByEmail(String email);

    List<AppUser> findAppUsersByName(Name name);
    boolean existsByName(Name name);

    Optional<AppUser> findAppUserByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    List<AppUser> findAppUsersByRole(UserRole role);
    boolean existsByRole(UserRole role);
}
