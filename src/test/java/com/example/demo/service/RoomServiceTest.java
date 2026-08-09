package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.dto.request.RoomRequest;
import com.example.demo.dto.response.RoomResponse;
import com.example.demo.entity.JRoom;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.repository.JRoomRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomServiceTest {

    private JRoomRepository roomRepository;
    private RoomMapper roomMapper;
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        roomRepository = mock(JRoomRepository.class);
        roomMapper = mock(RoomMapper.class);
        roomService = new RoomService(roomRepository, roomMapper);
    }

    @Test
    void testFindAll() {
        JRoom room = new JRoom();
        RoomResponse response = new RoomResponse();

        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomMapper.toResponse(room)).thenReturn(response);

        List<RoomResponse> rooms = roomService.findAll();
        assertEquals(1, rooms.size());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdFound() {
        UUID id = UUID.randomUUID();
        JRoom room = new JRoom();
        RoomResponse response = new RoomResponse();

        when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        when(roomMapper.toResponse(room)).thenReturn(response);

        RoomResponse found = roomService.findById(id);
        assertNotNull(found);
        verify(roomRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roomService.findById(id));
    }

    @Test
    void testSave() {
        RoomRequest request = new RoomRequest();
        JRoom room = new JRoom();
        JRoom savedRoom = new JRoom();
        RoomResponse response = new RoomResponse();

        when(roomMapper.toEntity(request)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(savedRoom);
        when(roomMapper.toResponse(savedRoom)).thenReturn(response);

        RoomResponse saved = roomService.save(request);
        assertNotNull(saved);
        verify(roomRepository, times(1)).save(room);
    }
}