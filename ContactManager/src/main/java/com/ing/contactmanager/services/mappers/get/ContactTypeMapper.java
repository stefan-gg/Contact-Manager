package com.ing.contactmanager.services.mappers.get;

import com.ing.contactmanager.controllers.dtos.get.contactType.ContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactTypeMapper {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactMapper contactMapper;

    public List<ContactTypeDTO> getAllContactTypes() {
        return ((List<ContactType>) contactTypeRepository
                .findAll())
                .stream()
                .map(this::convertToContactTypeDTO)
                .collect(Collectors.toList());
    }

    public ContactTypeDTO convertToContactTypeDTO(ContactType contactType){
        ContactTypeDTO contactTypeDTO = new ContactTypeDTO();

//        contactTypeDTO.setId(contactType.getId());
        contactTypeDTO.setUuid(contactType.getUid());
        contactTypeDTO.setContactTypeName(contactType.getContactTypeName());
        contactTypeDTO.setContacts(contactMapper.convertContactsToContactsDTO(contactType.getContacts()));

        return contactTypeDTO;
    }
}
