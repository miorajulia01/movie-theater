package com.example.demo.dto.response;

import com.example.demo.enums.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {

	private UUID id;
	private String firstName;
	private String lastName;
	private LocalDate birthdate;
	private String email;
	private String phone;
	private Role role;
}
