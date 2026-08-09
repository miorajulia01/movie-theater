package com.example.demo.controller;

import com.example.demo.entity.JRoom;
import com.example.demo.service.RoomService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@AllArgsConstructor
public class RoomController {
  private final RoomService roomService;

  @GetMapping
  public List<JRoom> getAllRooms() {
    return roomService.findAll();
  }

  @GetMapping("/{id}")
  public JRoom getRoomById(@PathVariable UUID id) {
    return roomService.findById(id);
  }

  @PostMapping
  public JRoom createRoom(@RequestBody JRoom room) {
    return roomService.save(room);
  }
}
