package com.lineup.service;

import com.lineup.config.JwtUtil;
import com.lineup.entity.UserEntity;
import com.lineup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserEntity registerUser(UserEntity user) {
        if (user.getRole() == UserEntity.Role.ROLE_USER) {
            user.setPermissions(Set.of("READ_PROFILE"));
        } else if (user.getRole() == UserEntity.Role.ROLE_ADMIN) {
            user.setPermissions(Set.of("READ_PROFILE", "EDIT_USER", "DELETE_USER"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public String login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name(),
                user.getPermissions()
        );
    }

}
