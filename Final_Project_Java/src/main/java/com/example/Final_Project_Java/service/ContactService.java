package com.example.Final_Project_Java.service;

import com.example.Final_Project_Java.model.Contact;
import com.example.Final_Project_Java.model.Reservation;
import com.example.Final_Project_Java.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> searchByName(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return contactRepository.findAll();
        }
        return contactRepository.findByNameIgnoreCaseContaining(keyword);
    }


    // Lưu contact
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    // Lấy tất cả contact (nếu cần)
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}
