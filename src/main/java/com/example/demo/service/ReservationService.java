package com.example.demo.service;

import com.example.demo.entity.JReservation;
import com.example.demo.repository.JReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReservationService {
    private final JReservationRepository reservationRepository;

    public List<JReservation> findAll() {
        return reservationRepository.findAll();
    }

    public JReservation findById(UUID id) {
        return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public JReservation save(JReservation reservation) {
        return reservationRepository.save(reservation);
    }
}