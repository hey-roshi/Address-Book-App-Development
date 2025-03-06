package com.development.Address.Book.App.service;
import com.development.Address.Book.App.dto.UserDTO;
import com.development.Address.Book.App.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(UserDTO userDTO);
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}