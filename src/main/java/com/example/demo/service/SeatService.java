package com.example.demo.service;

import com.example.demo.entity.JSeat;
import com.example.demo.repository.JSeatRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeatService {
  private final JSeatRepository seatRepository;

  public List<JSeat> findAll() {
    return seatRepository.findAll();
  }

  public JSeat findById(UUID id) {
    return seatRepository.findById(id).orElseThrow(() -> new RuntimeException("Seat not found"));
  }

  public JSeat save(JSeat seat) {
    return seatRepository.save(seat);
  }
}
