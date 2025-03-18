package com.development.Address.Book.App.controller;

import com.development.Address.Book.App.dto.LoginDTO;
import com.development.Address.Book.App.dto.UserDTO;
import com.development.Address.Book.App.repository.UserRepository;
import com.development.Address.Book.App.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        String response = authService.registerUser(userDTO);
        return ResponseEntity.ok(response);


    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        String response = authService.loginUser(loginDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @PathVariable String email,
            @RequestBody Map<String, String> requestBody) {

        String message = authService.forgotPassword(email, requestBody.get("password"));
        return ResponseEntity.ok(Map.of("message", message));
    }


    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<Map<String, String>> resetPassword(
            @PathVariable String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {

        String message = authService.resetPassword(email, currentPassword, newPassword);
        return ResponseEntity.ok(Map.of("message", message));
    }
}