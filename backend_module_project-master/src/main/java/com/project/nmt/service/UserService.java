package com.project.nmt.service;

import com.project.nmt.dto.LogInDto;
import com.project.nmt.dto.SignupForm;
import com.project.nmt.dto.UserUpdateForm;
import com.project.nmt.model.User;
import com.project.nmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getOneById(Long id) {
        return userRepository.findById(id).orElseGet(User::new);
    }

    public void createNewUser(SignupForm dto) {
        User user = User.builder()
                        .userId(dto.getUserId())
                        .password(dto.getPassword())
                        .name(dto.getName())
                        .age(dto.getAge())
                        .email(dto.getEmail())
                        .budget(10000)
                        .build();

        userRepository.save(user);
    }

    public User getOneByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseGet(User::new);
    }

    public boolean checkLogIn(LogInDto logIn) {
        User user = userRepository.findByUserId(logIn.getUserId()).orElseGet(User::new);

        return user.getPassword().equals(logIn.getPassword());
    }

    public void updateUser(User user, UserUpdateForm form) {
        user.update(form);

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
