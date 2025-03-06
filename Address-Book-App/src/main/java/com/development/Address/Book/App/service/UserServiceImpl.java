package com.development.Address.Book.App.service;

import com.development.Address.Book.App.dto.UserDTO;
import com.development.Address.Book.App.model.User;
import com.development.Address.Book.App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        user.setPincode(userDTO.getPincode());
        user.setPermanentAddress(userDTO.isPermanentAddress());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        user.setPincode(userDTO.getPincode());
        user.setPermanentAddress(userDTO.isPermanentAddress());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}