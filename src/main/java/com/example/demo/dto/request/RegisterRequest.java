package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotNull
	@Past
	private LocalDate birthdate;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min = 8, max = 100)
	private String password;

	@NotBlank
	@Pattern(regexp = "^[+]?[0-9 ()-]{6,20}$")
	private String phone;
}
