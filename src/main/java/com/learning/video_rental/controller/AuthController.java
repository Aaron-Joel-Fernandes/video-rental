package com.learning.video_rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.learning.video_rental.dto.UserRegistrationDto;
import com.learning.video_rental.entity.Role;
import com.learning.video_rental.entity.RoleType;
import com.learning.video_rental.entity.User;
import com.learning.video_rental.repository.UserRepository;
import com.learning.video_rental.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: Email is already in use!");
        }

        // Create and populate a new User
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        // Set Role using the enum
        Role role = new Role();
        role.setName(registrationDto.getRole() != null ? registrationDto.getRole() : RoleType.ROLE_CUSTOMER);
        // user.setRole(role);

        // Save user using the service layer
        userService.register(registrationDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }
}
