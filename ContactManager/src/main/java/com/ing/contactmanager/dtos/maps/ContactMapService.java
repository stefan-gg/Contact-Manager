package com.ing.contactmanager.dtos.maps;

import com.ing.contactmanager.dtos.ContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMapService {

    private final ContactRepository contactRepository;

    private ContactDTO convertContactToContactDTO(Contact contact){
        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setId(contact.getId());
        contactDTO.setUuid(contact.getUid());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setFirstName((contact.getFirstName()));
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setCreatedAt(contact.getCreatedAt());
        contactDTO.setUpdatedAt(contact.getUpdatedAt());
        contactDTO.setContactType(contact.getContactType());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setInfo(contact.getInfo());
        contactDTO.setUser(contact.getUser());

        return contactDTO;
    };
}
