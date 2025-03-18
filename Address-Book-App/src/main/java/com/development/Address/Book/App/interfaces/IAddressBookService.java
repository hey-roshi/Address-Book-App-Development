package com.development.Address.Book.App.interfaces;
import com.development.Address.Book.App.dto.ContactDTO;
import com.development.Address.Book.App.model.Contact;

import java.util.List;

public interface IAddressBookService {
    Contact createAddressBookEntry(ContactDTO dto);
    List<Contact> getAllEntries();
    Contact getEntryById(Long id);
    Contact updateEntry(Long id, ContactDTO dto);
    void deleteEntry(Long id);
}