package com.ing.contactmanager.services.mappers.post;

import com.ing.contactmanager.controllers.dtos.post.contactType.PostContactTypeDTO;
import com.ing.contactmanager.entities.ContactType;
import com.ing.contactmanager.repositories.ContactTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostContactTypeMapper {

    private final ContactTypeRepository contactTypeRepository;

    public ContactType convertPostContactTypeDTOToContactType(PostContactTypeDTO postContactTypeDTO){
        ContactType contactType = new ContactType();

        contactType.setContactTypeName(postContactTypeDTO.getContactTypeName());

        return contactType;
    }
}
