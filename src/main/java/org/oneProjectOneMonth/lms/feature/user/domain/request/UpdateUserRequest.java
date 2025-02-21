package org.oneProjectOneMonth.lms.feature.user.domain.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Data
public class UpdateUserRequest {
	private String name;
	
	private String username;
	
	@Email(message="Invalid Email format")
	private String email;
	
	private String phone;
	
	@Past(message="Birthday must be in the past")
	private LocalDate dob;
	
	private String address;
	
	private String profilePhoto;

	// For Instructor
	private String nrc;

	private String eduBackground;
}
