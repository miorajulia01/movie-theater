package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotNull @Past private LocalDate birthdate;


	@NotBlank
	@Pattern(regexp = "^[+]?[0-9 ()-]{6,20}$")
	private String phone;
}
