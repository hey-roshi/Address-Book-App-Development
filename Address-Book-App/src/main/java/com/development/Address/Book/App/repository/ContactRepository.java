package com.development.Address.Book.App.repository;

import com.development.Address.Book.App.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
