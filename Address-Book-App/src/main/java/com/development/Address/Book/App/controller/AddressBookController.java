package com.development.Address.Book.App.controller;

import com.development.Address.Book.App.dto.UserDTO;
import com.development.Address.Book.App.exception.AddressBookNotFoundException;
import com.development.Address.Book.App.model.User;
import com.development.Address.Book.App.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AddressBookController {

    @Autowired
    private UserService userService;
    @ExceptionHandler(AddressBookNotFoundException.class)
    public ResponseEntity<String> handleAddressBookNotFoundException(AddressBookNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

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
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // POST create multiple users
    @PostMapping("/bulk")
    public ResponseEntity<List<User>> createUsers(@Valid @RequestBody List<UserDTO> userDTOList) {
        List<User> createdUsers = userService.saveAllUsers(userDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }

    // PUT update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}