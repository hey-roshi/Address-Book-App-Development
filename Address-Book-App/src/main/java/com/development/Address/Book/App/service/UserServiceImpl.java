package com.development.Address.Book.App.service;

import com.development.Address.Book.App.dto.UserDTO;
import com.development.Address.Book.App.model.User;
import com.development.Address.Book.App.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j // Add this annotation for logging
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users"); // Logging
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.info("Fetching user by ID: {}", id); // Logging
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id); // Logging for error
                    return new RuntimeException("User not found");
                });
    }

    @Override
    public User createUser(UserDTO userDTO) {
        log.info("Creating new user: {}", userDTO); // Logging
        User user = new User();
        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        user.setPincode(userDTO.getPincode());
        user.setPermanentAddress(userDTO.isPermanentAddress());
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId()); // Logging
        return savedUser;
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        log.info("Updating user with ID: {}", id); // Logging
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id); // Logging for error
                    return new RuntimeException("User not found");
                });
        user.setName(userDTO.getName());
        user.setAddress(userDTO.getAddress());
        user.setPincode(userDTO.getPincode());
        user.setPermanentAddress(userDTO.isPermanentAddress());
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId()); // Logging
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id); // Logging
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id); // Logging
    }

    @Override
    public List<User> saveAllUsers(List<UserDTO> userDTOList) {
        log.info("Saving a list of users: {}", userDTOList); // Logging
        // Convert UserDTO list to User list
        List<User> users = userDTOList.stream()
                .map(dto -> {
                    User user = new User();
                    user.setName(dto.getName());
                    user.setAddress(dto.getAddress());
                    user.setPincode(dto.getPincode());
                    user.setPermanentAddress(dto.isPermanentAddress());
                    return user;
                })
                .collect(Collectors.toList());

        // Save all users to the database
        List<User> savedUsers = userRepository.saveAll(users);
        log.info("Saved {} users successfully", savedUsers.size()); // Logging
        return savedUsers;
    }
}


//package com.development.Address.Book.App.service;
//
//import com.development.Address.Book.App.dto.UserDTO;
//import com.development.Address.Book.App.model.User;
//import com.development.Address.Book.App.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public User getUserById(Long id) {
//        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    @Override
//    public User createUser(UserDTO userDTO) {
//        User user = new User();
//        user.setName(userDTO.getName());
//        user.setAddress(userDTO.getAddress());
//        user.setPincode(userDTO.getPincode());
//        user.setPermanentAddress(userDTO.isPermanentAddress());
//        return userRepository.save(user);
//    }
//
//    @Override
//    public User updateUser(Long id, UserDTO userDTO) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        user.setName(userDTO.getName());
//        user.setAddress(userDTO.getAddress());
//        user.setPincode(userDTO.getPincode());
//        user.setPermanentAddress(userDTO.isPermanentAddress());
//        return userRepository.save(user);
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//
//    @Override
//    public List<User> saveAllUsers(List<UserDTO> userDTOList) {
//        // Convert UserDTO list to User list
//        List<User> users = userDTOList.stream()
//                .map(dto -> {
//                    User user = new User();
//                    user.setName(dto.getName());
//                    user.setAddress(dto.getAddress());
//                    user.setPincode(dto.getPincode());
//                    user.setPermanentAddress(dto.isPermanentAddress());
//                    return user;
//                })
//                .collect(Collectors.toList());
//
//        // Save all users to the database
//        return userRepository.saveAll(users);
//    }
//}