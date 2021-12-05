package com.tahadonuk.restaurantmanagementsystem.data.repository;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Reservation;
import com.tahadonuk.restaurantmanagementsystem.data.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Optional<Reservation>> findAllByTable(RestaurantTable table);
    boolean existsByTable(RestaurantTable table);

    List<Optional<Reservation>> findAllByProcessDateBetween(Date processDate, Date processDate2);
    boolean existsByProcessDateBetween(Date processDate, Date processDate2);

    List<Optional<Reservation>> findAllByCustomer_CustomerId(long customerId);
    boolean existsByCustomer_CustomerId(long customerId);
}
