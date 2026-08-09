package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String number;

  @Column(nullable = false)
  private int capacity;

  @Builder.Default
  @JsonIgnore
  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<JSeat> seats = new ArrayList<>();
}
