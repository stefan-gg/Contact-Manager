package com.ing.contactmanager.services.mappers.post;

import com.ing.contactmanager.controllers.dtos.post.contactType.PostContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import org.springframework.stereotype.Component;

@Component
public class PostContactTypeMapper {

    public ContactType convertPostContactTypeDTOToContactType(PostContactTypeDTO postContactTypeDTO){
        ContactType contactType = new ContactType();

        contactType.setUid(postContactTypeDTO.getUuid());
        contactType.setContactTypeName(postContactTypeDTO.getContactTypeName());

        return contactType;
    }

    public PostContactTypeDTO convertContactTypeToPostContactTypeDTO(ContactType contactType){
        PostContactTypeDTO postContactTypeDTO = new PostContactTypeDTO();

        postContactTypeDTO.setId(contactType.getId());
        postContactTypeDTO.setUuid(contactType.getUid());
        postContactTypeDTO.setContactTypeName(contactType.getContactTypeName());

        return postContactTypeDTO;
    }
}
