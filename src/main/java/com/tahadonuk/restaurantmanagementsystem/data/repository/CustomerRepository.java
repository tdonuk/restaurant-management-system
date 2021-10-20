package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Customer;
import com.tahadonuk.restaurantmanagementsystem.data.entity.user.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(Email email);
    boolean existsByEmail(Email email);
}
