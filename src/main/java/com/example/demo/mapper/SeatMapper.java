package com.example.demo.mapper;

import com.example.demo.dto.request.SeatRequest;
import com.example.demo.dto.response.SeatResponse;
import com.example.demo.entity.JRoom;
import com.example.demo.entity.JSeat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

  private final RoomMapper roomMapper;

  public SeatMapper(RoomMapper roomMapper) {
    this.roomMapper = roomMapper;
  }

  public SeatResponse toResponse(JSeat seat) {
    if (seat == null) return null;
    return SeatResponse.builder()
        .id(seat.getId())
        .number(seat.getNumber())
        .room(roomMapper.toResponse(seat.getRoom()))
        .build();
  }

  public JSeat toEntity(SeatRequest request, JRoom room) {
    if (request == null) return null;
    return JSeat.builder().number(request.getNumber()).room(room).build();
  }
}
