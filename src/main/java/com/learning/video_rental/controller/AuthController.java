package com.learning.video_rental.controller;

import com.learning.video_rental.dto.*;
import com.learning.video_rental.entity.User;
import com.learning.video_rental.security.JwtUtil;
import com.learning.video_rental.services.UserService;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, UserService userService, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto dto) {
        User created = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        UserDetails ud = (UserDetails) auth.getPrincipal();
        String email = ud.getUsername();
        // find role via UserService or repository to generate token:
        // quick approach: assume userService (or repo) can return user by email
        User user = userService.findByEmail(email).orElseThrow();
        String token = jwtUtil.generateToken(email, user.getRole());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

