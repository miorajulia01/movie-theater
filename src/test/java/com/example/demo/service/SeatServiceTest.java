package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.dto.request.SeatRequest;
import com.example.demo.dto.response.SeatResponse;
import com.example.demo.entity.JRoom;
import com.example.demo.entity.JSeat;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.SeatMapper;
import com.example.demo.repository.JRoomRepository;
import com.example.demo.repository.JSeatRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeatServiceTest {

  private JSeatRepository seatRepository;
  private JRoomRepository roomRepository;
  private SeatMapper seatMapper;
  private SeatService seatService;

  @BeforeEach
  void setUp() {
    seatRepository = mock(JSeatRepository.class);
    roomRepository = mock(JRoomRepository.class);
    seatMapper = mock(SeatMapper.class);
    seatService = new SeatService(seatRepository, roomRepository, seatMapper);
  }

  @Test
  void testFindAll() {
    JSeat seat = new JSeat();
    SeatResponse response = new SeatResponse();

    when(seatRepository.findAll()).thenReturn(List.of(seat));
    when(seatMapper.toResponse(seat)).thenReturn(response);

    List<SeatResponse> seats = seatService.findAll();
    assertEquals(1, seats.size());
    verify(seatRepository, times(1)).findAll();
  }

  @Test
  void testFindByIdFound() {
    UUID id = UUID.randomUUID();
    JSeat seat = new JSeat();
    SeatResponse response = new SeatResponse();

    when(seatRepository.findById(id)).thenReturn(Optional.of(seat));
    when(seatMapper.toResponse(seat)).thenReturn(response);

    SeatResponse found = seatService.findById(id);
    assertNotNull(found);
    verify(seatRepository, times(1)).findById(id);
  }

  @Test
  void testFindByIdNotFound() {
    UUID id = UUID.randomUUID();
    when(seatRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> seatService.findById(id));
  }

  @Test
  void testSave() {
    UUID roomId = UUID.randomUUID();
    SeatRequest request = new SeatRequest();
    request.setRoomId(roomId);

    JRoom room = new JRoom();
    JSeat seat = new JSeat();
    JSeat savedSeat = new JSeat();
    SeatResponse response = new SeatResponse();

    when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
    when(seatMapper.toEntity(request, room)).thenReturn(seat);
    when(seatRepository.save(seat)).thenReturn(savedSeat);
    when(seatMapper.toResponse(savedSeat)).thenReturn(response);

    SeatResponse saved = seatService.save(request);
    assertNotNull(saved);
    verify(seatRepository, times(1)).save(seat);
  }
}
