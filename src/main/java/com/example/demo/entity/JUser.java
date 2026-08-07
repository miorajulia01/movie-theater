package com.example.demo.entity;

import com.example.demo.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JUser {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String email;
    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;
}