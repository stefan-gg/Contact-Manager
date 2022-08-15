package com.ing.contactmanager.services.impl;

import com.ing.contactmanager.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.dtos.request.contactType.RequestContactTypeDTO;
import com.ing.contactmanager.dtos.response.contactType.ResponseContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import com.ing.contactmanager.services.mappers.ContactTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactTypeServiceImpl {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;

    @Transactional(readOnly = true)
    public Page<ResponseContactTypeDTO> getAll(Pageable pageable) {
        return new PageImpl<>(
                contactTypeMapper.getAllContactTypes(contactTypeRepository.findAll(pageable).getContent()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByUuid(UUID uuid) {
        contactTypeRepository.deleteByUid(uuid);
    }

    @Transactional(readOnly = true)
    public ResponseContactTypeDTO getByUuid(UUID uuid) {
        return contactTypeMapper.convertToContactTypeDTO(contactTypeRepository
                .findByUid(uuid)
                .orElseThrow(() -> new NoSuchElementException("Element with UUID : " + uuid.toString() + " does not exist")));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseContactTypeDTO createOrUpdate(RequestContactTypeDTO requestContactTypeDTO, UUID uuid) {
        if (uuid == null) {

            ContactType contactType = contactTypeMapper
                    .convertPostContactTypeDTOToContactType(requestContactTypeDTO);

            contactType.setUid(UUID.randomUUID());

            contactTypeRepository.save(contactType);

            return contactTypeMapper.convertToContactTypeDTO(contactType);
        } else {
            ContactType contactType = getContactTypeByUuid(uuid);
            ContactType updatedContactType =
                    contactTypeMapper.convertPostContactTypeDTOToContactType(requestContactTypeDTO);

            updatedContactType.setId(contactType.getId());

            contactTypeRepository.save(updatedContactType);

            return contactTypeMapper.convertToContactTypeDTO(updatedContactType);
        }
    }

    public ContactType getContactType(RequestContactDTO postRequestContactDTO) {
        return contactTypeRepository.getContactTypeByContactTypeName(
                postRequestContactDTO.getContactTypeName()).orElseThrow(
                () -> new EntityNotFoundException("ContactType with passed Name does not exist"));
    }

    private ContactType getContactTypeByUuid(UUID uuid) {
        return contactTypeRepository.findByUid(uuid).orElseThrow(() -> new NoSuchElementException(
                "Element with UUID : " + uuid.toString() + " does not exist"));
    }
}
