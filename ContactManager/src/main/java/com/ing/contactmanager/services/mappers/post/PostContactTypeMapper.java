package com.ing.contactmanager.services.mappers.post;

import com.ing.contactmanager.controllers.dtos.post.contactType.PostContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import org.springframework.stereotype.Component;

@Component
public class PostContactTypeMapper {

    public ContactType convertPostContactTypeDTOToContactType(PostContactTypeDTO postContactTypeDTO){
        ContactType contactType = new ContactType();

        contactType.setContactTypeName(postContactTypeDTO.getContactTypeName());

        return contactType;
    }
}
