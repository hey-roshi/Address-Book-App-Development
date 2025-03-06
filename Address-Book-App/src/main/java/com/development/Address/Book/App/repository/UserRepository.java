package com.development.Address.Book.App.repository;

import com.development.Address.Book.App.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}