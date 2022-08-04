package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements CRUDService<Contact> {

    private final ContactRepository contactRepository;

    @Override
    public void deleteByUuid(UUID uuid) {
        contactRepository.deleteByUid(uuid);
    }

    @Override
    public Contact getByUuid(UUID uuid) {
        return contactRepository.findByUid(uuid);//.orElseThrow(() -> new NoSuchElementException("ContactService.notFound"));
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
