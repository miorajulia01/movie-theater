package com.example.demo.mapper;

import com.example.demo.dto.request.ProjectionRequest;
import com.example.demo.dto.response.ProjectionResponse;
import com.example.demo.entity.JProjection;
import com.example.demo.entity.JMovie;
import com.example.demo.entity.JRoom;
import org.springframework.stereotype.Component;

@Component
public class ProjectionMapper {

    private final MovieMapper movieMapper;
    private final RoomMapper roomMapper;

    public ProjectionMapper(MovieMapper movieMapper, RoomMapper roomMapper) {
        this.movieMapper = movieMapper;
        this.roomMapper = roomMapper;
    }

    public ProjectionResponse toResponse(JProjection projection) {
        if (projection == null) return null;
        return ProjectionResponse.builder()
                .id(projection.getId())
                .datetime(projection.getDatetime())
                .seatPrice(projection.getSeatPrice())
                .movie(movieMapper.toResponse(projection.getMovie()))
                .room(roomMapper.toResponse(projection.getRoom()))
                .build();
    }

    public JProjection toEntity(ProjectionRequest request, JMovie movie, JRoom room) {
        if (request == null) return null;
        return JProjection.builder()
                .datetime(request.getDatetime())
                .seatPrice(request.getSeatPrice())
                .movie(movie)
                .room(room)
                .build();
    }
}