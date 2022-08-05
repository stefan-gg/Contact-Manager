package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import com.ing.contactmanager.repositories.ContactRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.maps.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements CRUDService<ContactDTO> {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void deleteByUuid(UUID uuid) {
        contactRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ContactDTO getByUuid(UUID uuid) {
        return contactMapper.convertContactToContactDTO(contactRepository.findByUid(uuid));//.orElseThrow(() -> new NoSuchElementException("ContactService.notFound"));
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public ContactDTO createOrUpdate(ContactDTO contact) {
        //contact.setUid(UUID.randomUUID());
        return new ContactDTO();
        //return contactRepository.save(contact);
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public List<ContactDTO> getAll() {
        return contactMapper.getAllContacts();
    }
}
