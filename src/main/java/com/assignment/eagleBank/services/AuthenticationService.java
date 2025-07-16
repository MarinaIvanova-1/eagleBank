package com.assignment.eagleBank.services;

import com.assignment.eagleBank.dtos.LoginUserDto;
import com.assignment.eagleBank.dtos.RegisterUserDto;
import com.assignment.eagleBank.entity.User;
import com.assignment.eagleBank.repositories.UserRepository;
import com.assignment.eagleBank.services.utils.IdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        var user = new User()
                .setName(input.getName())
                .setEmail(input.getEmail())
                .setPhoneNumber(input.getPhoneNumber())
                .setAddress(input.getAddress())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setId(IdGenerator.generateUserId());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}