package com.ing.contactmanager.services.mappers;

import com.ing.contactmanager.controllers.dtos.get.contact.ContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContactMapper {

    private final ContactRepository contactRepository;

    public List<ContactDTO> getAllContacts() {
        return ((List<Contact>) contactRepository
                .findAll())
                .stream()
                .map(this::convertContactToContactDTO)
                .collect(Collectors.toList());
    };

    public List<ContactDTO> convertContactsToContactsDTO(List<Contact> contacts){
        return contacts
                .stream()
                .map(this::convertContactToContactDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO convertContactToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();

//        contactDTO.setId(contact.getId());
//        contactDTO.setUuid(contact.getUid());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setFirstName((contact.getFirstName()));
        contactDTO.setLastName(contact.getLastName());
//        contactDTO.setCreatedAt(contact.getCreatedAt());
//        contactDTO.setUpdatedAt(contact.getUpdatedAt());
//        contactDTO.setContactType(contact.getContactType());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setInfo(contact.getInfo());
//        contactDTO.setUser(contact.getUser());

        return contactDTO;
    };
}
