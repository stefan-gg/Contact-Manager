package com.ing.contactmanager.services.mappers.post;

import com.ing.contactmanager.controllers.dtos.post.contact.PostContactDTO;
import com.ing.contactmanager.entities.Contact;
import com.ing.contactmanager.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostContactMapper {

    private final ContactRepository contactRepository;

    public Contact convertPostContactDTOToContact(PostContactDTO postContactDTO){
        Contact contact = new Contact();

        contact.setEmail(postContactDTO.getEmail());
        contact.setFirstName(postContactDTO.getFirstName());
        contact.setLastName(postContactDTO.getLastName());
        contact.setInfo(postContactDTO.getInfo());
        contact.setAddress(postContactDTO.getAddress());
        contact.setPhoneNumber(postContactDTO.getPhoneNumber());
        //TODO: kako da se dodaju user i contactType ? saznaj

        return contact;
    }
}
