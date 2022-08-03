package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements CRUDService<Contact> {

    private final ContactRepository contactRepository;

    @Override
    public void deleteById(Integer id) {
        contactRepository.deleteById(id);
    }

    @Override
    public Contact getById(Integer id) {
        return contactRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ContactService.notFound"));
    }

    @Override
    public Contact createOrUpdate(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }
}
