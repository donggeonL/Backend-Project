package com.project.nmt.validator;

import com.project.nmt.dto.SignupForm;
import com.project.nmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class UserSignupValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SignupForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignupForm joinForm = (SignupForm) target;

        if (userRepository.existsByUserId(joinForm.getUserId())) {
            errors.rejectValue("userId", "duplicate.userId", "이미 사용중인 닉네임입니다.");
        }
        if (userRepository.existsByEmail(joinForm.getEmail())) {
            errors.rejectValue("email", "duplicate.email", "이미 가입된 이메일입니다.");
        }
        if (!joinForm.getPassword().equals(joinForm.getPasswordRepeat())) {
            errors.rejectValue("passwordRepeat", "invalid.passwordRepeat", "비밀번호 확인이 틀렸습니다.");
        }
    }
}
