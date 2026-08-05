package com.example.demo.controller;

import com.example.demo.entity.JSeat;
import com.example.demo.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seats")
@AllArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping
    public List<JSeat> getAllSeats() {
        return seatService.findAll();
    }

    @GetMapping("/{id}")
    public JSeat getSeatById(@PathVariable UUID id) {
        return seatService.findById(id);
    }

    @PostMapping
    public JSeat createSeat(@RequestBody JSeat seat) {
        return seatService.save(seat);
    }
}