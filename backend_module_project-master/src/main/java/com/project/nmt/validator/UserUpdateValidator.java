package com.project.nmt.validator;

import com.project.nmt.dto.UserUpdateForm;
import com.project.nmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class UserUpdateValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserUpdateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateForm joinForm = (UserUpdateForm) target;

        if (!joinForm.getEmail().equals("") && userRepository.existsByEmail(joinForm.getEmail())) {
            errors.rejectValue("email", "duplicate.email", "이미 가입된 이메일입니다.");
        }
        if(!joinForm.getPassword().equals("") && joinForm.getPassword().length() >= 8 && joinForm.getPassword().length() <= 20) {
            errors.rejectValue("password", "invalidLength.password", "비밀번호는 8자 이상, 20자 이하여야 합니다.");
        }
        if (!joinForm.getPassword().equals(joinForm.getPasswordRepeat())) {
            errors.rejectValue("passwordRepeat", "invalid.passwordRepeat", "비밀번호 확인이 틀렸습니다.");
        }
    }
}
