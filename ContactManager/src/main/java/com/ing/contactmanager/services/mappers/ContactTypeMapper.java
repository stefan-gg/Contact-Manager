package com.ing.contactmanager.services.mappers;

import com.ing.contactmanager.dtos.request.contactType.RequestContactTypeDTO;
import com.ing.contactmanager.dtos.response.contactType.ResponseContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactTypeMapper {

    public List<ResponseContactTypeDTO> getAllContactTypes(List<ContactType> contactTypes) {
        return (contactTypes
                .stream()
                .map(this::convertToContactTypeDTO)
                .collect(Collectors.toList()));
    }

    public ResponseContactTypeDTO convertToContactTypeDTO(ContactType contactType) {
        ResponseContactTypeDTO responseContactTypeDTO = new ResponseContactTypeDTO();

        responseContactTypeDTO.setUuid(contactType.getUid());
        responseContactTypeDTO.setContactTypeName(contactType.getContactTypeName());

        return responseContactTypeDTO;
    }

    public ContactType convertPostContactTypeDTOToContactType(RequestContactTypeDTO requestContactTypeDTO) {
        ContactType contactType = new ContactType();

        contactType.setUid(requestContactTypeDTO.getUuid());
        contactType.setContactTypeName(requestContactTypeDTO.getContactTypeName());

        return contactType;
    }
}
