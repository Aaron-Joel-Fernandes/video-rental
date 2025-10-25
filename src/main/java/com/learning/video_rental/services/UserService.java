package com.learning.video_rental.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.learning.video_rental.dto.UserRegistrationDto;
import com.learning.video_rental.entity.RoleType;
import com.learning.video_rental.entity.User;
import com.learning.video_rental.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRegistrationDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        RoleType roleType = dto.getRole() != null ? dto.getRole() : RoleType.ROLE_CUSTOMER;
        
        // Create new user
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(roleType);

        return userRepository.save(user);
    }
}