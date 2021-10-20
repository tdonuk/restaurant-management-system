package com.tahadonuk.restaurantmanagementsystem.service;

import com.tahadonuk.restaurantmanagementsystem.data.entity.Reservation;
import com.tahadonuk.restaurantmanagementsystem.data.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository resRepo;

    public void saveReservation(Reservation reservation) {
        resRepo.save(reservation);
    }

    public void deleteReservation(long id) {
        if(resRepo.existsById(id)) resRepo.deleteById(id);
    }

    public Reservation getById(long id) {
        if(resRepo.existsById(id)) {
            return resRepo.findById(id).get();
        }
        else return null;
    }
}
