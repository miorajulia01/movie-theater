package com.example.demo.entity;

import com.example.demo.enums.Genre;
import jakarta.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Set<Genre> genre = new HashSet<>();

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Duration duration;
}