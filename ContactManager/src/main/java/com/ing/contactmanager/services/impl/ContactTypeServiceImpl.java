package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.request.contactType.RequestContactTypeDTO;
import com.ing.contactmanager.dtos.response.contactType.ResponseContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import com.ing.contactmanager.services.CRUDService;
import com.ing.contactmanager.services.mappers.ContactTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl implements CRUDService<ResponseContactTypeDTO, RequestContactTypeDTO> {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid) {
        contactTypeRepository.deleteByUid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseContactTypeDTO getByUuid(UUID uuid) {
        return contactTypeMapper.convertToContactTypeDTO(contactTypeRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with UUID : " + uuid.toString() + " does not exist")));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseContactTypeDTO createOrUpdate(RequestContactTypeDTO requestContactTypeDTO, UUID uuid) {
        if (uuid == null) {
            requestContactTypeDTO.setUuid(UUID.randomUUID());

            ContactType contactType = contactTypeMapper
                    .convertPostContactTypeDTOToContactType(requestContactTypeDTO);

            contactTypeRepository.save(contactType);

            return contactTypeMapper.convertToContactTypeDTO(contactType);
        } else {
            ContactType contactType = getContactTypeByUuid(uuid);
            ContactType updatedContactType = contactTypeMapper.convertPostContactTypeDTOToContactType(requestContactTypeDTO);

            updatedContactType.setId(contactType.getId());

            contactTypeRepository.save(updatedContactType);

            return contactTypeMapper.convertToContactTypeDTO(updatedContactType);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseContactTypeDTO> getAll() {
        return new PageImpl<>(contactTypeMapper.getAllContactTypes(contactTypeRepository.findAll()));
    }

    private ContactType getContactTypeByUuid(UUID uuid) {
        return contactTypeRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with UUID : " + uuid.toString() + " does not exist"));
    }
}
