package com.ing.contactmanager.dtos.maps;

import com.ing.contactmanager.dtos.ContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactTypeMapService {

    private final ContactTypeRepository contactTypeRepository;

    public List<ContactTypeDTO> getAllContactTypes() {
        return ((List<ContactType>) contactTypeRepository
                .findAll())
                .stream()
                .map(this::convertToContactTypeDTO)
                .collect(Collectors.toList());
    };

    private ContactTypeDTO convertToContactTypeDTO(ContactType contactType){
        ContactTypeDTO contactTypeDTO = new ContactTypeDTO();

        contactTypeDTO.setId(contactType.getId());
        contactTypeDTO.setUuid(contactType.getUid());
        contactTypeDTO.setContactTypeName(contactType.getContactTypeName());

        return contactTypeDTO;
    };
}
