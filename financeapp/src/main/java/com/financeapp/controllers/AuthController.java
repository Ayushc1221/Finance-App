package com.financeapp.controllers;

import com.financeapp.dto.AuthRequest;
import com.financeapp.dto.AuthResponse;
import com.financeapp.security.JwtUtil;
import com.financeapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        userService.registerUser(request.getEmail(), request.getPassword(), request.getFullName());
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        var user = userService.findByEmail(request.getEmail());
        if (user.isPresent() && userService.verifyPassword(user.get(), request.getPassword())) {
            String token = jwtUtil.generateToken(user.get().getEmail());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(401).body("Invalid credentials.");
    }
}
