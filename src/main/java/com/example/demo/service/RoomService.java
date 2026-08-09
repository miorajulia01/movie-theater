package com.example.demo.service;

import com.example.demo.entity.JRoom;
import com.example.demo.repository.JRoomRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomService {
  private final JRoomRepository roomRepository;

  public List<JRoom> findAll() {
    return roomRepository.findAll();
  }

  public JRoom findById(UUID id) {
    return roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
  }

  public JRoom save(JRoom room) {
    return roomRepository.save(room);
  }
}
