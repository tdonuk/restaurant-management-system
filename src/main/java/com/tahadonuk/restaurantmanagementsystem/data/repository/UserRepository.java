package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.AppUser;
import com.tahadonuk.restaurantmanagementsystem.data.UserRole;
import com.tahadonuk.restaurantmanagementsystem.dto.Address;
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

    int countAppUsersByRole(UserRole role);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.lastLoginDate = :loginDate where user.email = :email")
    void updateLastLogin(@Param("loginDate") Date loginDate, @Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.lastLogoutDate = :logoutDate where user.email = :email")
    void updateLastLogout(@Param("logoutDate") Date logoutDate, @Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.name = :name where user.userId = :userId")
    void changeName(@Param("userId") long userId, @Param("name") Name name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.email = :email where user.userId = :userId")
    void changeEmail(@Param("userId") long userId, @Param("email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.phoneNumber = :phoneNumber where user.userId = :userId")
    void changePhoneNumber(@Param("userId") long userId, @Param("phoneNumber") String phoneNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.role = :userRole where user.userId = :userId")
    void assignRole(@Param("userId") long userId, @Param("userRole") UserRole userRole);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.salary = :salary where user.userId = :userId")
    void updateSalary(@Param("userId") long userId, @Param("salary") double salary);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.password = :password where user.userId = :userId")
    void updatePassword(@Param("userId") long userId, @Param("password") String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update AppUser user set user.address = :address where user.userId = :userId")
    void changeAddress(@Param("userId") long userId, @Param("address") Address address);
}
