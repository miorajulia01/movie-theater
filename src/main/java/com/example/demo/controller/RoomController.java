package com.example.demo.controller;

import com.example.demo.dto.request.RoomRequest;
import com.example.demo.dto.response.RoomResponse;
import com.example.demo.service.RoomService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
  private final RoomService roomService;

  @GetMapping
  public ResponseEntity<List<RoomResponse>> findAll() {
    return ResponseEntity.ok(roomService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<RoomResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(roomService.findById(id));
  }

  @PostMapping
  public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest request) {
    return ResponseEntity.ok(roomService.save(request));
  }
}
