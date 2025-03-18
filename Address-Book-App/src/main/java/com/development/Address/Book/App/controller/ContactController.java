package com.development.Address.Book.App.controller;

import com.development.Address.Book.App.dto.ContactDTO;
import com.development.Address.Book.App.model.Contact;
import com.development.Address.Book.App.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@Slf4j
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<?> createEntry(@Valid @RequestBody ContactDTO dto) {
        try {
            Contact contact = contactService.createAddressBookEntry(dto);
            return ResponseEntity.ok(contact);
        } catch (Exception e) {
            log.error("Error creating contact: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error creating contact: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllEntries() {
        try {
            List<Contact> contacts = contactService.getAllEntries();
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            log.error("Error fetching contacts: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error fetching contacts: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable Long id) {
        try {
            Contact contact = contactService.getEntryById(id);
            if (contact == null) {
                log.warn("Contact with ID {} not found", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(contact);
        } catch (Exception e) {
            log.error("Error fetching contact with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error fetching contact: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable Long id, @Valid @RequestBody ContactDTO dto) {
        try {
            Contact updatedContact = contactService.updateEntry(id, dto);
            if (updatedContact == null) {
                log.warn("Failed to update contact with ID {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedContact);
        } catch (Exception e) {
            log.error("Error updating contact with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error updating contact: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id) {
        try {
            contactService.deleteEntry(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting contact with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error deleting contact: " + e.getMessage());
        }



    }
}