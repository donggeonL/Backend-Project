package com.project.nmt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class SignupForm {


	@NotBlank
	@Length(min = 3, max = 20)
	@Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
	private String userId;

	@NotBlank
	@Length(min = 8, max = 20)
	private String password;

	@NotBlank
	private String passwordRepeat;

	@NotBlank
	@Length(min = 2, max = 10)
	@Pattern(regexp = "^[가-힣]{2,10}$")
	private String name;

	@NotBlank
	@Email
	private String email;

	@Min(value = 1)
	@Max(value = 200)
	private Integer age;

}
