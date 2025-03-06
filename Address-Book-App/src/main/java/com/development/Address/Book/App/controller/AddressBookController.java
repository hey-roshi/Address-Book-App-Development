package com.development.Address.Book.App.controller;
import com.development.Address.Book.App.model.User;

import com.development.Address.Book.App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.development.Address.Book.App.dto.UserDTO;
import java.util.List;
import java.util.Optional;
import com.development.Address.Book.App.dto.UserDTO;
import com.development.Address.Book.App.model.User;
import com.development.Address.Book.App.service.UserService;

@RestController
@RequestMapping("/api/users")
public class AddressBookController{

    @Autowired
    private UserService userService;

    // GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // POST create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    // POST create multiple users
    @PostMapping("/bulk")
    public ResponseEntity<List<User>> createUsers(@RequestBody List<UserDTO> userDTOList) {
        return ResponseEntity.ok(userService.saveAllUsers(userDTOList));
    }

    // PUT update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    // DELETE user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}