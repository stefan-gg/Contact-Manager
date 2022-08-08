package com.ing.contactmanager.services.mappers;

import com.ing.contactmanager.controllers.dtos.response.contactType.ResponseContactTypeDTO;
import com.ing.contactmanager.controllers.dtos.request.contactType.RequestContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactTypeMapper {

    private final ContactMapper contactMapper;

    public List<ResponseContactTypeDTO> getAllContactTypes(List<ContactType> contactTypes) {
        return (contactTypes
                .stream()
                .map(this::convertToContactTypeDTO)
                .collect(Collectors.toList()));
    }

    public ResponseContactTypeDTO convertToContactTypeDTO(ContactType contactType){
        ResponseContactTypeDTO responseContactTypeDTO = new ResponseContactTypeDTO();

//        responseContactTypeDTO.setId(contactType.getId());
        responseContactTypeDTO.setUuid(contactType.getUid());
        responseContactTypeDTO.setContactTypeName(contactType.getContactTypeName());
        responseContactTypeDTO.setContacts(contactMapper.convertContactsToContactsDTO(contactType.getContacts()));

        return responseContactTypeDTO;
    }

    public ContactType convertPostContactTypeDTOToContactType(RequestContactTypeDTO requestContactTypeDTO){
        ContactType contactType = new ContactType();

        contactType.setUid(requestContactTypeDTO.getUuid());
        contactType.setContactTypeName(requestContactTypeDTO.getContactTypeName());

        return contactType;
    }
}
