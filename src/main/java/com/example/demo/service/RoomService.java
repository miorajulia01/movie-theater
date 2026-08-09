package com.example.demo.service;

import com.example.demo.dto.request.RoomRequest;
import com.example.demo.dto.response.RoomResponse;
import com.example.demo.entity.JRoom;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.repository.JRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
  private final JRoomRepository roomRepository;
  private final RoomMapper roomMapper;

  public List<RoomResponse> findAll() {
    return roomRepository.findAll().stream()
            .map(roomMapper::toResponse)
            .collect(Collectors.toList());
  }

  public RoomResponse findById(UUID id) {
    JRoom room = roomRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + id));
    return roomMapper.toResponse(room);
  }

  public RoomResponse save(RoomRequest request) {
    JRoom room = roomMapper.toEntity(request);
    JRoom saved = roomRepository.save(room);
    return roomMapper.toResponse(saved);
  }
}