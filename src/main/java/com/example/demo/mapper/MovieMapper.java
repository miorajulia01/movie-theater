package com.example.demo.mapper;

import com.example.demo.dto.request.MovieRequest;
import com.example.demo.dto.response.MovieResponse;
import com.example.demo.entity.JMovie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public MovieResponse toResponse(JMovie movie) {
        if (movie == null) return null;
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .description(movie.getDescription())
                .duration(movie.getDuration())
                .build();
    }

    public JMovie toEntity(MovieRequest request) {
        if (request == null) return null;
        return JMovie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .description(request.getDescription())
                .duration(request.getDuration())
                .build();
    }
}