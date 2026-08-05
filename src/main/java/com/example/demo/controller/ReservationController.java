package com.example.demo.controller;

import com.example.demo.entity.JReservation;
import com.example.demo.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public List<JReservation> getAllReservations() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public JReservation getReservationById(@PathVariable UUID id) {
        return reservationService.findById(id);
    }

    @PostMapping
    public JReservation createReservation(@RequestBody JReservation reservation) {
        return reservationService.save(reservation);
    }
}