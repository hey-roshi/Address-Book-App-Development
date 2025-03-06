package com.development.Address.Book.App.controller;
import com.development.Address.Book.App.model.User;

import com.development.Address.Book.App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.development.Address.Book.App.dto.UserDTO;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class AddressBookController {

    @Autowired
    private UserRepository userRepository;

    // GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // POST create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        user.setPincode(userDTO.getPincode());
        user.setPermanentAddress(userDTO.isPermanentAddress());
        return ResponseEntity.ok(userRepository.save(user));
    }

    // PUT update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDTO.getName());
            user.setAddress(userDTO.getAddress());
            user.setPincode(userDTO.getPincode());
            user.setPermanentAddress(userDTO.isPermanentAddress());
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}