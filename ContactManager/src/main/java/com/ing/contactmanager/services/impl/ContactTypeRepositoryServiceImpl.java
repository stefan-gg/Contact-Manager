package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.get.contactType.ContactTypeDTO;
import com.ing.contactmanager.controllers.dtos.post.contactType.PostContactTypeDTO;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.mappers.get.ContactTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactTypeRepositoryServiceImpl implements CRUDService<ContactTypeDTO, PostContactTypeDTO> {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;
    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public void deleteByUuid(UUID uuid) {
        contactTypeRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public ContactTypeDTO getByUuid(UUID uuid) {
        return contactTypeMapper.convertToContactTypeDTO(contactTypeRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist")));
    }

    @Override
    @Transactional(rollbackFor = { SQLException.class })
    public PostContactTypeDTO createOrUpdate(PostContactTypeDTO postContactTypeDTO) {
        return new PostContactTypeDTO();//contactTypeRepository.save(contactType);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ContactTypeDTO> getAll() {
        return contactTypeMapper.getAllContactTypes();
    }
}
