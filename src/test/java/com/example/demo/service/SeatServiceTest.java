//package com.example.demo.service;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import com.example.demo.entity.JSeat;
//import com.example.demo.repository.JSeatRepository;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class SeatServiceTest {
//
//  private JSeatRepository seatRepository;
//  private SeatService seatService;
//
//  @BeforeEach
//  void setUp() {
//    seatRepository = mock(JSeatRepository.class);
//    seatService = new SeatService(seatRepository);
//  }
//
//  @Test
//  void testFindAll() {
//    JSeat seat = new JSeat();
//    when(seatRepository.findAll()).thenReturn(List.of(seat));
//
//    List<JSeat> seats = seatService.findAll();
//    assertEquals(1, seats.size());
//    verify(seatRepository, times(1)).findAll();
//  }
//
//  @Test
//  void testFindByIdFound() {
//    UUID id = UUID.randomUUID();
//    JSeat seat = new JSeat();
//    seat.setId(id);
//    when(seatRepository.findById(id)).thenReturn(Optional.of(seat));
//
//    JSeat found = seatService.findById(id);
//    assertNotNull(found);
//    assertEquals(id, found.getId());
//  }
//
//  @Test
//  void testFindByIdNotFound() {
//    UUID id = UUID.randomUUID();
//    when(seatRepository.findById(id)).thenReturn(Optional.empty());
//
//    assertThrows(RuntimeException.class, () -> seatService.findById(id));
//  }
//
//  @Test
//  void testSave() {
//    JSeat seat = new JSeat();
//    when(seatRepository.save(any(JSeat.class))).thenReturn(seat);
//
//    JSeat saved = seatService.save(seat);
//    assertNotNull(saved);
//    verify(seatRepository, times(1)).save(seat);
//  }
//}
