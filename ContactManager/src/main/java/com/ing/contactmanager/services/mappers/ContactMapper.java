package com.ing.contactmanager.services.mappers;

import com.ing.contactmanager.dtos.request.contact.RequestContactDTO;
import com.ing.contactmanager.dtos.response.contact.ResponseContactDTO;
import com.ing.contactmanager.entities.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactMapper {

    public List<ResponseContactDTO> getAllContacts(List<Contact> contacts) {
        return (contacts
                .stream()
                .map(this::convertContactToContactDTO)
                .collect(Collectors.toList()));
    }

    public List<ResponseContactDTO> convertContactsToContactsDTO(List<Contact> contacts) {
        return contacts
                .stream()
                .map(this::convertContactToContactDTO)
                .collect(Collectors.toList());
    }

    public ResponseContactDTO convertContactToContactDTO(Contact contact) {
        ResponseContactDTO responseContactDTO = new ResponseContactDTO();

        responseContactDTO.setUuid(contact.getUid());
        responseContactDTO.setEmail(contact.getEmail());
        responseContactDTO.setFirstName((contact.getFirstName()));
        responseContactDTO.setLastName(contact.getLastName());
        responseContactDTO.setAddress(contact.getAddress());
        responseContactDTO.setInfo(contact.getInfo());
        responseContactDTO.setPhoneNumber(contact.getPhoneNumber());

        return responseContactDTO;
    }

    public Contact convertPostContactDTOToContact(RequestContactDTO postRequestContactDTO, Contact contact) {

        contact.setId(contact.getId());
        contact.setContactType(contact.getContactType());
        contact.setEmail(postRequestContactDTO.getEmail());
        contact.setFirstName(postRequestContactDTO.getFirstName());
        contact.setLastName(postRequestContactDTO.getLastName());
        contact.setInfo(postRequestContactDTO.getInfo());
        contact.setAddress(postRequestContactDTO.getAddress());
        contact.setPhoneNumber(postRequestContactDTO.getPhoneNumber());

        return contact;
    }
}
