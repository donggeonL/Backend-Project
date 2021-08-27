package com.project.nmt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateForm {

    private String password;

    private String passwordRepeat;

    @Email
    private String email;

}
