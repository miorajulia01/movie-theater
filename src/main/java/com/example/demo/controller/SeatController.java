package com.example.demo.controller;

import com.example.demo.dto.request.SeatRequest;
import com.example.demo.dto.response.SeatResponse;
import com.example.demo.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
  private final SeatService seatService;

  @GetMapping
  public ResponseEntity<List<SeatResponse>> findAll() {
    return ResponseEntity.ok(seatService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SeatResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(seatService.findById(id));
  }

  @PostMapping
  public ResponseEntity<SeatResponse> create(@RequestBody SeatRequest request) {
    return ResponseEntity.ok(seatService.save(request));
  }
}