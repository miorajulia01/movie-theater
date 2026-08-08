package com.example.demo.service;

import com.example.demo.entity.JRoom;
import com.example.demo.repository.JRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    private JRoomRepository roomRepository;
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        roomRepository = mock(JRoomRepository.class);
        roomService = new RoomService(roomRepository);
    }

    @Test
    void testFindAll() {
        JRoom room = new JRoom();
        when(roomRepository.findAll()).thenReturn(List.of(room));

        List<JRoom> rooms = roomService.findAll();
        assertEquals(1, rooms.size());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdFound() {
        UUID id = UUID.randomUUID();
        JRoom room = new JRoom();
        room.setId(id);
        when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        JRoom found = roomService.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> roomService.findById(id));
    }

    @Test
    void testSave() {
        JRoom room = new JRoom();
        when(roomRepository.save(any(JRoom.class))).thenReturn(room);

        JRoom saved = roomService.save(room);
        assertNotNull(saved);
        verify(roomRepository, times(1)).save(room);
    }
}