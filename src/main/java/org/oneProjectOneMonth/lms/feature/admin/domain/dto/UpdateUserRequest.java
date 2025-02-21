package org.oneProjectOneMonth.lms.feature.admin.domain.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Data
public class UpdateUserRequest {
	private String name;
	
	@Email(message="Invalid Email format")
	private String email;
	
	private String phone;
	
	@Past(message="Date must be in the past")
	private LocalDate dob;
	
	private String address;
	
	private String profilePhoto;

	// For Instructor
	private String nrc;

	private String eduBackground;
}
