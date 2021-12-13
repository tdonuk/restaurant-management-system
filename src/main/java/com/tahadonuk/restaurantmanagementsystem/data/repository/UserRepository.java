package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.dto.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.lastLoginDate = :loginDate where user.email = :email")
    void updateLastLogin(@Param("loginDate") Date loginDate, @Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.lastLogoutDate = :logoutDate where user.email = :email")
    void updateLastLogout(@Param("logoutDate") Date logoutDate, @Param("email") String email);
}
