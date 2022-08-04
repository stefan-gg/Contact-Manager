package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import com.ing.contactmanager.services.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactTypeRepositoryService implements CRUDService<ContactType> {

    private final ContactTypeRepository contactTypeRepository;
    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        contactTypeRepository.deleteByUid(uuid);
    }

    @Override
    public ContactType getByUuid(UUID uuid) {
        return contactTypeRepository.findByUid(uuid);//.orElseThrow(() -> new NoSuchElementException("ContactTypeService.notFound"));
    }

    @Override
    public ContactType createOrUpdate(ContactType contactType) {
        return contactTypeRepository.save(contactType);
    }

    @Override
    public List<ContactType> getAll() {
        return contactTypeRepository.findAll();
    }
}
