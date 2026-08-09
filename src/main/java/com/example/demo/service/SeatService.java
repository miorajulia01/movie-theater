package com.example.demo.service;

import com.example.demo.dto.request.SeatRequest;
import com.example.demo.dto.response.SeatResponse;
import com.example.demo.entity.JRoom;
import com.example.demo.entity.JSeat;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.SeatMapper;
import com.example.demo.repository.JRoomRepository;
import com.example.demo.repository.JSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {
  private final JSeatRepository seatRepository;
  private final JRoomRepository roomRepository;
  private final SeatMapper seatMapper;

  public SeatResponse findById(UUID id) {
    JSeat seat = seatRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Seat not found: " + id));
    return seatMapper.toResponse(seat);
  }

  public SeatResponse save(SeatRequest request) {
    JRoom room = roomRepository.findById(request.getRoomId())
            .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    return seatMapper.toResponse(seatRepository.save(seatMapper.toEntity(request, room)));
  }
}