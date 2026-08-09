package com.example.demo.mapper;

import com.example.demo.dto.request.RoomRequest;
import com.example.demo.dto.response.RoomResponse;
import com.example.demo.entity.JRoom;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomResponse toResponse(JRoom room) {
        if (room == null) return null;
        return RoomResponse.builder()
                .id(room.getId())
                .number(room.getNumber())
                .capacity(room.getCapacity())
                .build();
    }

    public JRoom toEntity(RoomRequest request) {
        if (request == null) return null;
        return JRoom.builder()
                .number(request.getNumber())
                .capacity(request.getCapacity())
                .build();
    }
}