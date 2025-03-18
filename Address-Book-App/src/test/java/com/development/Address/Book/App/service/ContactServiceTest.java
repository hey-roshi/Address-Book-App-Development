package com.development.Address.Book.App.service;

import com.development.Address.Book.App.dto.ContactDTO;
import com.development.Address.Book.App.model.Contact;
import com.development.Address.Book.App.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@EnableCaching
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    private Contact contact;
    private ContactDTO contactDTO;

    @BeforeEach
    void setUp() {
        contact = new Contact(1L, "Roshi", "srivastavaroshi00@gmail.com", "6386196707");
        contactDTO = new ContactDTO("Roshi", "srivastavaroshi00@gmail.com", "6386196707");
    }

    @Test
    void testCreateAddressBookEntry() {
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);
        Contact createdContact = contactService.createAddressBookEntry(contactDTO);
        assertNotNull(createdContact);
        assertEquals("Roshi", createdContact.getName());
    }

    @Test
    void testGetAllEntries() {
        when(contactRepository.findAll()).thenReturn(List.of(contact));
        List<Contact> contacts = contactService.getAllEntries();
        assertFalse(contacts.isEmpty());
        assertEquals(1, contacts.size());
    }

    @Test
    void testGetEntryById_Found() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        Contact retrievedContact = contactService.getEntryById(1L);
        assertNotNull(retrievedContact);
        assertEquals("Roshi", retrievedContact.getName());
    }

    @Test
    void testGetEntryById_NotFound() {
        when(contactRepository.findById(2L)).thenReturn(Optional.empty());
        Contact retrievedContact = contactService.getEntryById(2L);
        assertNull(retrievedContact);
    }

    @Test
    void testUpdateEntry_Success() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);
        Contact updatedContact = contactService.updateEntry(1L, new ContactDTO("Jane Doe", "jane@example.com", "9876543210"));
        assertNotNull(updatedContact);
        assertEquals("Jane Doe", updatedContact.getName());
    }

    @Test
    void testUpdateEntry_NotFound() {
        when(contactRepository.findById(2L)).thenReturn(Optional.empty());
        Contact updatedContact = contactService.updateEntry(2L, contactDTO);
        assertNull(updatedContact);
    }

    @Test
    void testDeleteEntry_Success() {
        when(contactRepository.existsById(1L)).thenReturn(true);
        doNothing().when(contactRepository).deleteById(1L);
        assertDoesNotThrow(() -> contactService.deleteEntry(1L));
    }

    @Test
    void testDeleteEntry_NotFound() {
        when(contactRepository.existsById(2L)).thenReturn(false);
        assertDoesNotThrow(() -> contactService.deleteEntry(2L));
    }
}
