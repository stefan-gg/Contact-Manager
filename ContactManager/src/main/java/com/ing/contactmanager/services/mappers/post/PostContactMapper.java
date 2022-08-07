package com.ing.contactmanager.services.mappers.post;

import com.ing.contactmanager.controllers.dtos.post.contact.PostContactDTO;
import com.ing.contactmanager.entities.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostContactMapper {

    public Contact convertPostContactDTOToContact(PostContactDTO postContactDTO) {
        Contact contact = new Contact();

        contact.setEmail(postContactDTO.getEmail());
        contact.setFirstName(postContactDTO.getFirstName());
        contact.setLastName(postContactDTO.getLastName());
        contact.setInfo(postContactDTO.getInfo());
        contact.setAddress(postContactDTO.getAddress());
        contact.setPhoneNumber(postContactDTO.getPhoneNumber());
        //contact.setContactType(postContactTypeMapper
        //        .convertPostContactTypeDTOToContactType(postContactDTO.getPostContactTypeDTO()));
        //contact.setUser(postUserMapper
        //        .convertPostUserDTOToUser(postContactDTO.getPostUserDTO()));

        return contact;
    }
}
