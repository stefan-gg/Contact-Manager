package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.controllers.dtos.get.contactType.ContactTypeDTO;
import com.ing.contactmanager.controllers.dtos.post.contactType.PostContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.mappers.get.ContactTypeMapper;
import com.ing.contactmanager.services.mappers.post.PostContactTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements CRUDService<ContactTypeDTO, PostContactTypeDTO> {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;
    private final PostContactTypeMapper postContactTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid) {
        contactTypeRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactTypeDTO getByUuid(UUID uuid) {
        return contactTypeMapper.convertToContactTypeDTO(contactTypeRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist")));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostContactTypeDTO createOrUpdate(PostContactTypeDTO postContactTypeDTO, UUID uuid) {
        if (uuid == null) {
            postContactTypeDTO.setUuid(UUID.randomUUID());
            contactTypeRepository
                    .save(postContactTypeMapper
                            .convertPostContactTypeDTOToContactType(postContactTypeDTO));
        } else {
            ContactType contactType = getContactTypeByUuid(uuid);
            ContactType updatedContactType = postContactTypeMapper.convertPostContactTypeDTOToContactType(postContactTypeDTO);

            updatedContactType.setId(contactType.getId());

            contactTypeRepository.save(updatedContactType);
        }

        return postContactTypeDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactTypeDTO> getAll() {
        return contactTypeMapper.getAllContactTypes();
    }

    private ContactType getContactTypeByUuid(UUID uuid) {
        return contactTypeRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with passed UUID does not exist"));
    }
}
